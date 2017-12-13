package com.gakki.love.repository;

import com.gakki.love.domain.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 林漠 on 2017/6/23.
 */

@Repository
public interface TagsRepository extends JpaRepository<Tags,Integer> {

    Tags getTagsByTagName(String tagName);
}
