package com.example.yxy.controller;

import com.example.yxy.entity.FileChunk;
import com.example.yxy.entity.UploadResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@CrossOrigin
@RestController
@RequestMapping("/upload")
public class FileUploadController {
    
    @Value("${file.upload.path}")
    private String uploadPath;
    
    private final Map<String, Set<Integer>> uploadProgress = new ConcurrentHashMap<>();
    
    // 检查文件是否存在，用于秒传
    @PostMapping("/check")
    public ResponseEntity<?> checkFile(@RequestParam String identifier) {
        String filename = findFileByIdentifier(identifier);
        if (filename != null) {
            return ResponseEntity.ok().body(Collections.singletonMap("exist", true));
        }
        return ResponseEntity.ok().body(Collections.singletonMap("exist", false));
    }
    
    // 检查分片是否存在，用于断点续传
    @PostMapping("/chunkCheck")
    public ResponseEntity<?> checkChunk(@RequestParam String identifier, 
                                       @RequestParam Integer chunkNumber) {
        String chunkFilename = getChunkFilename(identifier, chunkNumber);
        File chunkFile = new File(uploadPath + File.separator + "chunks" +
                                File.separator + identifier, chunkFilename);
        
        boolean exists = chunkFile.exists();
        return ResponseEntity.ok().body(Collections.singletonMap("exist", exists));
    }
    
    // 上传分片
    @PostMapping("/chunk")
    public ResponseEntity<UploadResult> uploadChunk(FileChunk chunk,
                                                    @RequestParam("file") MultipartFile file) {
        UploadResult result = new UploadResult();
        
        try {
            // 创建分片存储目录
            String chunkDir = uploadPath + File.separator + "chunks" + 
                            File.separator + chunk.getIdentifier();
            File dir = new File(chunkDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            // 保存分片
            String chunkFilename = getChunkFilename(chunk.getIdentifier(), chunk.getChunkNumber());
            File chunkFile = new File(chunkDir, chunkFilename);
            file.transferTo(chunkFile);
            
            // 更新上传进度
            updateUploadProgress(chunk.getIdentifier(), chunk.getChunkNumber());
            
            result.setSuccess(true);
            result.setMessage("分片上传成功");
            result.setUploadId(chunk.getIdentifier());
            result.setUploadedChunks(getUploadedChunksCount(chunk.getIdentifier()));
            
        } catch (IOException e) {
            result.setSuccess(false);
            result.setMessage("分片上传失败: " + e.getMessage());
        }
        
        return ResponseEntity.ok(result);
    }
    
    // 合并分片
    @PostMapping("/merge")
    public ResponseEntity<UploadResult> mergeChunks(@RequestBody FileChunk chunk) {
        UploadResult result = new UploadResult();
        
        try {
            String chunkDir = uploadPath + File.separator + "chunks" + 
                            File.separator + chunk.getIdentifier();
            String filename = chunk.getFilename();
            
            // 创建目标文件
            File destFile = new File(uploadPath, filename);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            
            // 合并分片
            try (RandomAccessFile randomAccessFile = new RandomAccessFile(destFile, "rw")) {
                randomAccessFile.setLength(chunk.getTotalSize());
                
                for (int i = 1; i <= chunk.getTotalChunks(); i++) {
                    File chunkFile = new File(chunkDir, getChunkFilename(chunk.getIdentifier(), i));
                    try (FileInputStream fis = new FileInputStream(chunkFile)) {
                        byte[] bytes = new byte[(int) chunkFile.length()];
                        fis.read(bytes);
                        
                        randomAccessFile.seek((i - 1) * chunk.getChunkSize());
                        randomAccessFile.write(bytes);
                    }
                }
            }
            
            // 删除分片目录
            deleteDirectory(new File(chunkDir));
            
            // 清除上传进度
            uploadProgress.remove(chunk.getIdentifier());
            
            result.setSuccess(true);
            result.setMessage("文件合并成功");
            result.setFilePath("/uploads/" + filename);
            
        } catch (IOException e) {
            result.setSuccess(false);
            result.setMessage("文件合并失败: " + e.getMessage());
        }
        
        return ResponseEntity.ok(result);
    }
    
    // 工具方法
    private String getChunkFilename(String identifier, Integer chunkNumber) {
        return identifier + "-" + chunkNumber + ".chunk";
    }
    
    private void updateUploadProgress(String identifier, Integer chunkNumber) {
        uploadProgress.computeIfAbsent(identifier, k -> ConcurrentHashMap.newKeySet())
                     .add(chunkNumber);
    }
    
    private Integer getUploadedChunksCount(String identifier) {
        return uploadProgress.getOrDefault(identifier, Collections.emptySet()).size();
    }
    
    private String findFileByIdentifier(String identifier) {
        // 实现根据文件标识符查找已存在文件的逻辑
        // 可以使用数据库或文件系统记录文件标识符和路径的映射
        return null;
    }
    
    private void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        dir.delete();
    }
}