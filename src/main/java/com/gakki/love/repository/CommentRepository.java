package com.gakki.love.repository;

import com.gakki.love.domain.Comment;
import com.gakki.love.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 林漠 on 2017/6/12.
 */
@Repository
public interface CommentRepository  extends JpaRepository<Comment,Integer>{

    /*
    通过ID和分享UID获取评论
     */

    Comment getByIdAndTopic(Integer id,Topic topic);
}
