package com.timurtatarshaov.demoforum.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.timurtatarshaov.demoforum.model.Topic;

public interface TopicRepository extends PagingAndSortingRepository<Topic, Long> {

	Page<Topic> findAll(Pageable pageable);
}
