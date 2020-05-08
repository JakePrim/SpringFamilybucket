package com.example.springboot.hello.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 这个类放在了约定之外的包里，将无法被正常扫描到Spring容器中，所以该类无法生效
 */
@Controller
public class TestController {
    @ResponseBody
    @RequestMapping("/testhello")
    public String hello() {
        return "test hello";
    }
}
