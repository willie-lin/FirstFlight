package com.gakki.love.service;

import com.gakki.love.domain.Notify;
import com.gakki.love.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface NotifyService {
    @Transactional(readOnly = true)
    Set<Notify> getNotifies(User user, String hasCheck);

    @Transactional(readOnly = true)
    Notify getNotifyById(Notify notify);

    @Transactional(readOnly = true)
    int getNotifiedNumber(User user);

    @Transactional
    boolean notifyFollowUser(User user, User followUser, String message);

    @Transactional
    boolean sendPrivMess(Notify notify);

    @Transactional
    boolean updateNotifu(Notify notify);

    @Transactional
    boolean delete(Notify notify);

    Notify getByIdAndNotifiedUserId(Integer notify_id, User notified_user);
}
