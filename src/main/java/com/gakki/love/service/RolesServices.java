package com.gakki.love.service;

import com.gakki.love.domain.Roles;
import org.springframework.transaction.annotation.Transactional;

public interface RolesServices {
    @Transactional
    Roles saveOrUpdate(Roles roles);

    @Transactional(readOnly = true)
    Roles getByName(String name);

    @Transactional(readOnly = true)
    long countRole();
}
