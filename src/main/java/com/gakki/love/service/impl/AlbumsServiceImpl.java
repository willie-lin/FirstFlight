package com.gakki.love.service.impl;

import com.gakki.love.domain.Albums;
import com.gakki.love.repository.AlbumsRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: YuAn
 * \* Date: 2017/11/26
 * \* Time: 0:44
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class AlbumsServiceImpl implements com.gakki.love.service.AlbumsService {

    @Resource
    private AlbumsRepository albumsRepository;


    @Override
    public Albums save(Albums albums){
        return albumsRepository.saveAndFlush(albums);
    }
}