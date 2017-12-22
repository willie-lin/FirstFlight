package com.gakki.love.utils;

import com.gakki.love.constant.MessageConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import sun.swing.plaf.synth.DefaultSynthStyle;

import javax.servlet.http.HttpSession;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: YuAn
 * \* Date: 2017/12/14
 * \* Time: 21:43
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 *  * 用户工具类，包含常用的静态方法：
 * <strong>
 *  1.登陆检测
 *  2.操作成功返回
 *  </strong>
 * @author ramer
 *
 */
@Slf4j
public class UserUtils {

    private static final String SUCCESS_MESSAGE = MessageConstant.SUCCESS_MESSAGE;

    /**
     * 操作成功
     * @param session
     * @param succMessage 成功信息
     */

    public static void execSuccess(HttpSession session,String... succMessage){
        if (succMessage.length >  0) {
            session.setAttribute("succMessage", succMessage[0]);
            return;
        }
        session.setAttribute("succMessage",SUCCESS_MESSAGE);
    }

    public static boolean checkLogin(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + " principal : { }",principal);

        if (principal.equals("anonymousUser")){
            return false;
        }
        org.springframework.security.core.userdetails.User user = (User) principal;
        log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " loginUser: [{ }}",user.getUsername());
        return true;

    }
}