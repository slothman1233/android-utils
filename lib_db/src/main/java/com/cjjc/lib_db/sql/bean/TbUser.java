package com.cjjc.lib_db.sql.bean;


import com.cjjc.lib_db.sql.annotion.DbFiled;
import com.cjjc.lib_db.sql.annotion.DbTable;

/**
 * 总用户表
 */
@DbTable("tb_user")
public class TbUser {
    @DbFiled("_id")
    public String id; //用户ID
    @DbFiled("name")
    public String name; //用户名
    @DbFiled("password")
    public String password; //用户密码
    @DbFiled("status")
    public Integer status; //用户登录状态

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public TbUser() {
    }

    public TbUser(String id, String name, String password, Integer status) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.status = status;
    }

    public TbUser(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
