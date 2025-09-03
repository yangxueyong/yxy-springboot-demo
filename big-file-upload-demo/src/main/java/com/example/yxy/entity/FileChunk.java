package com.example.yxy.entity;

import lombok.Data;

@Data
public class FileChunk {
    private String id;
    private String uploadId;
    private Integer chunkNumber;
    private Long chunkSize;
    private Long currentChunkSize;
    private Long totalSize;
    private String identifier;
    private String filename;
    private String relativePath;
    private Integer totalChunks;
    private String type;
}

