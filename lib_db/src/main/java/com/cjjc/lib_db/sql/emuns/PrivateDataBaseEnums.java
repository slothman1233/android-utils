package com.cjjc.lib_db.sql.emuns;


import com.cjjc.lib_db.sql.BaseDaoFactory;
import com.cjjc.lib_db.sql.UserDao;
import com.cjjc.lib_db.sql.bean.TbUser;
import com.cjjc.lib_db.sql.tools.DbConstantConfig;

import java.io.File;

/**
 * 提供数据访问路径
 */
public enum PrivateDataBaseEnums {
    database(DbConstantConfig.USER_DB_NAME);
    private String value;

    PrivateDataBaseEnums(String value) {
        this.value = value;
    }

    public String getValue() {
        UserDao userDao = BaseDaoFactory.getInstance().getUserDao(UserDao.class, TbUser.class);
        TbUser currentUser = userDao.getCurrentUser();
        if (currentUser != null) {
            //创建总数据库路径
            File file = new File(DbConstantConfig.DB_PATH, DbConstantConfig.SUM_DB_DIR_NAME);
            if (!file.exists()) {
                file.mkdirs();
            }
            //创建用户数据库路径
            File userFile = new File(file, currentUser.getName());
            if (!userFile.exists()) {
                userFile.mkdirs();
            }
            return userFile.getAbsolutePath() + "/" + value;
        }
        return null;
    }
}
