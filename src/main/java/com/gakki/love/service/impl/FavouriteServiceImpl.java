package com.gakki.love.service.impl;

import com.gakki.love.domain.Favourite;
import com.gakki.love.domain.Topic;
import com.gakki.love.domain.User;
import com.gakki.love.repository.FavouriteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: YuAn
 * \* Date: 2017/11/28
 * \* Time: 21:07
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */

@Slf4j
@Service
public class FavouriteServiceImpl implements com.gakki.love.service.FavouriteService {

    @Resource
    private FavouriteRepository favouriteRepository;

    @Override
    @Transactional
    public boolean favourite(User user, Topic topic){

        Favourite f = favouriteRepository.getByUserAndTopic(user,topic);

        if (f != null){
            log.debug("重复添加收藏！");
            return false;
        }

        Favourite favourite = new Favourite();
        favourite.setUser(user);
        favourite.setTopic(topic);
        favouriteRepository.saveAndFlush(favourite);
        return true;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Integer> getFavouriteTopicIds(User user, User other){
        return favouriteRepository.getTopicIdsByUserIdAndOtherId(user.getId(),other.getId());
    }

    @Override
    @Transactional
    public boolean notFavourite(User user, Topic topic){
        favouriteRepository.deleteByUserAndTopic(user,topic);
        return true;
    }
}