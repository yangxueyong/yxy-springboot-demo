package com.example.yxy.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.alibaba.excel.read.metadata.holder.csv.CsvReadSheetHolder;
import com.alibaba.excel.read.metadata.holder.xls.XlsReadSheetHolder;
import com.alibaba.excel.read.metadata.holder.xlsx.XlsxReadSheetHolder;
import com.alibaba.excel.util.ListUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 导入数据的监听器
 *
 * @author yxy
 * @date 2023/05/18
 */
@Slf4j
public class ImportDataListener<T> implements ReadListener<T> {

    /**
     * 锁
     */
    public Object locker=new Object();

    private ImportDataParam importDataParam;
    /**
     * 线程池
     */
    private ThreadPoolExecutor threadPool = null;
    /**
     * 缓存的数据
     */
    private List<T> importExcelDataList = null;

    public ImportDataListener() {
        super();
    }

    /**
     * 初始化相关数据
     *
     * @param importDataParam 导入数据的入参
     */
    public ImportDataListener(ImportDataParam importDataParam) {
        this.importDataParam = importDataParam;
        this.importExcelDataList = ListUtils.newArrayListWithExpectedSize(importDataParam.getBatchCount());
        this.threadPool = new ThreadPoolExecutor(importDataParam.getThreadCore(),
                importDataParam.getThreadCore(),
                1,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(importDataParam.getThreadCore()));
    }


    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        if(importDataParam == null) {
            return;
        }
        //如果行列数据不为空，则根据行列去查找数据
        List<ImportDataParam.RowAndColData> rowAndColDataList = importDataParam.getRowAndColDataList();
        if (!CollectionUtils.isEmpty(rowAndColDataList) && importDataParam.getCallRowColDataFun() != null) {
            ReadSheetHolder readSheetHolder = context.readSheetHolder();
            Integer dataRowIndex = -1;
            Integer dataColumnIndex = -1;
            if(readSheetHolder instanceof XlsxReadSheetHolder) {
                dataRowIndex = ((XlsxReadSheetHolder)readSheetHolder).getRowIndex();
                dataColumnIndex = ((XlsxReadSheetHolder)readSheetHolder).getColumnIndex();
            }
            for (ImportDataParam.RowAndColData rowAndColData : rowAndColDataList) {
                int colIndex = rowAndColData.getColIndex();
                int rowIndex = rowAndColData.getRowIndex();
                //查找到了之后就直接回调
                if (colIndex >= 0 && rowIndex >= 0 && colIndex == dataColumnIndex && rowIndex == dataRowIndex) {
                    importDataParam.getCallRowColDataFun().callBack(rowIndex,colIndex, Arrays.asList(data));
                    return;
                }
            }
        }
        //对于不是根据行列去查找的数据，先将其放入list中
        importExcelDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (importExcelDataList.size() >= importDataParam.getBatchCount()) {
            //调用存储数据
            saveData();
            // 存储完成清理 list
            importExcelDataList = ListUtils.newArrayListWithExpectedSize(importDataParam.getBatchCount());
        }

    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        try {
            // 这里也要保存数据，确保最后遗留的数据也存储到数据库
            log.info("所有数据解析完成！");
            saveData();
            long startTime = System.currentTimeMillis();
            int waitCount = 0;
            while (true) {
                long time = System.currentTimeMillis() - startTime;
                if (threadPool.getActiveCount() <= 0) {
                    log.info("所有线程已执行完毕，共耗时->{}", time);
                    break;
                }
                if (time > importDataParam.getWaitMaxSecTime()) {
                    throw new RuntimeException("等待线程完成时长超过" + importDataParam.getWaitMaxSecTime() + "，共耗时->" + time);
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    log.error("线程休眠出错->", e);
                }
                log.warn("等待次数->{},等待耗时->{},所有线程未完成执行,MaximumPoolSize->{},ActiveCount->{}",
                        ++waitCount, time, threadPool.getMaximumPoolSize(), threadPool.getActiveCount());
            }
            if (importDataParam.getDoneCallFun() != null) {
                importDataParam.getDoneCallFun().callBack();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }

    /**
     * 存储数据到数据库 使用函数式编程调用重写方法
     */
    private void saveData() {
        try {
            //获取线程池的可用线程数
            int threadPoolAvailableSize = threadPool.getMaximumPoolSize() - threadPool.getActiveCount();
            log.info("可用线程数->{}", threadPoolAvailableSize);
            //开始时间
            long startTime = System.currentTimeMillis();
            //等待次数
            int waitCount = 0;
            //如果可用线程数为0，则需要循环等待线程池的空闲线程
            while (threadPoolAvailableSize <= 0) {
                threadPoolAvailableSize = threadPool.getMaximumPoolSize() - threadPool.getActiveCount();
                long time = System.currentTimeMillis() - startTime;
                //如果有空闲线程就直接跳出
                if (threadPoolAvailableSize > 0) {
                    log.info("等到了有效线程，共耗时->{}", time);
                    break;
                    //如果等待的时间超过指定时间的阈值，则表示异常
                } else if (time > importDataParam.getWaitMaxSecTime()) {
                    throw new RuntimeException("等待空闲线程时长超过" + importDataParam.getWaitMaxSecTime() + "，共耗时->" + time);
                }
                log.warn("等待次数->{},等待耗时->{},可用线程数不足,MaximumPoolSize->{},ActiveCount->{}", ++waitCount, time, threadPool.getMaximumPoolSize(), threadPool.getActiveCount());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    log.error("线程休眠出错->", e);
                }
            }
            //如果可用线程数大于0
            if (threadPoolAvailableSize > 0) {
                //使用线程池来做事
                synchronized (locker) {
                    threadPool.submit(new SaveDataThread(importExcelDataList, importDataParam.getCallDataFun()));
                }
            }
        }catch (Exception e){
            log.error("出错->",e);
            threadPool.shutdown();
        }
    }
}


/**
 * 保存数据的线程
 *
 * @author yxy
 * @date 2023/05/18
 */
@Slf4j
class SaveDataThread<T> extends Thread {

    public List<T> dataList;
    private CallRowFun callFun;

    public SaveDataThread(List<T> dataList, CallRowFun callFun) {
        this.dataList = dataList;
        this.callFun = callFun;
    }

    @Override
    public void run() {
        //todo 生产上记得去掉这个
        int num=(int)(Math.random()*1000-1);
        try {
            Thread.sleep(num);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //将数据处理交给回调方法去做
        callFun.callBack(dataList);
    }
}

