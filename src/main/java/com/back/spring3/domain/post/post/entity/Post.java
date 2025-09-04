package com.back.spring3.domain.post.post.entity;

import com.back.spring3.domain.post.comment.entity.Comment;
import com.back.spring3.global.jpa.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Post extends BaseEntity {
    private String title;
    private String content;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Comment addComment(String content) {
        Comment comment = new Comment(content, this);
        this.comments.add(comment);

        return comment;
    }

    public void deleteComment(Long commentId) {
        comments.stream()
                .filter(c -> c.getId().equals(commentId))
                .findFirst()
                .ifPresent(comments::remove);
    }
}