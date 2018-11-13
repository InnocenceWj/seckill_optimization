package com.wj.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class SeckillUser implements Serializable {
    private Long id;

    private String nickname;

    private String password;

    private String salt;

    private String head;

    private Date registerDate;

    private Date lastLoginDate;

    private Integer loginCount;

    private static final long serialVersionUID = 1L;

}