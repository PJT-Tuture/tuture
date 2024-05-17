package com.tuture.demo.global.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "boards")
@Mapping(mappingPath = "elasticsearch/search-mapping.json")
@Setting(settingPath = "elasticsearch/search-setting.json")
public class BoardDocument {

    @Id
    private Long id;
    private Long writerId;
    private String writerNickname;
    private String title;
    private String regDate;
    private int viewCnt;
    private String content;
    private String region;
    private String dueDate;
    private String roleCategory;
    private String imgUrl;

}
