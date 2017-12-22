package com.gakki.love.repository;

import com.gakki.love.domain.Favourite;
import com.gakki.love.domain.Topic;
import com.gakki.love.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 林漠 on 2017/6/12.
 */
@Repository
public interface FavouriteRepository extends JpaRepository<Favourite,Integer> {

    /*
    通过用户和分享获取收藏信息
     */

    Favourite getByUserAndTopic(User user, Topic topic);

    /*
    删除指定的收藏数
     */
    @Modifying
    @Query("delete from Favourite f where f.user=:user and f.topic= :topic")
    void  deleteByUserAndTopic(@Param("user") User user,@Param("topic")Topic topic);

    /*
    获取所有,被浏览用户分享的并且当前用户收藏的,分享ID
     */
    @Query(value = "select topic from favourite where user = :user_id and topic  in (select id from topic where  user=:topic_id )",nativeQuery = true)
    List<Integer> getTopicIdsByUserIdAndOtherId(@Param("user_id") Integer User_id,@Param("other_id") Integer other_id);


}
