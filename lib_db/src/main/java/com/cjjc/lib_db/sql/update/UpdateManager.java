package com.cjjc.lib_db.sql.update;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.cjjc.lib_db.sql.BaseDaoFactory;
import com.cjjc.lib_db.sql.UserDao;
import com.cjjc.lib_db.sql.bean.TbUser;
import com.cjjc.lib_db.sql.tools.DbConstantConfig;
import com.cjjc.lib_db.sql.tools.DomUtils;
import com.cjjc.lib_db.sql.tools.DbFileUtil;
import com.cjjc.lib_db.sql.update.bean.CreateDb;
import com.cjjc.lib_db.sql.update.bean.CreateVersion;
import com.cjjc.lib_db.sql.update.bean.UpdateDb;
import com.cjjc.lib_db.sql.update.bean.UpdateDbXml;
import com.cjjc.lib_db.sql.update.bean.UpdateStep;
import com.cjjc.lib_tools.util.log.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库升级管理
 */
public class UpdateManager {
    private File parentFile = new File(DbConstantConfig.DB_PATH, DbConstantConfig.SUM_DB_DIR_NAME);//原数据库路径
    private File bakFile = new File(parentFile, DbConstantConfig.BACK_DB_DIR_NAME); //备份数据库路径
    private List<TbUser> userList = new ArrayList<>();//当前所有的用户
    private boolean balStatue = true;//备份状态  如果 总数据库/用户数据库 出现备份失败 则停止数据库升级
    private static UpdateManager instance;

    public static UpdateManager getInstance() {
        if (instance == null) {
            synchronized (UpdateManager.class) {
                if (instance == null) {
                    instance = new UpdateManager();
                }
            }
        }
        return instance;
    }


    //开始迁移数据库版本
    public void startUpdateDb(Context context,String updateXmlPath) {
        //解析updateXml.xml
        UpdateDbXml updateDbxml = DomUtils.readDbXml(context,updateXmlPath);
        //解析 数据库版本迁移记录-->上个版本/目标版本(app更新后的当前版本)
        String[] versions = DbFileUtil.getLocalVersionInfo(new File(parentFile, DbConstantConfig.RECORD_UPDATE_FILE_NAME));
        String lastVersion = versions[0]; //拿到上一个版本
        String thisVersion = versions[1]; //拿到当前版本
        //第一步备份原数据库
        String userFile = parentFile.getAbsolutePath() + "/" + DbConstantConfig.USER_MSG_DB_NAME;//原数据库文件路径
        String userBak = bakFile.getAbsolutePath() + "/" + DbConstantConfig.USER_MSG_DB_NAME;//备份数据文件路径
        balStatue = DbFileUtil.CopySingleFile(userFile, userBak);//将原数据库拷贝一份作为备份
        if (!balStatue) {
            rollBack(0);
            return;
        }
        //获取当前总数据库中的所有用户
        userList.clear();
        userList =  BaseDaoFactory.getInstance().getUserDao(UserDao.class, TbUser.class).query(new TbUser());
        //备份所有用户数据库
        for (TbUser user : userList) {
            String loginDbDir = parentFile.getAbsolutePath() + "/" + user.getName() + "/" + DbConstantConfig.USER_DB_NAME;
            String loginCopy = bakFile.getAbsolutePath() + "/" + user.getName() + "/" + DbConstantConfig.USER_DB_NAME;
            balStatue = DbFileUtil.CopySingleFile(loginDbDir, loginCopy);
            if (!balStatue) {
                //如果某个用户数据库备份失败 则停止升级
                break;
            }
        }
        if (!balStatue) {
            rollBack(0);
            return;
        }
        //查找  原版本-->新版本  的更新方案
        UpdateStep updateStep = DomUtils.findStepByVersion(updateDbxml, lastVersion, thisVersion);
        if (updateStep == null) {
            return;
        }
        //获取更新方案脚本
        List<UpdateDb> updateDbs = updateStep.getUpdateDbs();
        try {
            //第二步   将原始数据库中所有的表名 更改成 bak_表名(数据还在)
            executeBefOrAftSql(updateDbs, true);
            // 第三步:检查新表，创建新表
            CreateVersion createVersion = DomUtils.findCreateByVersion(updateDbxml, thisVersion);
            executeCreateVersion(createVersion);
            //第四步  将原来bak_表名  的数据迁移到 新表中
            executeBefOrAftSql(updateDbs, false);
            rollBack(1);
        } catch (Exception e) {
            LogUtil.xLoge("数据库升级失败-->" + e.getMessage());
            rollBack(2);
        }
    }

    /**
     * 更改表名/迁移数据
     *
     * @param updateDbs
     * @param type
     * @throws Exception
     */
    private void executeBefOrAftSql(List<UpdateDb> updateDbs, boolean type) throws Exception {
        for (UpdateDb db : updateDbs) {
            if (db == null || db.getName() == null) {
                throw new Exception("db or dbName is null;");
            }
            //获取需要修改的表名脚本语句
            List<String> sqls;
            if (type) {
                sqls = db.getSqlBefores(); //更改备份数据库表名
            } else {
                sqls = db.getSqlAfters();  //将旧表中的数据迁移到新表并删除旧表
            }
            SQLiteDatabase sqlitedb = null;
            if (userList != null && !userList.isEmpty()) {
                // 多用户表升级
                for (int i = 0; i < userList.size(); i++) {
                    sqlitedb = getDb(db.getName(), userList.get(i).getName());
                    executeSql(sqlitedb, sqls);
                    sqlitedb.close();
                }
            }

        }
    }

