package com.dyg.bidcenter.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.UUID;
import com.dyg.bidcenter.entity.SysBidDocumentDto;
import com.dyg.bidcenter.entity.SysBidDocumentEntity;
import com.dyg.bidcenter.service.BidDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
public class UploadController {

    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private BidDocumentService bidDocumentService;

    /**
     * 单文件上传
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadOne")
    public Object uploadOne(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        String currentDate=LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String yyyyMMdd = uploadPath + currentDate + File.separator;
        if (!FileUtil.exist(yyyyMMdd)) {
            FileUtil.mkdir(yyyyMMdd);
        }
        String fileName = UUID.randomUUID().toString() + "@" + multipartFile.getOriginalFilename();
        File file1 = FileUtil.writeBytes(multipartFile.getBytes(), yyyyMMdd + fileName);
        if (file1.length() > 0) {
            Map<String, String> map = new HashMap<>();
            map.put("fileName", multipartFile.getOriginalFilename());
            map.put("fileUrl", currentDate + File.separator + fileName);
            return map;
        }
        return null;
    }

    /**
     * 单文件上传
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadSupplierBidFile")
    public Object uploadSupplierBidFile(@RequestParam(value = "file") MultipartFile multipartFile, SysBidDocumentDto sysBidDocumentDto) throws IOException {
        String currentDate=LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String yyyyMMdd = uploadPath + currentDate + File.separator;
        if (!FileUtil.exist(yyyyMMdd)) {
            FileUtil.mkdir(yyyyMMdd);
        }
        String absFileName = sysBidDocumentDto.getNumber() + "_" +
                sysBidDocumentDto.getCompanyName() + "." + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        String fileName = UUID.randomUUID().toString() + "@" + absFileName;
        File file1 = FileUtil.writeBytes(multipartFile.getBytes(), yyyyMMdd + fileName);
        if (file1.length() > 0) {
            SysBidDocumentEntity sysBidDocumentEntity = new SysBidDocumentEntity();
            sysBidDocumentEntity.setId(sysBidDocumentDto.getId());
            sysBidDocumentEntity.setBidId(sysBidDocumentDto.getBidId());
            sysBidDocumentEntity.setFileName(absFileName);
            sysBidDocumentEntity.setFileUrl(currentDate + File.separator + fileName);
            sysBidDocumentEntity.setTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            SysBidDocumentEntity update = bidDocumentService.update(sysBidDocumentEntity);
            if (update != null) {
                sysBidDocumentDto.setFileName(sysBidDocumentEntity.getFileName());
                sysBidDocumentDto.setFileUrl(sysBidDocumentEntity.getFileUrl());
                sysBidDocumentDto.setTime(sysBidDocumentEntity.getTime());
                return sysBidDocumentDto;
            }
        }
        return null;
    }

    /**
     * 多文件上传
     * @param multipartFiles
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadMany")
    public Object uploadMany(@RequestParam(value = "file") MultipartFile[] multipartFiles) throws IOException {
        String currentDate=LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String yyyyMMdd = uploadPath + currentDate + File.separator;
        if (!FileUtil.exist(yyyyMMdd)) {
            FileUtil.mkdir(yyyyMMdd);
        }
        List<String> pathList = new ArrayList<>();
        if (multipartFiles.length > 0) {
            for (MultipartFile multipartFile : multipartFiles) {
                String fileName = UUID.randomUUID().toString() + "@" + multipartFile.getOriginalFilename();
                File file1 = FileUtil.writeBytes(multipartFile.getBytes(), yyyyMMdd + fileName);
                if (file1.length() > 0) {
                    pathList.add(yyyyMMdd + fileName);
                }
            }
        }
        return pathList;
    }

    /**
     * desc: 图片显示
     * param:
     * return:
     * author: CDN
     * date: 2019/11/17
     */
    @GetMapping("showImg")
    public Object showImg(HttpServletResponse response, @RequestParam Map<String, Object> map) {
        if (map.isEmpty()) {
            return "文件不能为空";
        }
        boolean suffix = checkPic(map.get("suffix").toString());
        if (!suffix) {
            return "不是图片";
        }
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(FileUtil.readBytes(uploadPath + map.get("path").toString() + File.separator + map.get("fileName").toString()));
            IoUtil.close(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * desc: 文件下载
     * param:    http://127.0.0.1:83/download?path=20200709&fileName=63610ee-862c-4b55-9461-4c1ffe18e1c8@%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20200622141419.jpg
     * return:
     * author: CDN
     * date: 2019/11/17
     */
    @GetMapping("download")
    public Object download(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> map) throws IOException {
        if (map.isEmpty()) {
            return "文件不能为空";
        }
        String fileUrl =uploadPath + map.get("path").toString() + File.separator + map.get("fileName").toString();
//        String suffix = map.get("suffix").toString();
        ServletOutputStream outputStream = response.getOutputStream();
        response.setContentType("application/force-download");
        //设置编码，避免文件名中文乱码
        String fileName = map.get("fileName").toString().split("@")[1];
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            //IE浏览器处理
            fileName = java.net.URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
        } else {
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        outputStream.write(FileUtil.readBytes(fileUrl));
        IoUtil.close(outputStream);
        return null;
    }


    /**
     * desc: 图片格式检验
     * param:
     * return:
     * author: CDN
     * date: 2019/11/17
     */
    private static boolean checkPic(String suffix) {
        if (suffix.isEmpty()) {
            return false;
        }
        String reg = "(.JPEG|.jpeg|.JPG|.jpg|.PNG|.png|.GIF|.gif|.BMP|.bmp)$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher("." + suffix);
        return matcher.find();
    }

}
