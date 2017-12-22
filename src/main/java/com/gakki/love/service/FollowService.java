package com.gakki.love.service;

import com.gakki.love.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FollowService {
    @Transactional(readOnly = true)
    int getFollowedNumber(User user);

    @Transactional(readOnly = true)
    List<User> getFollowedUser(User user);

    @Transactional
    boolean follow(User user, User followedUser);

    @Transactional(readOnly = true)
    boolean isFollowed(User user, User followedUser);

    @Transactional
    boolean notFollowed(User user, User followedUser);
}
