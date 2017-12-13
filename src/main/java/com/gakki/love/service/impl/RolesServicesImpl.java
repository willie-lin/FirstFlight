package com.gakki.love.service.impl;

import com.gakki.love.domain.Roles;
import com.gakki.love.repository.RolesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by 林漠 on 2017/6/23.
 */
@Service
public class RolesServicesImpl implements com.gakki.love.service.RolesServices {

    @Resource
    private RolesRepository rolesRepository;

    @Override
    @Transactional
    public Roles saveOrUpdate(Roles roles){
        return rolesRepository.saveAndFlush(roles);
    }

    @Override
    @Transactional(readOnly = true)
    public Roles getByName(String name){
        return rolesRepository.getByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public long countRole(){
        return rolesRepository.count();
    }
}
