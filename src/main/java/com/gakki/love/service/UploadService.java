package com.gakki.love.service;

import com.alibaba.fastjson.JSONObject;

public interface UploadService {
    String getUrlEntityName(String url);

    String getUploadToken();

    JSONObject fetchResourceByUrl(String url);

    JSONObject deleteResourceByUrl(String url);
}
