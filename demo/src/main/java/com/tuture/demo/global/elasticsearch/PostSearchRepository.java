package com.tuture.demo.global.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostSearchRepository extends ElasticsearchRepository<BoardDocument, Long>,
        PagingAndSortingRepository<BoardDocument, Long> {

}
