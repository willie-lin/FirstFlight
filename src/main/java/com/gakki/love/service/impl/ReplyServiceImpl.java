package com.gakki.love.service.impl;

import com.gakki.love.domain.Reply;
import com.gakki.love.repository.ReplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by 林漠 on 2017/6/25.
 */
@Service
public class ReplyServiceImpl implements com.gakki.love.service.ReplyService {

    @Resource
    private ReplyRepository replyRepository;

    @Override
    @Transactional
    public boolean replyComment(Reply reply){

        replyRepository.saveAndFlush(reply);

        return true;
    }

    @Override
    @Transactional
    public void replyReply(Reply reply){

        replyRepository.saveAndFlush(reply);
    }

    @Override
    @Transactional
    public boolean deleteReply(Integer id){

        replyRepository.delete(id);

        return true;
    }
}
