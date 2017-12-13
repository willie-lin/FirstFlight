package com.gakki.love.service.impl;

import com.gakki.love.domain.Notify;
import com.gakki.love.domain.User;
import com.gakki.love.repository.NotifyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.plugin2.message.Message;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Set;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: YuAn
 * \* Date: 2017/11/27
 * \* Time: 22:37
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class NotifyServiceImpl implements com.gakki.love.service.NotifyService {

    @Resource
    private NotifyRepository notifyRepository;

    @Override
    @Transactional(readOnly = true)
    public Set<Notify> getNotifies(User user, String hasCheck){

        return notifyRepository.getByNotifiedUserAndHasCheckOrderByDateDesc(user,hasCheck);

    }
    @Override
    @Transactional(readOnly = true)
    public Notify getNotifyById(Notify notify){
        Notify n = notifyRepository.getById(notify.getId());
        return n == null ? new Notify() : n;
    }


    @Override
    @Transactional(readOnly = true)
    public int getNotifiedNumber(User user){

        int number = notifyRepository.getCountByNotifiedUser(user);
        return number > 0 ? number : 0;
    }

    @Override
    @Transactional
    public boolean notifyFollowUser(User user, User followUser, String message){

        Notify notify = new Notify();
        notify.setContent(message);
        notify.setDate(new Date());
        notify.setHasCheck("false");
        notify.setNotifiedUser(followUser);
        notify.setUser(user);
        notifyRepository.saveAndFlush(notify);
        return notify.getId() != null;
    }

    @Override
    @Transactional
    public boolean sendPrivMess(Notify notify){
        return notifyRepository.saveAndFlush(notify)!= null;
    }

    @Override
    @Transactional
    public boolean updateNotifu(Notify notify){

        return notifyRepository.saveAndFlush(notify) != null;
    }

    @Override
    @Transactional
    public boolean delete(Notify notify){
        notifyRepository.delete(notify);
        return true;
    }

    @Override
    public Notify getByIdAndNotifiedUserId(Integer notify_id, User notified_user){
        return notifyRepository.getByIdAndNotifiedUser(notify_id,notified_user);

    }

}