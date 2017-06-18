package com.gakki.love.repository;

import com.gakki.love.domain.Notify;
import com.gakki.love.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import java.util.Set;

/**
 * Created by 林漠 on 2017/6/12.
// */

@Repository
public interface NotifyRepository extends JpaRepository<Notify,Integer> {

    /*
    获取已读/未读消息按降序排列
     */

    Set<Notify> getByNotifiedUserAndHasCheckOrderByDateDesc(User user, String hasCheck);

    /*
    通过Id 获取消息
     */
    Notify getById(Integer notifyId);

    /*
    获取指定用户的消息数目
     */

    @Query("select  count(n.id) from Notify  n where n.notifiedUser= :notifiedUser and n.hasCheck='false '")
    int getCountByNotifiedUser(@Param("notifiedUser") User notifiedUsetr);

    /*
    通过ID和被通知用户ID获取消息
     */
    Notify getByIdAndNotifiedUser(Integer id,User notifiedUser);
}
