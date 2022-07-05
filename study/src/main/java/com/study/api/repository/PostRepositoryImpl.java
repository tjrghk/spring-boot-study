package com.study.api.repository;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;                                                                                 
import com.study.api.domain.Post;
import com.study.api.domain.QPost;
import com.study.api.request.PostSearch;    

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(QPost.post)
            .limit(postSearch.getSize())
            .offset(postSearch.getOffset())
            .orderBy(QPost.post.id.desc())
            .fetch();
    }


}
