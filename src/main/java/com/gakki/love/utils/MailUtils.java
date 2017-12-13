package com.gakki.love.utils;

import com.gakki.love.domain.User;
import com.gakki.love.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.patterns.PatternNode;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.thymeleaf.standard.expression.MessageExpression;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: YuAn
 * \* Date: 2017/12/7
 * \* Time: 21:51
 * \* To change this template use File | Settings | File Templates.
 * \* Description:邮箱工具类：
 * 功能：
 * 1.发送邮件
 * 2.验证一个字符串是否为邮箱格式
 * \
 */
@Slf4j
public class MailUtils {
    /**
     * 发一封邮件
     * @param mailTo 接收地址
     * @param top 邮件主题
     * @param content 邮件内容
     */

    public static void sendMail(String mailTo,String top,String content){

        Properties props = new Properties();
        props.setProperty("mail.smtp.auth","true");
        props.setProperty("mail.transporot.protocol","smtp");
        Session session = Session.getInstance(props);

        Message message = new MimeMessage(session);
        try {

            message.setFrom(new InternetAddress("1639536019@qq.com"));
            InternetAddress to[] = new InternetAddress[1];
            to[0] = new InternetAddress(mailTo);
            message.setRecipients(Message.RecipientType.TO,to);
            message.setSubject(top);
            message.setContent(content,"text/html;charset=UTF-8");
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.qq.com","1639536019@qq.com","you a error ");
            transport.sendMessage(message,to);
            transport.close();

        }catch (MessagingException e){
            e.printStackTrace();
        }
    }

    /**
     * 检测是否给定的字符串是否为邮箱
     * @param email 字符串
     * @return true,如果是邮箱就返回true
     */
    public static boolean isEmail(String email){
        Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()){
            log.debug("邮箱");
            return true;
        }
        return false;
    }


    public static boolean exist(String email, UserService userService){

        User user = userService.getByEmail(EncryptUtils.execEncrypt(email));
        return user == null ? false : true;
    }
}