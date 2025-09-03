package com.example.yxy.entity;

import lombok.Data;

@Data
public class UploadResult {
    private Boolean success;
    private String message;
    private String filePath;
    private String uploadId;
    private Integer uploadedChunks;
}