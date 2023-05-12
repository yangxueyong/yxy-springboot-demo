package com.example.yxy.dao.table;

import com.example.yxy.entity.table.OracleTabColumn;
import com.example.yxy.entity.table.OracleTabColumnWithBLOBs;
import com.example.yxy.entity.table.io.QueryOracleFieldIO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* Interface
* OracleTabColumnMapper
*
* @author 系统
* @date Create Time: Sun Nov 13 16:22:41 CST 2022
*/
@Mapper()
public interface OracleTabColumnMapper {
    /**
     * 插入数据库记录（不建议使用）
     * @param record
     */
    int insert(OracleTabColumnWithBLOBs record);

    /**
     * 插入数据库记录（建议使用）
     * @param record
     */
    int insertSelective(OracleTabColumnWithBLOBs record);


    List<OracleTabColumn> selectByColumn(QueryOracleFieldIO queryOracleFieldIO);
}