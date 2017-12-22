package com.gakki.love.service;

import com.gakki.love.domain.FeedBack;
import com.gakki.love.domain.User;
import com.gakki.love.utils.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 林漠 on 2017/6/14.
 */
public interface UserService {
    @Transactional(readOnly = true)
    Pagination<User> getTopPeople(int page, int size);

    @Transactional(readOnly = true)
    User login(User user);

    @Transactional
    boolean newOrUpdate(User user);

    @Transactional(readOnly = true)
    User getById(Integer id);

    @Transactional(readOnly = true)
    User getByName(String username);

    @Transactional
    void updateHead(User user);

    @Transactional(readOnly = true)
    User getByEmail(String email);

    @Transactional(readOnly = false)
    boolean feedback(FeedBack feedBack);
}
