package com.example.yxy.util;
 

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author chaird
 * @create 2020-10-11 15:53
 */
@Slf4j
public class ShellUtils {
  /**
   * @param pathOrCommand 脚本路径或者命令
   * @return
   */
  public static List<String> exceShell(String pathOrCommand) {
    List<String> result = new ArrayList<>();
 
    try {
      // 执行脚本
      Process ps = Runtime.getRuntime().exec(pathOrCommand);
      boolean exitValue = ps.waitFor(120, TimeUnit.SECONDS);
      if (!exitValue) {
        log.error("call shell failed. error code is :{}" , exitValue);
      }
 
      // 只能接收脚本echo打印的数据，并且是echo打印的最后一次数据
      BufferedInputStream in = new BufferedInputStream(ps.getInputStream());
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String line;
      while ((line = br.readLine()) != null) {
        log.info("脚本返回的数据如下： {}" , line);
        result.add(line);
      }
      in.close();
      br.close();
 
    } catch (Exception e) {
      log.error("报错了->",e);
    }
 
    return result;
  }

//  public static void main(String[] args) {
//    List<String> strings = exceShell("ls -la");
//    System.out.println(strings);
//  }
}