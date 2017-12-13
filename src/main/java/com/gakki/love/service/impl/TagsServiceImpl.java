package com.gakki.love.service.impl;

import com.gakki.love.domain.Tags;
import com.gakki.love.repository.TagsRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/*
 * Created by 林漠 on 2017/6/23.
 * Tag
 */
@Service
public class TagsServiceImpl implements com.gakki.love.service.TagsService {

    @Resource
    private TagsRepository tagsRepository;

    @Override
    @Transactional
    public synchronized Tags saveOrUpdate(Tags tags){

        return tagsRepository.saveAndFlush(tags);
    }

    @Override
    @Transactional(readOnly = true)
    public Tags getById(Integer id){
        return null;
    }


    @Override
    public Tags findOne(Integer id){
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Tags getByName(String tagName){
        return tagsRepository.getTagsByTagName(tagName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tags> getAllTags(){
        return tagsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tags> getTagsPage(int page, int size){

        Pageable pageable = new PageRequest(page,size,new Sort(new Sort.Order(Sort.Direction.DESC,"createTime")));

        return tagsRepository.findAll(pageable).getContent();
    }



}
