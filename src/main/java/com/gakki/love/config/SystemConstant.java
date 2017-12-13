package com.gakki.love.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: YuAn
 * \* Date: 2017/12/8
 * \* Time: 18:30
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Component
@Data
public class SystemConstant {

    @Value("${flight.encrypt.strength}")
    private int encryptStrength;
}