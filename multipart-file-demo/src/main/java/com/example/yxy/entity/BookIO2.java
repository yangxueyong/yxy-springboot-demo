package com.example.yxy.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 书io
 *
 * @author yxy
 * @date 2023/05/30
 */
@Data
public class BookIO2 {
    /**
     * 书id
     */
    private String bookId;
    /**
     * 书名字
     */
    private String bookName;
}
