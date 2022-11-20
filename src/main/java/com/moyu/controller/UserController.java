package com.moyu.controller;

import com.moyu.entity.User;
import com.moyu.service.UserService;
import com.moyu.vo.UserVo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2018/4/6.
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/getUser")
    public void getUser(Integer id,HttpServletResponse respone) throws IOException {

        respone.sendRedirect("getUser2");
    }

    @RequestMapping("/getUser2")
    public User getUser2(Integer id) throws IOException {

        User user = userService.getUser(id);
        return user;
    }


    @RequestMapping("/getUserByWhere")
    public List<User> getUserByWhere(User user)  {
        return userService.getUserByWhere(user);
    }

}
