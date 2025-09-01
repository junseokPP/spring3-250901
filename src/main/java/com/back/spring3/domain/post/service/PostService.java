package com.back.spring3.domain.post.service;

import com.back.spring3.domain.post.entity.Post;
import com.back.spring3.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(String title, String content) {

        Post post = new Post(title, content);
        postRepository.save(post);
    }

    public long count() {
        return postRepository.count();
    }
}
