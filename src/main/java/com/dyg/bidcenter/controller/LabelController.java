package com.dyg.bidcenter.controller;

import com.dyg.bidcenter.common.ResultCode;
import com.dyg.bidcenter.entity.SysLabelEntity;
import com.dyg.bidcenter.service.LabelService;
import com.dyg.bidcenter.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author merz
 * @Description:
 */
@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @GetMapping("/queryAll")
    public Object queryAll(@RequestParam(required = false) Integer id) {
        return ResponseUtil.result(ResultCode.SUCCESS, labelService.queryAll(id));
    }

    @GetMapping("/delete/{id}")
    public Object delete(@PathVariable Integer id) {
        return ResponseUtil.result(ResultCode.SUCCESS, labelService.delete(id));
    }

    @PostMapping("/add")
    public Object add(@Validated @RequestBody SysLabelEntity sysLabelEntity, BindingResult bindingResult) {
        return ResponseUtil.result(ResultCode.SUCCESS, labelService.add(sysLabelEntity));
    }
}
