package com.dyg.bidcenter.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.utils.StringUtils;
import com.dyg.bidcenter.common.ResultCode;
import com.dyg.bidcenter.entity.SmsResponseDataEntity;
import com.dyg.bidcenter.entity.SysBidEntity;
import com.dyg.bidcenter.entity.SysSupplierEntity;
import com.dyg.bidcenter.entity.SysSupplierLabelEntity;
import com.dyg.bidcenter.service.SupplierService;
import com.dyg.bidcenter.utils.ResponseUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author merz
 * @Description:
 */
@RestController
@RequestMapping("/consume")
@Slf4j
public class SendSmsController {

    @Autowired
    private SupplierService supplierService;

    //注册短信验证码
    @GetMapping("/send")
    public Object SendSms(@RequestParam String phoneNumber) {
        // 第一个参数 线路(不管)  第二个 AccessKey ID  第三个 密钥
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GGFQDorUa9cjKYGmEjR", "EQ5sgVF3lgX1U78eVSj8DxDF4JSyb0");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        // 手机号 入参
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        // 签名
        request.putQueryParameter("SignName", "HEC投标报价系统");
        // 模板Id
        request.putQueryParameter("TemplateCode", "SMS_204746871");
        Map<String,Object> map = new HashMap<>();
        // 随机生成6位验证码
        String code = RandomUtil.randomNumbers(6);
        // 将验证码保存到Session中,用于验证。
        HttpServletRequest re = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        re.getSession().setAttribute("code",code);
        map.put("code",code);
        // 转String
        String codeMsg = new Gson().toJson(map);
        request.putQueryParameter("TemplateParam", codeMsg);
        try {
            CommonResponse response = client.getCommonResponse(request);
            SmsResponseDataEntity smsResponseDataEntity = JSON.parseObject(response.getData(), SmsResponseDataEntity.class);
            if ("OK".equals(smsResponseDataEntity.getCode())) {
                return ResponseUtil.result(ResultCode.SUCCESS);
            } else {
                log.error("短信发送失败： {}", smsResponseDataEntity.getMessage());
                return ResponseUtil.result(ResultCode.ERROR);
            }
        } catch (ClientException e) {
            e.printStackTrace();
            log.error("短信发送失败： {}", e.getMessage());
            return ResponseUtil.result(ResultCode.ERROR);
        }
    }


