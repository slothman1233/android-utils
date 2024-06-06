package com.cjjc.lib_db.sql;

import java.util.List;

/**
 * 数据库操作顶层接口
 * @param <T>
 */
public interface IBaseDao<T> {

    Long insert(T bean);

    int update(T bean,T where);

    int delete(T where);

    List<T> query(T where);

    List<T> query(T where, String groupBy, String orderBy, String having, Integer startIndex,
                  Integer limit);
}
