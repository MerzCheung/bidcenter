package com.dyg.bidcenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author merz
 * @Description:
 * @date 2019/5/15 22:38
 */
@Controller
public class PageController {

    @GetMapping("/")
    public String loginView() {
        return "login";
    }
}
