package com.timurtatarshaov.demoforum.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.timurtatarshaov.demoforum.model.Post;
import com.timurtatarshaov.demoforum.model.Topic;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

	Page<Post> findByTopic(Topic topic, Pageable pageable);
}
