package com.gakki.love.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.xml.crypto.Data;
import java.util.Date;

/**
 * Topic 套图
 *
 * Created by 林漠 on 2017/6/23.
 */

@Entity
public class Albums {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String url;

    @CreationTimestamp
    private Date createTime;
}
