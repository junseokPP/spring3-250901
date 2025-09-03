package com.back.spring3.domain.post.service;

import com.back.spring3.domain.post.entity.Post;
import com.back.spring3.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post write(String title, String content) {
        Post post = new Post(title, content);
        return postRepository.save(post);
    }

    public long count() {
        return postRepository.count();
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }
}
