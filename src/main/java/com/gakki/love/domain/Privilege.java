package com.gakki.love.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by 林漠 on 2017/6/7.
 */
@Entity
@Data
public class Privilege {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

}
