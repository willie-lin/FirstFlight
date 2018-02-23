package com.gakki.love.controller;

import com.alibaba.fastjson.JSONObject;
import com.gakki.love.service.UploadService;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import static javax.crypto.Cipher.SECRET_KEY;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: YuAn
 * \* Date: 2018/2/18
 * \* Time: 23:25
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Slf4j
@Controller
public class UploadsController {

    @Resource
    private UploadService uploadService;

    @Value("${ flight.qiniu.downDomain}")
    private String downDomain;

    @RequestMapping("/upload/token")
    @ResponseBody
    public JSONObject upLoadToken(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uptoken",uploadService.getUploadToken());
        jsonObject.put("downDomain",downDomain);
        return jsonObject;
    }

    @GetMapping("/upload")
    public String forwardQiniuUpload(){
        return "qiniu_upload";
    }

}