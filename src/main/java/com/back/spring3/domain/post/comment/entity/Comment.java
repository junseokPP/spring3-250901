package com.back.spring3.domain.post.comment.entity;

import com.back.spring3.domain.post.post.entity.Post;
import com.back.spring3.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {

    private String content;

    @ManyToOne
    private Post post;
}
