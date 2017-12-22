package com.gakki.love.controller.user;

import com.gakki.love.constant.MessageConstant;
import com.gakki.love.constant.PageConstant;
import com.gakki.love.domain.Notify;
import com.gakki.love.domain.Topic;
import com.gakki.love.domain.User;
import com.gakki.love.domain.dto.CommonsResponse;
import com.gakki.love.exception.FlightException;
import com.gakki.love.service.NotifyService;
import com.gakki.love.service.TopicService;
import com.gakki.love.service.UserService;
import com.gakki.love.utils.EncryptUtils;
import com.gakki.love.utils.FileUtils;
import com.gakki.love.utils.StringUtils;
import com.gakki.love.utils.UserUtils;
import com.gakki.love.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;

/**
 * Created by 林漠 on 2017/6/14.
 */
@Slf4j
@SessionAttributes(value = {"user","topics"},types = {User.class,Topic.class})
@Controller
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserValidator userValidator;

    @Resource
    private TopicService topicService;

    @Resource
    private NotifyService notifyService;

    private static final String SUCCESS = PageConstant.SUCCESS;

    private final String SUCCESS_CHANGE_PASS = MessageConstant.SUCCESS_MESSAGE;

    //分享页面大小
    @Value("${flight.personal.topic.page.size}")
    private int TOPIC_PAGE_SIZE;

    @Value("${flight.encrypt.strength}")
    private int ENCRYPT_STRENGTH;

    @InitBinder("user")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    /**
     * 个人中心
     * @param pageNum 页码
     * @param user 用户
     * @param map the map
     * @param session the session
     * @return 如果已登录返回个人主页，否则返回错误页面
     */

    @GetMapping("/user/personal")
    public String personalMiddle(@RequestParam(value = "pageNum",required = false,defaultValue = "1")
                                 String pageNum,User user ,Map<String,Object> map,HttpSession session){
        session.setAttribute("inOtherPage",false);
        session.setAttribute("inTopicPage",false);
        if (!UserUtils.checkLogin()){
            throw new FlightException("您的登录已过期，请重新登录！！！");
        }
        //避免加载异常,重新获取USER
        user = userService.getById(user.getId());
        log.debug("个人中心");
        String hasCheck = "false";
        Set<Notify> notifies = notifyService.getNotifies(user,hasCheck);
        hasCheck = "true";
        Set<Notify> readedNotifies = notifyService.getNotifies(user,hasCheck);
        user.setNotifies(notifies);
        user.setReadedNotifies(readedNotifies);
        log.debug(" 用户 " + user.getId() + " 收到 " + notifies.size() + " 条信息 ");

        int page = 1;

        //当前页号属于人为构造时，用于判断页号是否存在

        Page<Topic> oldTopics = (Page<Topic>) session.getAttribute("topicsPage");
        try {
            page = Integer.parseInt(pageNum);
            if (page < 1){
                page = 1;
            }else if (oldTopics != null && page > oldTopics.getTotalPages()){
                page = oldTopics.getTotalPages() > 0 ? oldTopics.getTotalPages() : 1;

            }
        }catch (Exception e){
            page = 1;
        }
        Page<Topic> topicsPage = topicService.getTopicsPageByUserId(user,page,TOPIC_PAGE_SIZE);
        map.put("topicsPage",topicsPage);
        map.put("notifyCount",notifies.size());
        map.put("user",user);
        return "personal";
    }

    /**
     * 重定向到密码修改页
     * @return 引导到密码修改页
     *
     */

    @GetMapping("/user/forwardModifyPassword")
    public String forwardModifyPassword(){
        if (!UserUtils.checkLogin()){
            throw new FlightException("您还未登录或登录已过期！！！");
        }
        return "modify_pass";
    }

    public CommonsResponse modifyPassword(
            @RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword,
            @SessionAttribute("user") User user){
        if (!EncryptUtils.matches(oldPassword,user.getPassword())){
            return new CommonsResponse(true,"原始密码更改错误！！！");
        }
        if (!EncryptUtils.matches(newPassword,user.getPassword())){
            return new CommonsResponse(true,"密码未更改！！！");
        }
        user.setPassword(EncryptUtils.execEncrypt(newPassword));
        if (userService.newOrUpdate(user)) {
            return new CommonsResponse(true, "密码更改成功！！！");
        }

        return new CommonsResponse(false,"系统异常，请稍后再试！！！");
    }

}