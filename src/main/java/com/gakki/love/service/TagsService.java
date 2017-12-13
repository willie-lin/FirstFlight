package com.gakki.love.service;

import com.gakki.love.domain.Tags;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TagsService {
    @Transactional
    Tags saveOrUpdate(Tags tags);

    @Transactional(readOnly = true)
    Tags getById(Integer id);

    Tags findOne(Integer id);

    @Transactional(readOnly = true)
    Tags getByName(String tagName);

    @Transactional(readOnly = true)
    List<Tags> getAllTags();

    @Transactional(readOnly = true)
    List<Tags> getTagsPage(int page, int size);
}
