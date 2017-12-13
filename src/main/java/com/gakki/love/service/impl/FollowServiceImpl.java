package com.gakki.love.service.impl;

import com.gakki.love.domain.Follow;
import com.gakki.love.domain.User;
import com.gakki.love.repository.FollowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.SqlReturnUpdateCount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.security.sasl.RealmCallback;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: YuAn
 * \* Date: 2017/11/27
 * \* Time: 22:16
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Slf4j
@Service
public class FollowServiceImpl implements com.gakki.love.service.FollowService {

    @Resource
    private FollowRepository followRepository;

    @Override
    @Transactional(readOnly = true)
    public int getFollowedNumber(User user){
        int number = followRepository.getCountByUser(user);
        return number > 0 ? number:0;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getFollowedUser(User user){

        List<Integer> userIds = followRepository.getUserByFollowedUser(user);
        List<User> users = new ArrayList<User>();

        for (Integer userId : userIds){
            User u = new User();
            u.setId(userId);
            users.add(u);
        }
        return users;
    }

    @Override
    @Transactional
    public boolean follow(User user, User followedUser){
        if (isFollowed(user,followedUser)){
            log.debug("重复添加关注");
            return false;
        }
        Follow follow = new Follow();
        follow.setUser(user);
        follow.setFollowedUser(followedUser);
        followRepository.saveAndFlush(follow);

        return true;
    }


    @Override
    @Transactional(readOnly = true)
    public boolean isFollowed(User user, User followedUser){
        Follow f = followRepository.getByUserAndFollowedUser(user,followedUser);
        return (f != null) ? true : false;
    }

    @Override
    @Transactional
    public boolean notFollowed(User user, User followedUser){
        followRepository.deleteByUserAndFollowedUser(user,followedUser);
        return true;
    }


}