    //中标通知供应商
    @PostMapping("/advise")
    public void SendAdvise(@RequestBody SysBidEntity sysBidEntity) {
        String bidNumber = sysBidEntity.getNumber();
        String bidName =sysBidEntity.getName();
        String names = sysBidEntity.getSuccessfulSupplier();
        if (!StringUtils.isEmpty(names)) {
            String[] nameArr = names.split(",");
            for (String name: nameArr) {
                //根据供应商名称查询供应商电话
                SysSupplierEntity s = supplierService.getPhoneNumber(name);
                String companyName = s.getCompanyName();
                String phoneNumber = s.getPhoneNumber();
                // 第一个参数 线路(不管)  第二个 AccessKey ID  第三个 密钥
                DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GGFQDorUa9cjKYGmEjR", "EQ5sgVF3lgX1U78eVSj8DxDF4JSyb0");
                IAcsClient client = new DefaultAcsClient(profile);

                CommonRequest request = new CommonRequest();
                request.setSysMethod(MethodType.POST);
                request.setSysDomain("dysmsapi.aliyuncs.com");
                request.setSysVersion("2017-05-25");
                request.setSysAction("SendSms");
                request.putQueryParameter("RegionId", "cn-hangzhou");
                // 手机号
                request.putQueryParameter("PhoneNumbers", phoneNumber);
                // 签名
                request.putQueryParameter("SignName", "东阳光投标报价系统");
                // 模板Id
                request.putQueryParameter("TemplateCode", "SMS_204940114");
                Map<String,Object> map = new HashMap<>();
                map.put("name",companyName);
                map.put("bidNumber",bidNumber);
                map.put("bidName",bidName);
                // 转String
                String codeMsg = new Gson().toJson(map);
                request.putQueryParameter("TemplateParam", codeMsg);
                try {
                    CommonResponse response = client.getCommonResponse(request);
                    System.out.println(response.getData());
                } catch (ServerException e) {
                    e.printStackTrace();
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    //邀请供应商投标
    @PostMapping("/inviteBid")
    public void invitationBid(@RequestBody SysBidEntity sysBidEntity) {
        //获取标书信息
        String bidNumber = sysBidEntity.getNumber();
        String bidName =sysBidEntity.getName();
        //获取供应商信息
        List<SysSupplierEntity> suppliers = sysBidEntity.getSuppliers();
        String names = sysBidEntity.getSuccessfulSupplier();
        //第一轮通知
        if(suppliers!=null && StringUtils.isEmpty(names)){
            for (SysSupplierEntity supplier :suppliers) {
                //供应商名称
                String name = supplier.getCompanyName();
                //供应商ID
                Integer id = supplier.getId();
                //根据供应商ID查询供应商信息
                SysSupplierEntity s = supplierService.getPhoneNumberById(id);
                String phoneNumber = s.getPhoneNumber();

                //发送短信 第一个参数 线路(不管)  第二个 AccessKey ID  第三个 密钥
                DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GGFQDorUa9cjKYGmEjR", "EQ5sgVF3lgX1U78eVSj8DxDF4JSyb0");
                IAcsClient client = new DefaultAcsClient(profile);

                CommonRequest request = new CommonRequest();
                request.setSysMethod(MethodType.POST);
                request.setSysDomain("dysmsapi.aliyuncs.com");
                request.setSysVersion("2017-05-25");
                request.setSysAction("SendSms");
                request.putQueryParameter("RegionId", "cn-hangzhou");
                // 手机号
                request.putQueryParameter("PhoneNumbers", phoneNumber);
                // 签名
                request.putQueryParameter("SignName", "东阳光投标报价系统");
                // 模板Id
                request.putQueryParameter("TemplateCode", "SMS_204976386");
                Map<String,Object> map = new HashMap<>();
                map.put("name",name);
                map.put("bidNumber",bidNumber);
                map.put("bidName",bidName);
                // 转String
                String codeMsg = new Gson().toJson(map);
                request.putQueryParameter("TemplateParam", codeMsg);
                try {
                    CommonResponse response = client.getCommonResponse(request);
                    System.out.println(response.getData());
                } catch (ServerException e) {
                    e.printStackTrace();
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            }
        }else if(!StringUtils.isEmpty(names)){
            String[] nameArr = names.split(",");
            for (String name: nameArr) {
                //查询供应商电话
                SysSupplierEntity s = supplierService.getPhoneNumber(name);
                String phoneNumber = s.getPhoneNumber();
                String companyName = s.getCompanyName();
                // 第一个参数 线路(不管)  第二个 AccessKey ID  第三个 密钥
                DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GGFQDorUa9cjKYGmEjR", "EQ5sgVF3lgX1U78eVSj8DxDF4JSyb0");
                IAcsClient client = new DefaultAcsClient(profile);

                CommonRequest request = new CommonRequest();
                request.setSysMethod(MethodType.POST);
                request.setSysDomain("dysmsapi.aliyuncs.com");
                request.setSysVersion("2017-05-25");
                request.setSysAction("SendSms");
                request.putQueryParameter("RegionId", "cn-hangzhou");
                // 手机号
                request.putQueryParameter("PhoneNumbers", phoneNumber);
                // 签名
                request.putQueryParameter("SignName", "东阳光投标报价系统");
                // 模板Id
                request.putQueryParameter("TemplateCode", "SMS_204976386");
                Map<String,Object> map = new HashMap<>();
                map.put("name",companyName);
                map.put("bidNumber",bidNumber);
                map.put("bidName",bidName);
                // 转String
                String codeMsg = new Gson().toJson(map);
                request.putQueryParameter("TemplateParam", codeMsg);
                try {
                    CommonResponse response = client.getCommonResponse(request);
                    System.out.println(response.getData());
                } catch (ServerException e) {
                    e.printStackTrace();
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
