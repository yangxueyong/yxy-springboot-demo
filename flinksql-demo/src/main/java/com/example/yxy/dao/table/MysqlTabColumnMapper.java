package com.example.yxy.dao.table;

import com.example.yxy.entity.table.MysqlTabColumn;
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
public interface MysqlTabColumnMapper {

    List<MysqlTabColumn> selectByColumn(QueryOracleFieldIO queryOracleFieldIO);
}