package com.gakki.love.repository;

import com.gakki.love.domain.Follow;
import com.gakki.love.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Created by 林漠 on 2017/6/12.
 */
public interface FollowRepository extends JpaRepository<Follow,Integer> {

    /*
    通过用户和关注用户获取关注信息
     */
    Follow getByUserAndFollowedUser(User user,User followedUser);

    /*
    获取指定用户关注的用户总数
     */

    @Query("select count(f.id) from Follow f where f.user= :user")
    int getCountByUser(@Param("user")User user);

    /*
    删除指定的相关记录
     */
    @Modifying
    @Query("delete from Follow f where f.user=:user and f.followedUser=:followedUser")
    void  deleteByUserAndFollowedUser(@Param("user") User user, @Param("followedUser")User followedUser);

    /*
    获取所有关注了当前用户的follow
     */

    @Query(value = "select f.user from follow f where f.followed_user= :user",nativeQuery = true)
    List<Integer> getUserByFollowedUser(@Param("user")User user);
}
