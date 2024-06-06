package com.cjjc.lib_db.sql;


import com.cjjc.lib_db.sql.bean.TbUser;

import java.util.List;

/**
 * 扩展  用于处理业务使用  在增删改查时 添加自己的业务
 */
public class UserDao extends BaseDao<TbUser> {

    @Override
    public Long insert(TbUser bean) {
        boolean isExist = false;//当前用户是否已存在
        List<TbUser> query = query(new TbUser());//查询所有用户
        TbUser where = null;
        for (TbUser user : query) {
            where = new TbUser();
            where.setId(user.getId());
            user.setStatus(0);//设置当前用户为 未登录状态
            if (bean.getName().equals(user.getName())) {
                isExist = true;
            }
            update(user, where);
        }
        bean.setStatus(1);//设置当前用户未登录状态
        if (!isExist) {
            return super.insert(bean);
        } else {
            update(bean, bean);
            return 1L;
        }
    }

    //获取当前登录的用户
    public TbUser getCurrentUser() {
        TbUser user = new TbUser();
        user.setStatus(1);
        List<TbUser> list = query(user);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
