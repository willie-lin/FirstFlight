package com.gakki.love.service;

import com.gakki.love.domain.Topic;
import com.gakki.love.domain.User;
import com.gakki.love.utils.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 林漠 on 2017/6/27.
 */
public interface TopicService {
    Topic publish(Topic topic);

    @Transactional
    void deleteTopic(Topic topic);

    @Transactional(readOnly = true)
    List<String> getAllTags();

    @Transactional(readOnly = true)
    Page<Topic> getTopicsPage(int page, int size);

    @Transactional(readOnly = true)
    Page<Topic> getTopicsPageOrderByFavourite(int page, int size);

    @Transactional(readOnly = true)
    Pagination getTopicsPageByTags(String tag, int page, int size);

    @Transactional(readOnly = true)
    Page<Topic> getTopicsPageByUserId(User user, int page, int size);

    @Transactional(readOnly = true)
    Topic getTopicById(Integer topic_id);

    @Transactional(readOnly = true)
    boolean getTopicByUserIdAndTopicId(Integer topic_id, User user);

    @Transactional(readOnly = true)
    int getTopicNumber(User user);

    @Transactional(readOnly = true)
    long getCount();

    long getCountByTag(String tag);
}
