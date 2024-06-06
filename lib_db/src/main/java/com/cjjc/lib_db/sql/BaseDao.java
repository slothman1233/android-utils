package com.cjjc.lib_db.sql;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;


import com.cjjc.lib_db.sql.annotion.DbFiled;
import com.cjjc.lib_db.sql.annotion.DbTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据库操作实现
 *
 * @param <T>
 */
public class BaseDao<T> implements IBaseDao<T> {

    private boolean isInit = false;//只初始化一次
    private SQLiteDatabase sqLiteDatabase; //数据库操作对象
    public Class<T> bean;//当前操作的实体对象

    private String tableName;//当前表名
    private HashMap<String, Field> cacheMap;//保存表中的列与实体中字段的映射关系
    private List<Field> actionBeanField;//当前表中有注解标识的字段

    public synchronized boolean init(Class<T> bean, SQLiteDatabase sqLiteDatabase) {
        if (!isInit) {
            this.bean = bean;
            this.sqLiteDatabase = sqLiteDatabase;
            //未打开数据库
            if (!sqLiteDatabase.isOpen()) {
                return false;
            }
            //获取表名  如果未定义表名  就用类名作为表名
            if (bean.getAnnotation(DbTable.class) == null) {
                tableName = bean.getName();
            } else {
                tableName = bean.getAnnotation(DbTable.class).value();
            }
            //自动建表
            String sql = createTable();
            try {
                sqLiteDatabase.execSQL(sql);
            } catch (Exception e) {
                isInit = false;
                return false;
            }
            //建立表中的列与实体中字段的映射关系
            initCacheMap();
            isInit = true;
        }
        return isInit;
    }

    //自动建表
    @SuppressLint("NewApi")
    private String createTable() {
        actionBeanField = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        buffer.append("create table if not exists ");
        buffer.append(tableName + " (");
        //获取所有字段名
        Field[] fields = bean.getDeclaredFields();
        for (Field field : fields) {
            //未添加注解的字段  不作为数据库列名加入
            DbFiled annotation = field.getAnnotation(DbFiled.class);
            if (annotation == null) {
                continue;
            }
            actionBeanField.add(field);//保存可以操作的字段
            Class<?> type = field.getType();//获取字段类型
            if (type == String.class) {
                buffer.append(annotation.value() + " TEXT,");
            } else if (type == Double.class) {
                buffer.append(annotation.value() + " DOUBLE,");
            } else if (type == Integer.class) {
                buffer.append(annotation.value() + " INTEGER,");
            }
//            else if (type == BigDecimal.class) {
//                //暂时先不处理    处理思路--转成String 在数据插入时给给前面加个标识 在查询时还原数据
//                buffer.append(annotation.value() + " TEXT,");
//            }
//            else if (type == int.class) {
//                Log.e("=_=","wwwwwwww");
//                //只处理了插入
//                buffer.append(annotation.value() + " INTEGER,");
//            }
            else if (type == Long.class) {
                buffer.append(annotation.value() + " BIGINT,");
            } else if (type == byte[].class) {
                buffer.append(annotation.value() + " BLOB,");
            } else {
                //不支持的类型  (其他类型可自定义处理)
                continue;
            }
        }
        //删除最后一个字段的逗号
        if (buffer.charAt(buffer.length() - 1) == ',') {
            buffer.deleteCharAt(buffer.length() - 1);
        }
        buffer.append(")");
        return buffer.toString();
    }

    //建立表中的列与实体中字段的映射关系
    private void initCacheMap() {
        cacheMap = new HashMap<>();
        String sql = "select * from " + tableName + " limit 0"; //这里只要列名  不查询数据
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        String[] columnNames = cursor.getColumnNames();//获取表中所有列名
        if (actionBeanField == null || actionBeanField.size() == 0) {
            return;
        }
        for (String columnName : columnNames) {
            Field reslutField = null;
            for (Field field : actionBeanField) {
                String fieldName = field.getAnnotation(DbFiled.class).value();
                if (columnName.equals(fieldName)) {
                    reslutField = field;
                }
            }
            if (reslutField != null) {
                //存储当前有效映射列名
                cacheMap.put(columnName, reslutField);
            }
        }
    }

    @Override
    public Long insert(T bean) {
        Map<String, String> map = getBeanKeyValues(bean);
        ContentValues contentValues = getContentValues(map);
        return sqLiteDatabase.insert(tableName, null, contentValues);
    }

