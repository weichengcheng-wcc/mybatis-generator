package com.cw.generator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @Author weiChengCheng
 * @Date 2020-05-19 20:47
 * @Description todo
 */
@Controller
public class ViewController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

}
