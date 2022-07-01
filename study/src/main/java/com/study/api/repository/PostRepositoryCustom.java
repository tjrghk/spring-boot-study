package com.study.api.repository;

import java.util.List;

import com.study.api.domain.Post;
import com.study.api.request.PostSearch;

public interface PostRepositoryCustom {
    List<Post> getList(PostSearch postSearch);
}