    @Override
    public int update(T bean, T where) {
        Map<String, String> map = getBeanKeyValues(bean);
        ContentValues contentValues = getContentValues(map);
        Map<String, String> whereMap = getBeanKeyValues(where);
        Condition condition = new Condition(whereMap);
        return sqLiteDatabase.update(tableName, contentValues, condition.whereClause, condition.whereArgs);
    }

    @Override
    public int delete(T where) {
        Map<String, String> map = getBeanKeyValues(where);
        Condition condition = new Condition(map);
        return sqLiteDatabase.delete(tableName, condition.whereClause, condition.whereArgs);
    }

    @Override
    public List<T> query(T where) {
        return query(where, null, null, null, null, null);
    }

    @Override
    public List<T> query(T where, String groupBy, String orderBy, String having,
                         Integer startIndex, Integer limit) {
        //分页参数
        String limitString = null;
        if (startIndex != null && limit != null) {
            limitString = startIndex + " , " + limit;
        }
        Map<String, String> map = getBeanKeyValues(where);
        Condition condition=new Condition(map);
        //查询数据
        Cursor cursor = sqLiteDatabase.query(tableName, null, condition.whereClause,
                condition.whereArgs, groupBy, having, orderBy, limitString);
        List<T> result= getResult(cursor,where);
        cursor.close();//关闭游标
        return result;
    }

    //将实体分解成Map键值映射形式
    public Map<String, String> getBeanKeyValues(T bean) {
        Map<String, String> map = new HashMap<>();
        Iterator<Field> iterator = cacheMap.values().iterator();
        while (iterator.hasNext()) {
            Field next = iterator.next();
            next.setAccessible(true);
            try {
                Object obj = next.get(bean);
                if (obj == null) {
                    continue;
                }
                String key = next.getAnnotation(DbFiled.class).value();
                String value = obj.toString();
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    map.put(key, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    //创建数据库操作参数
    private ContentValues getContentValues(Map<String, String> map) {
        ContentValues contentValues = new ContentValues();
        Set<String> keys = map.keySet();//获取键集合
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();//获取Key
            String value = map.get(key);//获取Value
            if (value != null) {
                contentValues.put(key, value);
            }
        }
        return contentValues;
    }

    //自动生成:改/删条件语句
    private class Condition {
        String whereClause;//条件语句
        String[] whereArgs;//条件参数

        public Condition(Map<String, String> where) {
            List<String> list = new ArrayList<>();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" 1=1");
            Set<String> keys = where.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = where.get(key);
                if (!TextUtils.isEmpty(value)) {
                    stringBuilder.append(" and " + key + " =?");
                    list.add(value);
                }
            }
            whereClause = stringBuilder.toString();
            whereArgs = list.toArray(new String[list.size()]);
        }
    }

    //将查询的数据还原
    private List<T> getResult(Cursor cursor, T where) {
        ArrayList list=new ArrayList();
        Object item;
        while (cursor.moveToNext()){
            try {
                item=where.getClass().newInstance();//反射创建对象
                //遍历当前操作对象需要处理的字段
                Iterator<Map.Entry<String, Field>> iterator = cacheMap.entrySet().iterator();
                while (iterator.hasNext()){
                    Map.Entry<String, Field> next = iterator.next();
                    String key = next.getKey();//获取字段名 tb_name
                    Field value = next.getValue();//获取字段对象
                    Class<?> type = value.getType();//获取字段类型
                    int columnIndex = cursor.getColumnIndex(key);//根据字段名查询当前字段在数据库中的下标 =-1 则代表不存在
                    if (columnIndex != -1) {
                        //为对象赋值
                        if (type == String.class) {
                            value.set(item, cursor.getString(columnIndex));
                        } else if (type == Double.class) {
                            value.set(item, cursor.getDouble(columnIndex));
                        } else if (type == Integer.class) {
                            value.set(item, cursor.getInt(columnIndex));
                        }
//                       else if (type == int.class) {
//                            Log.e("=_=","aaaaaaaaaaaaaaaa");
//                            value.set(item, cursor.getInt(columnIndex));
//                        }
                        else if (type == Long.class) {
                            value.set(item, cursor.getLong(columnIndex));
                        } else if (type == byte[].class) {
                            value.set(item, cursor.getBlob(columnIndex));
                        } else {
                            continue;
                        }
                    }
                }
                list.add(item);//将生成的一条记录添加到集合
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
