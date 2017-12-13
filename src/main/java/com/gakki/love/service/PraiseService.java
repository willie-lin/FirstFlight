package com.gakki.love.service;

import com.gakki.love.domain.Topic;
import com.gakki.love.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PraiseService {
    @Transactional
    List<Integer> getPraiseTopicIds(User user, User other);

    @Transactional
    boolean praise(User user, Topic topic);

    boolean notPraise(User user, Topic topic);
}
