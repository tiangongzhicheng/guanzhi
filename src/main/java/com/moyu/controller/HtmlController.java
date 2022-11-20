package com.moyu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018/4/5.
 */

@RequestMapping("/html")
@Controller
public class HtmlController {


    @RequestMapping("/index")
    private String index() {
        return "index.html";
    }

    @RequestMapping("/index2")
    private String index2() {

        return "redirect:/index.html";
    }
}