    //根据用户名打开对应的数据库
    private SQLiteDatabase getDb(String dbname, String userName) {
        String dbfilepath = null;
        SQLiteDatabase sqlitedb = null;
        File file = new File(parentFile, userName);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (dbname.equalsIgnoreCase(DbConstantConfig.USER_DB_NAME.substring(0, DbConstantConfig.USER_DB_NAME.length() - 3))) {
            dbfilepath = file.getAbsolutePath() + "/" + DbConstantConfig.USER_DB_NAME;// logic对应的数据库路径
        }
        if (dbfilepath != null) {
            File f = new File(dbfilepath);
            f.mkdirs();
            if (f.isDirectory()) {
                f.delete();
            }
            sqlitedb = SQLiteDatabase.openOrCreateDatabase(dbfilepath, null);
        }
        return sqlitedb;
    }

    //执行脚本 将备份数据库中的所有表加上 bak_  前缀
    private void executeSql(SQLiteDatabase sqlitedb, List<String> sqls) {
        // 检查参数
        if (sqls == null || sqls.size() == 0) {
            return;
        }
        sqlitedb.beginTransaction();
        for (String sql : sqls) {
            sql = sql.replaceAll("\r\n", " ");
            sql = sql.replaceAll("\n", " ");
            if (!"".equals(sql.trim())) {
                try {
                    sqlitedb.execSQL(sql);
                } catch (SQLException e) {
                }
            }
        }
        sqlitedb.setTransactionSuccessful();
        sqlitedb.endTransaction();
    }

    //创建最新的表结构
    private void executeCreateVersion(CreateVersion createVersion) throws Exception {
        if (createVersion == null || createVersion.getCreateDbs() == null) {
            throw new Exception("createVersion or createDbs is null;");
        }
        for (CreateDb cd : createVersion.getCreateDbs()) {
            if (cd == null || cd.getName() == null) {
                throw new Exception("db or dbName is null when createVersion;");
            }
            // 创建数据库表sql
            List<String> sqls = cd.getSqlCreates();
            SQLiteDatabase sqlitedb = null;
            if (userList != null && !userList.isEmpty()) {
                for (int i = 0; i < userList.size(); i++) {
                    sqlitedb = getDb(cd.getName(), userList.get(i).getName());
                    executeSql(sqlitedb, sqls);
                    sqlitedb.close();
                }
            }
        }
    }

    /**
     * 升级失败 回滚
     *
     * @param type 0 备份失败  1 升级成功  2 升级失败
     */
    private void rollBack(int type) {
        //如果备份数据库 不存在 则无法回滚
        if (!bakFile.exists()) {
            return;
        }
        String oldMsgPath = parentFile.getAbsolutePath() + "/" + DbConstantConfig.USER_MSG_DB_NAME;//原数据库文件路径
        String balMsgPath = bakFile.getAbsolutePath() + "/" + DbConstantConfig.USER_MSG_DB_NAME;//备份数据文件路径
        File oldMsgFile = new File(oldMsgPath);
        File balMsgFile = new File(balMsgPath);
        File update = new File(parentFile, DbConstantConfig.RECORD_UPDATE_FILE_NAME);
        //升级失败回滚
        if (type == 2) {
            //删除原总数据库
            if (oldMsgFile.exists()) {
                oldMsgFile.delete();
            }
            if (userList.size() > 0) {
                //删除原所有用户数据库
                for (TbUser user : userList) {
                    String loginDbDir = parentFile.getAbsolutePath() + "/" + user.getName() + "/" + DbConstantConfig.USER_DB_NAME;
                    File oldUserFile = new File(loginDbDir);
                    if (oldUserFile.exists()) {
                        oldUserFile.delete();
                    }
                }
            }
            //还原备份的总数据库
            DbFileUtil.CopySingleFile(balMsgPath, oldMsgPath);
            //还原备份的所有用户数据库
            for (TbUser user : userList) {
                //备份用户数据库路径
                String balUserPath = bakFile.getAbsolutePath() + "/" + user.getName() + "/" + DbConstantConfig.USER_DB_NAME;
                //原用户数据库路径
                String oldUserPath = parentFile.getAbsolutePath() + "/" + user.getName() + "/" + DbConstantConfig.USER_DB_NAME;
                File oldUserFile = new File(balUserPath);
                if (oldUserFile.exists()) {
                    DbFileUtil.CopySingleFile(balUserPath, oldUserPath);
                }
            }
        }

        //删除总数据库的备份
        if (balMsgFile.exists()) {
            balMsgFile.delete();
        }
        if (userList.size() > 0) {
            //删除所有用户数据库的备份
            for (TbUser user : userList) {
                String balDbDirPath = bakFile.getAbsolutePath() + "/" + user.getName();
                String balDbFilePath = bakFile.getAbsolutePath() + "/" + user.getName() + "/" + DbConstantConfig.USER_DB_NAME;
                File dir = new File(balDbDirPath);
                File file = new File(balDbFilePath);
                if (dir.exists()) {
                    //删除备份数据库
                    if (file.exists()) {
                        file.delete();
                    }
                    //删除文件夹
                    dir.delete();
                }
            }
        }
        //删除备份数据文件夹
        bakFile.delete();
        //删除更新记录文件
        if (update.exists()) {
            update.delete();
        }
        switch (type) {
            case 0:
                LogUtil.xLoge("备份失败,停止升级,删除备份残留,备份文件是否还存在-->" + bakFile.exists());
                break;
            case 1:
                LogUtil.xLoge("升级成功,删除备份残留,备份文件是否还存在-->" + bakFile.exists());
                break;
            case 2:
                LogUtil.xLoge("升级失败,回滚完成,备份文件是否还存在-->" + bakFile.exists());
                break;
        }
    }


}
