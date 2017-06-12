package com.gakki.love.repository;

import com.gakki.love.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 林漠 on 2017/6/8.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {

    /*
    根据用户名和密码获取一个用户
     */

    User getByUsernameAndPassword(String username,String password);

    /*
    根据邮箱获取去用户
     */

    User getByEmailAndPassword(String email,String Password);


    /*
    根据用户Id获取用户信息
     */
   User getById(Integer id);

   /*
   根据用户名获取用户信息
    */

   User getByUsername(String username);

   /*
   根据邮箱获取用户信息
    */
   User getByEmail(String email);

   /*
   统计发表分享最多的用户,并联合user表查询相关信息.
    */

    @Query(value = "select *from user join (select user,count(id) as n from topic group by user) as t on "
            + "t.user=id order by t.n desc limit :start,:size", nativeQuery = true)
   List<User> getByIdJoinTopicUserId(@Param("start") int start, @Param("size") int size);
}
