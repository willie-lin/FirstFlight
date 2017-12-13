package com.gakki.love.service;

import com.gakki.love.domain.Topic;
import com.gakki.love.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FavouriteService {
    @Transactional
    boolean favourite(User user, Topic topic);

    @Transactional(readOnly = true)
    List<Integer> getFavouriteTopicIds(User user, User other);

    @Transactional
    boolean notFavourite(User user, Topic topic);
}
