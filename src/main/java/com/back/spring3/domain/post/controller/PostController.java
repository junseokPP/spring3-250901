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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @AllArgsConstructor
    @Getter
    public static class PostWriteForm {
        @NotBlank(message = "1-제목을 입력해주세요.")
        @Size(min = 2, max = 10, message = "2-제목은 2글자 이상 10글자 이하로 입력해주세요.")
        private String title;

        @NotBlank(message = "3-내용을 입력해주세요.")
        @Size(min = 2, max = 100, message = "4-내용은 2글자 이상 100글자 이하로 입력해주세요.")
        private String content;
    }


    @GetMapping("/posts/write")
    public String write(@ModelAttribute("form") PostWriteForm form) {
        return "post/write";
    }

    @PostMapping("/posts/doWrite")
    public String doWrite(
            @ModelAttribute("form") @Valid PostWriteForm form, BindingResult bindingResult,
            Model model
    ) {

        if(bindingResult.hasErrors()) {
            // 스트림
            String errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(field -> field.getField() + "-" + field.getDefaultMessage())
                    .map(message -> message.split("-"))
                    .map(bits -> """
                            <!-- %s --><li data-error-field-name="%s">%s</li>
                            """.formatted(bits[1], bits[0], bits[2]))
                    .sorted()
                    .collect(Collectors.joining("\n"));

            model.addAttribute("errorMessages", errorMessages);

            return "post/write";
        }


//        if(title.isBlank()) return getWriteFormHtml("제목을 입력해주세요.", title, content, "title");
//        if(title.length() < 2) return getWriteFormHtml("제목은 2글자 이상 적어주세요.", title, content, "title");
//        if(title.length() > 10) return getWriteFormHtml("제목은 10글자 이상 넘을 수 없습니다.", title, content, "title");
//        if(content.isBlank()) return getWriteFormHtml("내용을 입력해주세요.", title, content, "content");
//        if(content.length() < 2) return getWriteFormHtml("내용은 2글자 이상 적어주세요.", title, content, "content");
//        if(content.length() > 100) return getWriteFormHtml("내용은 100글자 이상 넘을 수 없습니다.", title, content, "content");

        Post post = postService.write(form.title, form.content);

        model.addAttribute("id", post.getId());
        return "post/writeDone";
    }
}
