package com.study.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.api.domain.Post;

/**
 * PostRepository
 */
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom{

    
}