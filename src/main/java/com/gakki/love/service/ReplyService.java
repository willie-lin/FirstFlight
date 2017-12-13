package com.gakki.love.service;

import com.gakki.love.domain.Reply;
import org.springframework.transaction.annotation.Transactional;

public interface ReplyService {
    @Transactional
    boolean replyComment(Reply reply);

    @Transactional
    void replyReply(Reply reply);

    @Transactional
    boolean deleteReply(Integer id);
}
