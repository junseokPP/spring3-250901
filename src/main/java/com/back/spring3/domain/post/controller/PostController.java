package com.back.spring3.domain.post.controller;

import com.back.spring3.domain.post.entity.Post;
import com.back.spring3.domain.post.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    private String getWriteFormHtml(String errorMessage,String title,String content){
        return """
                <ul style="color:red">%s</ul>
                
                <form method="POST" action="/posts/doWrite">
                    <input type="text" name="title" value="%s" autoFocus>
                    <br>
                    <textarea name="content">%s</textarea>
                    <br>
                    <input type="submit" value="작성">
                </form>
                
                <script>
                    const li = document.querySelect("ul li");
                    const errorFieldName = li.dataset.errorFieldName;
                    
                    if(errorFieldName.length > 0 )){
                        const form = document.querySelector("form");
                        form[errorFieldName].focus();
                    }
                </script>
                """.formatted(errorMessage,title,content);
    }

    public PostController() {
    }

    @AllArgsConstructor
    @Getter
    public static class PostWriteForm {
        @NotBlank(message = "1-제목을 입력해주세요.")
        @Size(min = 2, max = 10 ,message = "2-제목은 2글자 이상 10글자 이하로 입력해주세요.")
        private String title;

        @NotBlank(message = "3-내용을 입력해주세요.")
        @Size(min = 2, max = 100 ,message = "4-내용을 2글자 이상 100글자 이하로입력해주세요.")
        private String content;
    }


    @GetMapping("/posts/write")
    @ResponseBody
    public String write(){
        return getWriteFormHtml("","","");
    }

    @PostMapping("/posts/doWrite")
    @ResponseBody
    public String doWrite(
            @Valid PostWriteForm form, BindingResult bindingResult
            ){

        if(bindingResult.hasErrors()){

            String fieldName = "title";

            FieldError fieldError = bindingResult.getFieldError();

            String errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(field -> field.getField() + " - " + field.getDefaultMessage())
                    .map(message -> message.split("-"))
                    .map(bits ->"""
                            "<!-- %s --><li data-error-field-name="%s">%s</li>"
                            """.formatted(bits[1], bits[0],bits[2]))
                    .sorted()
                    .collect(Collectors.joining("<br>"));

            return getWriteFormHtml(errorMessages,form.title,form.content,fieldName);
        }

//        if(title.isBlank()) return getWriteFormHtml("제목을 입력해주세요.",title,content,"title");
//        if(title.length() < 2) return getWriteFormHtml("제목은 2글자 이상 적어주세요.",title,content,"title");
//        if(title.length() > 10) return getWriteFormHtml("제목은 10글자 이상 넘을 수 없습니다.",title,content,"title");
//        if(content.isBlank()) return getWriteFormHtml("내용을 입력해주세요.",title,content,"content");
//        if(content.length() < 2) return getWriteFormHtml("내용을 2글자 이상 적어주세요.",title,content,"content");
//        if(content.length() > 100) return getWriteFormHtml("내용을 100글자 이상 넘을 수 없습니다.",title,content,"content");

        Post post = postService.write(form.title,form.content);

        return "%d번 글이 작성되었습니다.".formatted(post.getId());
    }
}
