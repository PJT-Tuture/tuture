//package com.tuture.demo.global.elasticsearch;
//
//import com.tuture.demo.model.domain.Board;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Mapping;
//import org.springframework.data.elasticsearch.annotations.Setting;
//
//import java.util.List;
//
//
//@Getter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Document(indexName = "boards")
//@Mapping(mappingPath = "elasticsearch/search-mapping.json")
//@Setting(settingPath = "elasticsearch/search-setting.json")
//public class BoardDocument {
//
//    @Id
//    private Long id;
//    private Long writerId;
//    private String writerNickname;
//    private String title;
//    private String regDate;
//    private int viewCnt;
//    private String content;
//    private String region;
//    private String dueDate;
//    private String roleCategory;
//    private String imgUrl;
//    private List<Long> tagIds;
//    public static BoardDocument createBoardDocument(Board board, List<Long> tagIds) {
//        return BoardDocument.builder()
//                .id(board.getId())
//                .title(board.getTitle())
//                .content(board.getContent())
//                .writerNickname(board.getWriterNickname())
//                .imgUrl(board.getImgUrl())
//                .dueDate(board.getDueDate())
//                .regDate(board.getRegDate())
//                .tagIds(tagIds)
//                .build();
//    }
//
//    public BoardDocument updateBoardDocument(Board board, List<Long> tagIds) {
//        this.title = board.getTitle();
//        this.content = board.getContent();
//        this.imgUrl = board.getImgUrl();
//        this.dueDate = board.getDueDate();
//        this.regDate = board.getRegDate();
//        this.tagIds = tagIds;
//        return this;
//    }
//}
