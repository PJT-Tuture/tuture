package com.tuture.demo.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UploadFileResponse {

    private  String fileName;

    private  String fileDownloadUri;

    private String fileType;

    private long size;
}
