package com.gakki.love.repository;

import com.gakki.love.domain.Topic;
import com.gakki.love.domain.User;
import org.apache.commons.collections.iterators.EmptyListIterator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by 林漠 on 2017/6/12.
 */
public interface TopicRepository extends PagingAndSortingRepository<Topic,Integer> {
    /*
    获取指定用户分享数
    返回指定用户分享的总数
     */
    @Query("select count (t.id) from Topic t where t.user= :user")
    int getCountByUser(@Param("user")User user);

    /*
    通过用户id,获取topic.
   *
   * @param user the user
   * @param pageable 分页参数
   * @return 返回分享的集合topics
     */

    Page<Topic> getByUserOrderByDateDesc(User user, Pageable pageable);

    /*
    通过分享ID，获取topic
     */
    Topic getById(Integer topic_id);

    /*
    分享的点赞次数+1
     */
    @Modifying
    @Query("update Topic t set t.upCounts = (t.upCounts +1) where t.id = :topic_id")
    void updateTopicPraise(@Param("topic_id") Integer topic_id);

    /*
    分享的点赞次数-1
     */
    @Modifying
    @Query("update Topic t set t.upCounts = (t.upCounts -1) where t.id = :topic_id")
    void updateTopicNotPraise(@Param("topic_id") Integer topic_id);

    /*
    通过用户ID和topic ID获取topic
     */
    Topic getByIdAAndUser(Integer topic_id,User user);

    /*
    非空标签的集合
     */
    @Query(value = "select tags from (select tags ,count(tags) an n from topic where tags!='' group by" +
            " tags) as t order by t.n desc",nativeQuery = true)
    List<String> getOrderedTags();

    /*
    获取热门标签,并按照出现的次数排序
     */
    @Query(value = "select tags from (select tags ,count(tags) an n from topic where tags!='' group by" +
            " tags) as t order by t.n desc limit 5",nativeQuery = true)
    List<String> getOrderedTagsLimit();

    /*
    通过标签获取分享
     */
    @Query(value = "select * from topic where tags like % :tags % limit :start,:size",nativeQuery = true)
    List<Topic> getByTagsLimit(@Param("tags") String tags ,@Param("start") int start );

    /*
    获取标签的总数.
     */
    @Query(value = "select count(id) from Topic where tags like % :tags%" ,nativeQuery = true)
    long getCountByTags(@Param("tags") String tags);

}
