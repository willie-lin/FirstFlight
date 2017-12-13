package com.gakki.love.service;

import com.gakki.love.domain.Comment;
import org.springframework.transaction.annotation.Transactional;

public interface CommentService {
    @Transactional
    void Comment(Comment comment);

    @Transactional
    boolean deleteComment(Comment comment);
}
