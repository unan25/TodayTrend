package com.todaytrend.postservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Post {

    @Id
    private Integer post_id;

}
