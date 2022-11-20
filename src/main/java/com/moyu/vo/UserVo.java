package com.moyu.vo;

import com.moyu.entity.User;

/**
 * Created by Administrator on 2018/4/7.
 */
public class UserVo {

    private  Integer rows;
    private  Integer page;
    private User user;

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
