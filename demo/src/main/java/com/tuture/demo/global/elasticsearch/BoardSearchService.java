package com.tuture.demo.global.elasticsearch;

import com.tuture.demo.model.domain.Board;
import com.tuture.demo.model.dto.SearchCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchException;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardSearchService {

    @Autowired
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private final BoardSearchRepository boardSearchRepository;
    public List<Board> search(int page, String condition, String keyword, String orderBy, String orderByDir, List<Long> tagIds) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (tagIds != null && !tagIds.isEmpty()) {
            boolQuery.filter(QueryBuilders.termsQuery("tagIds", tagIds));
        }

        if (condition != null && keyword != null) {
            switch (condition) {
                case "title":
                    boolQuery.must(QueryBuilders.matchQuery("title", keyword));
                    break;
                case "title_content":
                    boolQuery.must(QueryBuilders.multiMatchQuery(keyword, "title", "content"));
                    break;
                case "writer":
                    boolQuery.must(QueryBuilders.matchQuery("writerNickname", keyword));
                    break;
            }
        }

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withPageable(org.springframework.data.domain.PageRequest.of(page - 1, 10));

        if (orderBy != null && orderByDir != null) {
            queryBuilder.withSort(org.elasticsearch.search.sort.SortBuilders.fieldSort(orderBy)
                    .order(SortOrder.fromString(orderByDir)));
        }

        Query searchQuery = queryBuilder.build();
        SearchHits<Board> searchHits = elasticsearchRestTemplate.search(searchQuery, Board.class);

        return searchHits.stream().map(hit -> hit.getContent()).collect(Collectors.toList());
    }
    public void save(Board board, List<Long> tagIds) {
        boardSearchRepository.save(BoardDocument.createBoardDocument(board, tagIds));
    }

    public void update(Board board, List<Long> tagIds) {
        BoardDocument boardDocument = boardSearchRepository.findById(board.getId());
        boardSearchRepository.save(boardDocument.updateBoardDocument(board, tagIds));
    }

    public void delete(Board board) {
        BoardDocument boardDocument = boardSearchRepository.findById(board.getId())
                .orElseThrow(() -> new SearchException(ErrorCode.NOT_FOUND_BOARD_DOCUMENT));
        boardSearchRepository.delete(boardDocument);
    }

    public void deleteAll() {
        boardSearchRepository.deleteAll();
    }

    public Iterable<BoardDocument> getAllData() {
        return boardSearchRepository.findAll();
    }
}
