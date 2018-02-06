package com.gakki.love.service.impl;

import com.gakki.love.domain.Praise;
import com.gakki.love.domain.Reply;
import com.gakki.love.domain.Topic;
import com.gakki.love.domain.User;
import com.gakki.love.repository.PraiseRepository;
import com.gakki.love.repository.ReplyRepository;
import com.gakki.love.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Created by 林漠 on 2017/7/2.
 */
@Service
public class PraiseServiceImpl implements com.gakki.love.service.PraiseService {

    @Resource
    private PraiseRepository praiseRepository;

    @Resource
    private TopicRepository topicRepository;


    @Override
    @Transactional
    public List<Integer> getPraiseTopicIds(User user, User other){

        return praiseRepository.getTopicIdByUserIdAndOtherrId(user.getId(),other.getId());
    }

    /**
     *
     * @param user
     * @param topic
     * @return
     */

    @Override
    @Transactional
    public boolean praise(User user, Topic topic){
        //判断用户是否点过赞

        Praise p  = praiseRepository.getByUserAndTopic(user,topic);

//        如果不为空说明用户已点过赞,取消本次操作
        if (p != null){
            return false;
        }
        //将分享点赞次数加1

        topicRepository.updateTopicPraise(topic.getId());
//        更新点赞表
        Praise praise = new Praise();
        praise.setUser(user);
        praise.setTopic(topic);
        praiseRepository.saveAndFlush(praise);

        return true;

    }

    @Override
    public boolean notPraise(User user, Topic topic){
        //判断是否以点过赞👍
        Praise p = praiseRepository.getByUserAndTopic(user,topic);
        if (p != null){
            return false;
        }
//        如果点过，将点赞次数减1，取消点赞

        topicRepository.updateTopicNotPraise(topic.getId());
        praiseRepository.deleteByTopicAAndUser(topic,user);
        return true;
    }

//    public boolean replayComment(Reply reply){
//
////        replyRepository.saveAndFlush(reply);
//
//        return true;
//            }

}
