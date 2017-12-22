package com.gakki.love.validator;

import com.gakki.love.domain.User;
import com.gakki.love.utils.MailUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: YuAn
 * \* Date: 2017/12/18
 * \* Time: 21:49
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {

        User user = (User) o;

        String username = user.getUsername();

        if (username.length() < 4 || username.length() > 15){
            errors.rejectValue("username","filed.username.length","用户名必须为4-15个字母");
        }
        String password = user.getPassword();
        if (password.length() < 8 || password.length() > 15){
            errors.rejectValue("password","filed.password.length","密码必须为8——15个字符");
        }
        String email = user.getEmail();
        if (!MailUtils.isEmail(email)){
            errors.rejectValue("email","field.email.error","邮箱格式错误！");
        }
    }
}