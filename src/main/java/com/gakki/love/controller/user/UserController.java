package com.gakki.love.controller.user;

import com.gakki.love.constant.MessageConstant;
import com.gakki.love.constant.PageConstant;
import com.gakki.love.domain.Notify;
import com.gakki.love.domain.Topic;
import com.gakki.love.domain.User;
import com.gakki.love.domain.dto.CommonResponse;
import com.gakki.love.exception.FlightException;
import com.gakki.love.service.NotifyService;
import com.gakki.love.service.TopicService;
import com.gakki.love.service.UserService;
import com.gakki.love.utils.*;
import com.gakki.love.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

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
            if (page < 1) {
                page = 1;
            } else if (oldTopics != null && page > oldTopics.getTotalPages()) {
                page = oldTopics.getTotalPages() > 0 ? oldTopics.getTotalPages() : 1;

            }
        } catch (NumberFormatException e) {
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

    /**
     * 修改密码
     * @param oldPassword 原始密码
     * @param newPassword 新密码
     * @param user 用户
     * @return 密码修改成功 : 返回个人主页,失败: 返回密码修改页面
     */

    @PutMapping("/user/modifyPassword")
    public CommonResponse modifyPassword(
            @RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword,
            @SessionAttribute("user") User user){
        if (!EncryptUtils.matches(oldPassword,user.getPassword())){
            return new CommonResponse(true,"原始密码更改错误！！！");
        }
        if (!EncryptUtils.matches(newPassword,user.getPassword())){
            return new CommonResponse(true,"密码未更改！！！");
        }
        user.setPassword(EncryptUtils.execEncrypt(newPassword));
        if (userService.newOrUpdate(user)) {
            return new CommonResponse(true, "密码更改成功！！！");
        }

        return new CommonResponse(false,"系统异常，请稍后再试！！！");
    }

    /**
     * 定向到修改邮箱页面.
     *
     * @param session JSP内置对象
     * @return 修改邮箱页面 string
     */

    @GetMapping("/user/forwardModifyEmail")
    public String forwardModifyEmail(HttpSession session){
        if (!UserUtils.checkLogin()){
            throw new FlightException("您的登录已过期，请重新登录！！！");
        }
        return "modify_email";
    }

    /**
     * 发送邮件，更改邮箱！
     * @param newEmail 新邮箱！
     * @param user 当前用户
     * @param session
     * @return 修改成功！
     */

    @PostMapping("/user/modifyEmail/sendMail")
    public CommonResponse sendEmailToModifyEmail(@RequestParam("newEmail")String newEmail,
                                                 @SessionAttribute("user")User user, HttpSession session){

        if (!UserUtils.checkLogin()){
            throw new FlightException("您的登录已过期，请重新登录！！！");
        }
        log.debug("发送邮件，修改邮箱！");
        if (!StringUtils.hasText(newEmail)){
            return new CommonResponse(false,"请填写邮箱地址！！！");
        }
        if (!MailUtils.isEmail(newEmail)){
            return new CommonResponse(false,"您输入的不是邮箱哒 ^o^||");
        }
        //判断数据库中是否存在
        if (MailUtils.exist(newEmail,userService)){
            return new CommonResponse(false,"邮箱已存在！");
        }
        Calendar calendar  = Calendar.getInstance();
        // 时间是5分钟之后
        calendar.add(Calendar.MINUTE,5);
        String expireTime = new SimpleDateFormat("yyMMddhhmmss").format(calendar.getTime()).toString();
        user.setExpireTime(expireTime);
        userService.newOrUpdate(user);
        String servletName = session.getServletContext().getServletContextName();
        String encodedEmail = EncryptUtils.execEncrypt(newEmail);
        String content =  "<h3>请点击下面的链接完成邮箱更改,五分钟内有效</h3><br>" + "<a href='http://localhost:8080/" + servletName
                + "/user/modifyEmail?email1=" + encodedEmail + "&email2=" + user.getEmail() + "'>http://localhost:8080/"
                + servletName + "/user/modifyEmail/" + newEmail + "</a>";

        String top = "来自第一次旅行项目组";
        MailUtils.sendMail(newEmail,top,content);
        return new CommonResponse(true,"嗖...............到家啦！");

    }

    /**
     * 修改邮箱
     * @param email 用户原来的邮箱
     * @param newEmail 修改的新邮箱
     * @param session the session
     * @return
     */

    @PutMapping("/user/modifyEmail")
    public String modifyEmail(@RequestParam("email2")String email,@RequestParam("email1")String newEmail,
                              HttpSession session){

        log.debug("修改邮箱！");
        String expireTime = new SimpleDateFormat("yyMMddhhmmss").format(Calendar.getInstance().getTime());
        User user = userService.getByEmail(email);
        if (user == null || expireTime.compareTo(user.getExpireTime()) > 0 ){
            throw new FlightException("链接已失效!!!");
        }
        //判断邮箱是否存在
        if (MailUtils.exist(newEmail,userService)){
            throw new FlightException("邮箱已存在！！！");
        }
        user.setEmail(newEmail);
        userService.newOrUpdate(user);
        UserUtils.execSuccess(session,"邮箱更改成功，去主页溜溜吧！");
        user.setExpireTime(expireTime);
        return SUCCESS;
    }

    /**
     * 重定向到忘记密码页面
     * @param email 用户绑定的邮箱
     * @param map the map
     * @return 引导到忘记密码页面
     */
    @GetMapping("/user/forwardForgetPassword")
    public String forwardForgetPassword(
            @RequestParam(value = "email",required = false,defaultValue = " ")String email,
            Map<String,Object> map){
        log.debug("引导到忘记用户密码页面");
        if (!email.equals("")){
            map.put("email","email");
        }
        return "forget_pass";
    }

    /**
     * 发送邮件
     * @param email 未加密的邮箱地址
     * @param session the session
     * @param response jsp内置对象
     * @throws IOException 写入信息失败抛出IO异常
     */

    @PostMapping ("/user/forgetPass/sendMail")
    public void sendMailToResetPass(@RequestParam("email")String email,HttpSession session,
    HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("utf-8");
        if (email.trim()==null || email.trim().equals("")){
            response.getWriter().write("不好意思，暂时找不到收货地址^o^");
            return;
        }
        if (!MailUtils.isEmail(email)){
            response.getWriter().write("请您输入正确的邮箱！！！");
            return;
        }
        String encodedEmail = EncryptUtils.execEncrypt(email);
        User user = userService.getByEmail(encodedEmail);
        if (user == null){
            response.getWriter().write("您输入的邮箱还未注册，请先注册再来登录哦！！！");
            return;
        }
        log.debug("邮箱认证通过！");

        String servletName = session.getServletContext().getServletContextName();
        String content = "<h3>请点击下面的链接继续重置密码,五分钟内有效</h3><br>" + "<a href='http://localhost:8080/" + servletName
                + "/user/forwardForgetPassword?email=" + encodedEmail + "'>http://localhost:8080/" + servletName
                + "/user/forgetPassword/" + email + "</a>";
        String top = "来自第一次飞行的重置密码邮件！！！";
        MailUtils.sendMail(email,top,content);
        Calendar calendar = Calendar.getInstance();
        //时间是5min后
        String expireTime = new SimpleDateFormat("yyMMddhhmmss").format(calendar.getTime().toString());
        user.setExpireTime(expireTime);
        userService.newOrUpdate(user);
        response.getWriter().write("嗖.......... 到家啦 ^v^,查收邮件后再继续操作哦");

    }

    /**
     * 忘记密码
     * @param email 已加密邮箱
     * @param password 新密码
     * @param repassword 重复密码
     * @param session the session
     * @return 密码修改成功：返回个人主页，失败：返回密码修改页面
     */

    @PutMapping("/user/forgetPassword")
    public String forgetPassword(@RequestParam("email") String email,@RequestParam("password") String password,
                                 @RequestParam("repassword") String repassword,HttpSession session){

        log.debug("忘记密码，请重置密码！！！");
        User user = userService.getByEmail(email);

        //获取当前时间，并格式化
        String expireTime = new SimpleDateFormat("yyMMddhhmmss").format(Calendar.getInstance().getTime());
        if (expireTime.compareTo(user.getExpireTime()) > 0){
            log.debug("链接已失效，请重新获取链接！！！");
            throw new FlightException("链接已失效，请重新获取链接！！！");
        }
        if (!password.equals(repassword)){
            throw new FlightException("密码不匹配！！！");
        }
        user.setPassword(EncryptUtils.execEncrypt(password));
        if (!userService.newOrUpdate(user)){
            throw new FlightException();
        }
        UserUtils.execSuccess(session,SUCCESS_CHANGE_PASS);
        return SUCCESS;

    }

    /**
     * 更新用户头像
     * @param user  个人用户
     * @param file 头像文件
     * @param session the session
     * @return 赶回个人主页
     * @throws IOException
     */

    @GetMapping("/user/update")
    public String editHead(User user,@RequestParam("picture") MultipartFile file , HttpSession session) throws IOException {

        log.debug("更新用户头像！！！");
        if (!file.isEmpty()){
            log.debug("保存图片。");
            String pictureUrl = FileUtils.saveFile(file,session,true,StringUtils.hasChinese(user.getUsername()));

            user.setHead(pictureUrl);
        }
        userService.updateHead(user);
        return "redirect:/user/personal";
    }

    /**
     * 表单回显,用于更新用户.
     *
     * @param id UID
     * @param map the map
     * @return 引导用户更新界面
     */

    @GetMapping(value = "/user/{id}")
    public String input(@PathVariable("id") Integer id,Map<String,Object> map){

        User user = (User) map.get("user");

        if (user !=  null && user.getId().equals(id)){
            map.put("user",userService.getById(user.getId()));
            return PageConstant.USER_INPUT;
        }
        throw new FlightException("无法访问用户信息，登录已过期！！！");
    }



    //  由于需要上传文件form 带有属性enctype="multipart/form-data",因此无法使用PUT请求
    @PostMapping("/user/{id}/update")
    public String update(@SessionAttribute(value = "user") @Valid User user,@RequestParam("id") Integer userId,
                         @RequestParam("picture") MultipartFile file,
                         HttpSession session,Map<String,Object> map,
                         @RequestParam("checkFile") String checkFile){
        if (!userService.getByName(user.getUsername()).getId().equals(userId)){
            throw new FlightException("用户名已存在，更新失败");
        }
        //如果是注册需要加密码，而更新是不允许修改密码的

        if (user.getId() == null){
            user.setPassword(EncryptUtils.execEncrypt(user.getPassword()));
        }
        if (StringUtils.hasChinese(user.getUsername())) {
            log.debug("用户名包含中文");

            user.setAlias(new SimpleDateFormat("yyMMddhhmmss").format(new Date()));
        }
        //先保存图片
        if (!file.isEmpty()){
            log.debug("保存图片");
            String pictureUrl;
            try {
                pictureUrl = FileUtils.saveFile(file,session,true,StringUtils.hasChinese(user.getUsername()));
                user.setHead(pictureUrl);
            }catch (IOException e){
                e.printStackTrace();
            }
        }else {
            checkFile = checkFile.substring(checkFile.indexOf("/",2));
            log.debug("checkFile:{}",checkFile);
            user.setHead(checkFile);
        }
        //    判断邮箱是否存在，如果存在说明以后未修改，不要加密
        if (userService.getByEmail(user.getEmail()) == null) {
            user.setEmail(EncryptUtils.execEncrypt(user.getEmail()));
        }
        Integer id = user.getId();
        if (userService.newOrUpdate(user)) {
            user = userService.login(user);
            if (user.getId() == null) {
                throw new FlightException("系统出错了,操作被取消,请返回重新操作");
            }
            map.put("user", user);
            //更新用户应返回到个人主页
            if (id != null) {
                return "redirect:/user/personal";
            }
            //注册用户返回到主页
            return "redirect:/home";
        }
        throw new FlightException("系统出错了,操作被取消,请返回重新操作");
    }

}
