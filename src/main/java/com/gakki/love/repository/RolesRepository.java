package com.gakki.love.repository;

import com.gakki.love.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;

/**
 * Created by 林漠 on 2017/6/23.
 */
@Repository
public interface RolesRepository extends JpaRepository<Roles,Integer> {

    /*
    获取名称
     */

    Roles getByName(String name);

}
