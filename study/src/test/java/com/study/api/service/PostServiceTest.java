package com.study.api.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.study.api.domain.Post;
import com.study.api.exception.PostNotFound;
import com.study.api.repository.PostRepository;
import com.study.api.request.PostCreate;
import com.study.api.request.PostEdit;
import com.study.api.request.PostSearch;
import com.study.api.response.PostResponse;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }
    
    @Test
    @DisplayName("글 작성")
    void test1() {

        PostCreate postCreate = PostCreate.builder()
            .title("제목입니다.")
            .content("내용입니다.")
            .build();

        postService.write(postCreate);

        Post post = postRepository.findAll().get(0);
        Assertions.assertEquals("제목입니다.", post.getTitle());
        Assertions.assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {  
        Post requestPost = Post.builder()
            .title("foo")
            .content("bar")
            .build();
        postRepository.save(requestPost);

        PostResponse response = postService.get(requestPost.getId());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1L, postRepository.count());
        Assertions.assertEquals("foo", response.getTitle());
        Assertions.assertEquals("bar", response.getContent());
    }

    @Test
    @DisplayName("글 1개 조회 에러")
    void test2_1() {  
        Post requestPost = Post.builder()
            .title("foo")
            .content("bar")
            .build();
        postRepository.save(requestPost);

        assertThrows(PostNotFound.class, () -> {
            postService.get(requestPost.getId() + 1L);
        });
    }

    @Test
    @DisplayName("글 여러개 조회")
    void test3() {  
        List<Post> requestPosts = IntStream.range(0, 20)
            .mapToObj(i -> Post.builder()
                            .title("foo" + i)
                            .content("bar" + i)
                            .build()
            ).collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
            .page(1)
            .build();
        List<PostResponse> posts = postService.getList(postSearch);

        Assertions.assertEquals(10L, posts.size());
        Assertions.assertEquals("foo19", posts.get(0).getTitle());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test4() {  
        Post post = Post.builder()
            .title("foo")
            .content("bar")
            .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
            .title("fooo")
            .content("bar")
            .build();

        postService.edit(post.getId(), postEdit);

        Post changedPost = postRepository.findById(post.getId())
            .orElseThrow(() -> new PostNotFound());
       
        Assertions.assertEquals("fooo", changedPost.getTitle());
    }

    @Test
    @DisplayName("글 내용 수정 조회에러")
    void test4_1() {  
        Post post = Post.builder()
            .title("foo")
            .content("bar")
            .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
            .title("foo")
            .content("barr")
            .build();

        assertThrows(PostNotFound.class, () -> {
            postService.edit(post.getId() + 1L, postEdit);
        });
    }

    @Test
    @DisplayName("게시글 삭제")
    void test6() {  
        Post post = Post.builder()
            .title("foo")
            .content("bar")
            .build();

        postRepository.save(post);

        postService.delete(post.getId());
       
        Assertions.assertEquals(0, postRepository.count());
    }

    @Test
    @DisplayName("게시글 삭제 조회에러")
    void test6_1() {  
        Post post = Post.builder()
            .title("foo")
            .content("bar")
            .build();

        postRepository.save(post);

        assertThrows(PostNotFound.class, () -> {
            postService.delete(post.getId() + 1L);
        });
    }
}
