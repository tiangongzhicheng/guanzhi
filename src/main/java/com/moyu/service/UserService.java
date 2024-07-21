package com.moyu.service;

import com.moyu.annotation.AuditOperate;
import com.moyu.entity.User;

import java.util.List;

/**
 * Created by Administrator on 2018/4/6.
 */

public interface UserService {
    User getUser(Integer id);

    List<User> getUserByWhere(User user);

    @AuditOperate(name = "nameA", value = "valueA")
    String  testaa(Integer id);
}
