package com.gakki.love.service.impl;

import com.gakki.love.domain.Comment;
import com.gakki.love.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: YuAn
 * \* Date: 2017/11/28
 * \* Time: 20:40
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */

@Service
public class CommentServiceImpl implements com.gakki.love.service.CommentService {

    @Resource
    private CommentRepository commentRepository;

    @Override
    @Transactional
    public void Comment(Comment comment){
        commentRepository.saveAndFlush(comment);
    }

    @Override
    @Transactional
    public boolean deleteComment(Comment comment){
        Comment c = commentRepository.getByIdAndTopic(comment.getId(),comment.getTopic());

        if (c == null){
            return false;
        }
        commentRepository.delete(comment.getId());
    return true;
    }
}