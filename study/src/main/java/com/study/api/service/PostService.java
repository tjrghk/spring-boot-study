package com.study.api.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.study.api.domain.Post;
import com.study.api.exception.PostNotFound;
import com.study.api.repository.PostRepository;
import com.study.api.request.PostCreate;
import com.study.api.request.PostEdit;
import com.study.api.request.PostSearch;
import com.study.api.response.PostResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
    
    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {
        Post post = Post.builder()
            .title(postCreate.getTitle())
            .content(postCreate.getContent())
            .build();

        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new PostNotFound());
        
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream()
                .map(post -> new PostResponse(post)).collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new PostNotFound());

        post.change(postEdit.getTitle(), postEdit.getContent());
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new PostNotFound());

        postRepository.delete(post);
    }
}
