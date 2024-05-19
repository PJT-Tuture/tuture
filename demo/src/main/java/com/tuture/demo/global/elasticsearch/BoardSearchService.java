//package com.tuture.demo.global.elasticsearch;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//import org.springframework.data.elasticsearch.core.SearchHits;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class BoardSearchService {
//
//    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
//    private final BoardSearchRepository boardSearchRepository;
//
//    /**
//     * 마감 안된 것 제목 및 본문 검색 오타, 비슷한 문자 포함.
//     *
//     * @param keyword   검색할 단어
//     * @param pageNum   페이지 번호
//     * @param boardOrder 정렬 기준 : 마감O, 마감X
//     * @return 진행중인 항목들 중에서 검색한 단어가 제목 및 본문에 포함되는 리스트
//     */
//    public Page<BoardListDto.Response> searchOngoingBoardsByKeyword(String keyword, int pageNum, BoardOrder boardOrder) {
//        PageRequest pageable = PageRequestCustom.of(pageNum, boardOrder);
//        NativeSearchQuery query = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.boolQuery()
//                        .must(QueryBuilders.rangeQuery("endAt").gt(LocalDateTime.now()))
//                        .must(QueryBuilders.multiMatchQuery(keyword, "title", "content")))
//                .withPageable(pageable)
//                .build();
//
//        return getSearchPage(pageable, query);
//    }
//
//    /**
//     * 마감 된 것 제목 및 본문 검색 오타, 비슷한 문자 포함.
//     *
//     * @param keyword   검색할 단어
//     * @param pageNum   페이지 번호
//     * @param boardOrder 정렬 기준 : 마감O, 마감X
//     * @return 마감된 항목들 중에서 검색한 단어가 제목 및 본문에 포함되는 리스트
//     */
//    public Page<BoardListDto.Response> searchCompletedBoardsByKeyword(String keyword, int pageNum, BoardOrder boardOrder) {
//        PageRequest pageable = PageRequestCustom.of(pageNum, boardOrder);
//        NativeSearchQuery query = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.boolQuery()
//                        .must(QueryBuilders.rangeQuery("endAt").lte(LocalDateTime.now()))
//                        .must(QueryBuilders.multiMatchQuery(keyword, "title", "content")))
//                .withPageable(pageable)
//                .build();
//
//        return getSearchPage(pageable, query);
//    }
//
//    /**
//     * 마감 안된 것 태그 검색 오타, 비슷한 문자 포함.
//     *
//     * @param keyword   검색할 단어
//     * @param pageNum   페이지 번호
//     * @param boardOrder 정렬 기준 : 마감O, 마감X
//     * @return 진행중인 항목들 중에서 검색한 단어가 태그에 포함되는 리스트
//     */
//    public Page<BoardListDto.Response> searchOngoingBoardsByTag(String keyword, Integer pageNum, BoardOrder boardOrder) {
//        PageRequest pageable = PageRequestCustom.of(pageNum, boardOrder);
//        NativeSearchQuery query = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.boolQuery()
//                        .must(QueryBuilders.rangeQuery("endAt").gt(LocalDateTime.now()))
//                        .must(QueryBuilders.matchQuery("tags", keyword)))
//                .withPageable(pageable)
//                .build();
//
//        return getSearchPage(pageable, query);
//    }
//
//    /**
//     * 마감 된 것 태그 검색 오타, 비슷한 문자 포함.
//     *
//     * @param keyword   검색할 단어
//     * @param pageNum   페이지 번호
//     * @param boardOrder 정렬 기준 : 마감O, 마감X
//     * @return 마감된 항목들 중에서 검색한 단어가 태그에 포함되는 리스트
//     */
//    public Page<BoardListDto.Response> searchCompletedBoardsByTag(String keyword, Integer pageNum, BoardOrder boardOrder) {
//        PageRequest pageable = PageRequestCustom.of(pageNum, boardOrder);
//        NativeSearchQuery query = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.boolQuery()
//                        .must(QueryBuilders.rangeQuery("endAt").lte(LocalDateTime.now()))
//                        .must(QueryBuilders.matchQuery("tags", keyword)))
//                .withPageable(pageable)
//                .build();
//
//        return getSearchPage(pageable, query);
//    }
//
//    private Page<BoardListDto.Response> getSearchPage(PageRequest pageable, NativeSearchQuery query) {
//        SearchHits<BoardDocument> searchHits = elasticsearchRestTemplate.search(query, BoardDocument.class);
//        List<BoardListDto.Response> list = searchHits.stream()
//                .map(hit -> BoardListDto.Response.boardDocumentToBoardListResponse(hit.getContent()))
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(list, pageable, searchHits.getTotalHits());
//    }
//
//    public void save(Board board, List<String> tags) {
//        boardSearchRepository.save(BoardDocument.createBoardDocument(board, tags));
//        log.debug("[BoardService] save boardDocument, Id : {}", board.getBoardId());
//    }
//
//    public void update(Board board, List<String> tags) {
//        BoardDocument boardDocument = boardSearchRepository.findById(board.getBoardId())
//                .orElseThrow(() -> new SearchException(ErrorCode.NOT_FOUND_BOARD_DOCUMENT));
//
//        boardSearchRepository.save(boardDocument.updateBoardDocument(board, tags));
//        log.debug("[BoardService] update boardDocument, Id : {}", boardDocument.getId());
//    }
//
//    public void delete(Board board) {
//        BoardDocument boardDocument = boardSearchRepository.findById(board.getBoardId())
//                .orElseThrow(() -> new SearchException(ErrorCode.NOT_FOUND_BOARD_DOCUMENT));
//        log.debug("[BoardService] delete boardDocument, Id : {}", boardDocument.getId());
//        boardSearchRepository.delete(boardDocument);
//    }
//
//    public void deleteAll() {
//        boardSearchRepository.deleteAll();
//    }
//
//    public Iterable<BoardDocument> getAllData() {
//        return boardSearchRepository.findAll();
//    }
//
//    public void updateVoteCount(int voteCount, Long boardId) {
//        BoardDocument boardDocument = boardSearchRepository.findById(boardId)
//                .orElseThrow(() -> new SearchException(ErrorCode.NOT_FOUND_BOARD_DOCUMENT));
//
//        boardDocument.setVoteCount(voteCount);
//        boardSearchRepository.save(boardDocument);
//    }
//
//}
