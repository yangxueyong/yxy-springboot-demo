package com.example.yxy.excel;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 导入数据的参数
 *
 * @author yxy
 * @date 2023/05/18
 */
@Data
public class ImportDataParam{
    /**
     * 线程池等待最大秒时间 15分钟
     */
    private int waitMaxSecTime = 15 * 60 * 1000;

    /**
     * 批次条数
     */
    private int batchCount = 5;
    /**
     * 线程核心
     */
    private int threadCore = 5;

    /**
     * 保存数据的回调
     */
    private CallRowFun callDataFun = null;

    /**
     * 找到了指定单元格之后的回调
     */
    private CallRowColFun callRowColDataFun = null;

    /**
     * 所有数据保存完成的回调
     */
    private CallDoneFun doneCallFun = null;

    /**
     * 指定获取哪些单元格的数据
     */
    private List<RowAndColData> rowAndColDataList;

    /**
     * 行列数据
     *
     * @author yxy
     * @date 2023/05/18
     */
    @Data
    @AllArgsConstructor
    public static class RowAndColData{
        private int rowIndex = -1;
        private int colIndex = -1;
    }

    public ImportDataParam(int batchCount, int threadCore, List<RowAndColData> rowAndColDataList,CallRowColFun callRowColFun, CallRowFun callRowFun, CallDoneFun doneCallFun) {
        this.batchCount = batchCount;
        this.threadCore = threadCore;
        this.callDataFun = callRowFun;
        this.callRowColDataFun = callRowColFun;
        this.doneCallFun = doneCallFun;
        this.rowAndColDataList = rowAndColDataList;
    }
}