package com.study.api.controller;



import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.api.domain.Post;
import com.study.api.repository.PostRepository;
import com.study.api.request.PostCreate;
import com.study.api.request.PostEdit;

@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청시 Hello world를 출력.")
    void test() throws Exception {

        PostCreate request = PostCreate.builder()
            .title("제목입니다.")
            .content("내용입니다.")
            .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("/posts 요청시 title값은 필수다.")
    void test2() throws Exception {

        PostCreate request = PostCreate.builder()
            .content("내용입니다.")
            .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("잘못된 요청입니다."))
            .andExpect(MockMvcResultMatchers.jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("/posts 요청시 DB저장")
    void test3() throws Exception {

        PostCreate request = PostCreate.builder()
        .title("제목입니다.")
        .content("내용입니다.")
        .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());

        Assertions.assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        Assertions.assertEquals("제목입니다.", post.getTitle());
        Assertions.assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("/글 1개 조회")
    void test4() throws Exception {
        Post requestPost = Post.builder()
            .title("12345678910")
            .content("bar")
            .build();
        postRepository.save(requestPost);

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", requestPost.getId())
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(requestPost.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("1234567891"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("bar"))
            .andDo(MockMvcResultHandlers.print());
       
    }

    @Test
    @DisplayName("/글 여러개 조회")
    void test5() throws Exception {
        List<Post> requestPosts = IntStream.range(1, 31)
            .mapToObj(i -> Post.builder()
                            .title("foo " + i)
                            .content("bar " + i)
                            .build()
            ).collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=0&size=10")
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(10)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("foo 30"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("bar 30"))
            .andDo(MockMvcResultHandlers.print());
       
    }

    @Test
    @DisplayName("/글 제목 수정")
    void test6() throws Exception {
        
        Post post = Post.builder()
            .title("foo")
            .content("bar")
            .build();

         postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
            .title("fooo")
            .content("bar")
            .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/{postId}", post.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postEdit))
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
       
    }

    @Test
    @DisplayName("/게시글 삭제")
    void test7() throws Exception {
        
        Post post = Post.builder()
            .title("foo")
            .content("bar")
            .build();

        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{postId}", post.getId())
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
       
    }

    @Test
    @DisplayName("/존재하지 않는 게시글 조회")
    void test8() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andDo(MockMvcResultHandlers.print());
       
    }

    @Test
    @DisplayName("/존재하지 않는 게시글 수정")
    void test9() throws Exception {
        PostEdit postEdit = PostEdit.builder()
            .title("fooo")
            .content("bar")
            .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/posts/{postId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postEdit))
            )
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andDo(MockMvcResultHandlers.print());
       
    }

    @Test
    @DisplayName("게시글 작성시 제목에 '바보'는 포함 될 수 없다.")
    void test10() throws Exception {

        PostCreate request = PostCreate.builder()
            .title("나는 바보입니다.")
            .content("내용입니다.")
            .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andDo(MockMvcResultHandlers.print());
      
    }
}
