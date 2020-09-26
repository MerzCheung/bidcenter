package com.dyg.bidcenter.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/{path}")
    public String page(@PathVariable("path") String path) {
        return path;
    }

    @GetMapping("/json")
    @ResponseBody
    public String page() {
        Map map = new HashMap<>();
        map.put("1", "2");
        map.put("2", "2");
        map.put("3", "2");
        return JSON.toJSONString(map);
    }
}
