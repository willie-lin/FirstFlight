package com.gakki.love.utils;

import com.gakki.love.config.SystemConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.ThreeDEval;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.security.SecureRandom;

/**
 * Created by 林漠 on 2017/6/14.
 */

@Slf4j
@Component
public class EncryptUtils {

    @Resource
    private SystemConstant systemConstant;
    private static BCryptPasswordEncoder encoder;


    @PostConstruct
    public void initBCryptPasswordEncoder(){
        encoder = new BCryptPasswordEncoder(systemConstant.getEncryptStrength(),new SecureRandom("yuan".getBytes()));

    }

    public static String execEncrypt(Object string){
        log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"  :  [{ }]",encoder);
        String encode = encoder.encode(string.toString());
        log.info(Thread.currentThread().getStackTrace()[1].getMethodName() + "   加密：[{ }],[{ }]",string,encode);
        return encode;
    }

    public static boolean matches(String plainPassword,String encodedPassword){
        boolean matches = encoder.matches(plainPassword,encodedPassword);
        log.info(Thread.currentThread().getStackTrace()[1].getMethodName() + "  密码匹配：[{ }]",matches);
        return matches;
    }


}
