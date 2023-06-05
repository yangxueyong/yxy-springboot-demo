package com.cqrcb.cloud.util;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

public class FileUtil {
    public static <T> T getFileToJavaObj(String filePath,Class<T> t){
        return JSONUtil.toBean(getFileToJSONStr(filePath),t);
    }

    public static String getFileToJSONStr(String filePath){
        return FileReader.create(new File(filePath)).readString();
    }

    public static JSONObject getFileToJSONObject(String filePath){
        String s = FileReader.create(new File(filePath)).readString();
        return JSON.parseObject(s);
    }


}
