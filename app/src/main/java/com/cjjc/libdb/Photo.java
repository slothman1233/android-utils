package com.cjjc.libdb;

import com.cjjc.lib_db.sql.annotion.DbFiled;
import com.cjjc.lib_db.sql.annotion.DbTable;

@DbTable("tb_photo")
public class Photo {
    @DbFiled("tb_time")
    public String time;
    @DbFiled("tb_path")
    public String path;

    public Photo() {
    }

    public Photo(String time, String path) {
        this.time = time;
        this.path = path;
    }

    public Photo(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "time='" + time + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
