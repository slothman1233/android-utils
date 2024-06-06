package com.cjjc.lib_db.sql;

import android.database.sqlite.SQLiteDatabase;


import com.cjjc.lib_db.sql.emuns.PrivateDataBaseEnums;
import com.cjjc.lib_db.sql.tools.DbConstantConfig;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库创建工厂
 */
public class BaseDaoFactory {

    private SQLiteDatabase userDatabase;//总库--数据库操作引用
    private SQLiteDatabase sqLiteDatabase;//当前用户--数据库操作引用
    private static BaseDaoFactory instance = new BaseDaoFactory();

    public static BaseDaoFactory getInstance() {
        return instance;
    }

    //保存所有的dao层，实现单例
    protected Map<String, BaseDao> map = Collections.synchronizedMap(new HashMap<String, BaseDao>());

    public BaseDaoFactory() {
        //总表
        File file = new File(DbConstantConfig.DB_PATH, DbConstantConfig.SUM_DB_DIR_NAME);
        if (!file.exists()) {
            file.mkdirs();
        }
        //总数据库
        String userDatabasePath = file.getAbsolutePath() + "/" + DbConstantConfig.USER_MSG_DB_NAME;
        userDatabase = SQLiteDatabase.openOrCreateDatabase(userDatabasePath, null);
    }

    /**
     * 总数据库
     *
     * @param clazz 数据库操作实现
     * @param bean  当前要操作的对象
     * @param <R>   数据库操作实现泛型
     * @param <T>   当前要操作的对象泛型
     * @return
     */
    public synchronized <R extends BaseDao<T>, T> R getUserDao(Class<R> clazz, Class<T> bean) {
        BaseDao baseDao = null;
        //判断当前总数据的表是否已缓存
        if (map.get(clazz.getSimpleName()) != null) {
            return (R) map.get(clazz.getSimpleName());
        }
        try {
            //创建当前业务扩展 数据库操作类
            baseDao = clazz.newInstance();
            baseDao.init(bean, userDatabase);//初始化表
            map.put(clazz.getSimpleName(), baseDao);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (R) baseDao;
    }

    /**
     * 个人数据库
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public synchronized <T> BaseDao<T> getAppDao(Class<T> clazz) {
        BaseDao baseDao = null;
        if (sqLiteDatabase == null) {
            //创建个人用户数据库
            sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(PrivateDataBaseEnums.database.getValue(), null);
        }
        try {
            baseDao = BaseDao.class.newInstance();
            baseDao.init(clazz, sqLiteDatabase);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseDao;
    }

    /**
     * 创建当前操作对象
     * @param clazz  数据库操作实现
     * @param bean   当前要操作的对象
     * @param <R>    数据库操作实现泛型
     * @param <T>    当前要操作的对象泛型
     * @return
     */
//    public synchronized <R extends BaseDao<T>,T> R getAppDao(Class<R> clazz,Class<T> bean){
//        BaseDao baseDao=null;
//        try {
//            baseDao=clazz.newInstance();
//            baseDao.init(bean,sqLiteDatabase);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
//        return (R) baseDao;
//    }
}
