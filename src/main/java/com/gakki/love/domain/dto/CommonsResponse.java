package com.gakki.love.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 通用JSON响应实体
 * Created by 林漠 on 2017/6/7.
 */
@Data
@AllArgsConstructor
public class CommonsResponse{
    private boolean success;
    private String message;
}