package com.study.api.controller;



import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.study.api.request.PostCreate;
import com.study.api.request.PostEdit;
import com.study.api.request.PostSearch;
import com.study.api.response.PostResponse;
import com.study.api.service.PostService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class PostController {
    
    private final PostService postService;
    
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate postCreate) {
        postCreate.validate();
        postService.write(postCreate);
    }

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        return postService.get(postId);
    }

    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public void post(@PathVariable Long postId, @RequestBody @Valid PostEdit postCreate) {
        postService.edit(postId, postCreate);
    }

    @DeleteMapping("/posts/{postId}")
    public void post(@PathVariable Long postId) {
        postService.delete(postId);
    }
}
