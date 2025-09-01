package com.back.spring3.domain.post.controller;

import com.back.spring3.domain.post.entity.Post;
import com.back.spring3.domain.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {

    private PostService postService;

    public PostController() {
    }

    @GetMapping("/posts/write")
    @ResponseBody
    public String write(){
        return """
                <form action="/posts/doWrite">
                    <input type="text" name="title">
                    <br>
                    <textarea name="content"></textarea>
                    <br>
                    <input type="submit" value="작성">
                </form>
                """;
    }

    @GetMapping("/posts/doWrite")
    @ResponseBody
    public String doWrite(String title, String content){

        Post post = postService.write(title,content);

        return "%d번 글이 작성되었습니다.".formatted(post.getId());
    }
}
