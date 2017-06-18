package com.gakki.love.repository;

import com.gakki.love.domain.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 林漠 on 2017/6/12.
 */@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack,Integer> {
}
