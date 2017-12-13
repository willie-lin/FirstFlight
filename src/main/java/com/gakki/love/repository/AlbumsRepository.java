package com.gakki.love.repository;

import com.gakki.love.domain.Albums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: YuAn
 * \* Date: 2017/11/27
 * \* Time: 22:57
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Repository
public interface AlbumsRepository extends JpaRepository<Albums, Integer> {
}