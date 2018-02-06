package com.gakki.love.repository;

import com.gakki.love.domain.Praise;
import com.gakki.love.domain.Topic;
import com.gakki.love.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by 林漠 on 2017/6/12.
 */
@Repository
public interface PraiseRepository extends JpaRepository<Praise,Integer> {

    /*
    获取点赞信息
     */
    Praise getByUserAndTopic(User user, Topic topic);

    /*
    通过topicid删除所有用户点赞信息
     */

    @Modifying
    @Query("delete from Praise p where p.topic = :topic")
    void deleteByTopic(@Param("topic") Topic topic);

    /*
    通过topicid和userid删除点赞信息
     */

    @Modifying
    @Query("delete from Praise p where p.topic= :topic and p.user= :user")
    void deleteByTopicAAndUser(@Param("topic") Topic topic,@Param("user")User user);

    /*
    该方法获取用户当前浏览他人的分享的点赞信息
     */


  @Query(value = "select topic from praise where user= :user_id and topic in (select id from topic where user= :other_id)", nativeQuery = true)
    List<Integer> getTopicIdByUserIdAndOtherrId(@Param("user_id") Integer user_id,@Param("other_id")Integer other_id);


}
