package com.gakki.love.controller.user;

import com.gakki.love.domain.Topic;
import com.gakki.love.domain.User;
import com.gakki.love.service.UserService;
import com.gakki.love.utils.EncryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

/**
 * Created by 林漠 on 2017/6/14.
 */

@Slf4j
@SessionAttributes(value = { "user", "topics", }, types = { User.class, Topic.class })
@Controller
public class Login {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param user
     * @param map
     * @param session
     * @param principal
     * @return
     */

    @RequestMapping (value = "/sign_in" ,method = RequestMethod.POST)
    @ResponseBody
    public String userLogin(User user, Map<String,Object> map, HttpSession session, Principal principal){

        user.setSessionid(session.getId());

        String regex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

        if (user.getUsername().matches(regex)){

            log.debug("通过邮箱登录!!!");

            user.setEmail(EncryptUtils.execEncrypt(user.getEmail()));
            user.setUsername(null);
        }

        User user1 = userService.getByName(principal.getName());

        if (user1.getId() != null){
            map.put("user",user1);

            session.setAttribute("user",user1);
            return "success";
        }

        return "error";
    }



}
