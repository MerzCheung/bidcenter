package com.dyg.bidcenter.controller;

import com.dyg.bidcenter.common.ResultCode;
import com.dyg.bidcenter.entity.SysDictEntity;
import com.dyg.bidcenter.service.DictService;
import com.dyg.bidcenter.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author merz
 * @Description:
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @PostMapping("/getList")
    public Object getList(@RequestBody SysDictEntity sysDictEntity) {
        return ResponseUtil.result(ResultCode.SUCCESS, dictService.getList(sysDictEntity));
    }
}
