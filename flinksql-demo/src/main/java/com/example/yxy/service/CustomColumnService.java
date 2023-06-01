package com.example.yxy.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.yxy.constant.MatchConstant;
import com.example.yxy.dao.table.MysqlTabColumnMapper;
import com.example.yxy.dao.table.OracleTabColumnMapper;
import com.example.yxy.entity.table.MysqlTabColumn;
import com.example.yxy.entity.table.OracleTabColumn;
import com.example.yxy.entity.table.TabColumn;
import com.example.yxy.entity.table.io.QueryOracleFieldIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomColumnService {
    @Autowired
    private OracleTabColumnMapper oracleTabColumnMapper;
    @Autowired
    private MysqlTabColumnMapper mysqlTabColumnMapper;
    @Autowired
    @Lazy
    private CustomColumnService customColumnService;

    @DS("pft-oracle")
    public List<OracleTabColumn> queryFromPFTOracle(String tableName) {
        return queryByOracle(tableName);
    }

    @DS("market-oracle")
    public List<OracleTabColumn> queryFromMarketOracle(String tableName) {
        return queryByOracle(tableName);
    }

    public List<OracleTabColumn> queryByOracle(String tableName) {
        QueryOracleFieldIO queryOracleFieldIO = new QueryOracleFieldIO();
        queryOracleFieldIO.setTableName(tableName);
        List<OracleTabColumn> oracleTabColumns = oracleTabColumnMapper.selectByColumn(queryOracleFieldIO);
        return oracleTabColumns;
    }

    @DS("pft-mysql")
    public List<MysqlTabColumn> queryFromPFTMysql(String tableName) {
        return queryByMysql(tableName);
    }

    @DS("market-mysql")
    public List<MysqlTabColumn> queryFromMarketMysql(String tableName) {
        return queryByMysql(tableName);
    }

    public List<MysqlTabColumn> queryByMysql(String tableName) {
        QueryOracleFieldIO queryOracleFieldIO = new QueryOracleFieldIO();
        queryOracleFieldIO.setTableName(tableName);
        List<MysqlTabColumn> oracleTabColumns = mysqlTabColumnMapper.selectByColumn(queryOracleFieldIO);
        return oracleTabColumns;
    }

    public List<TabColumn> getColumn(String prefixTableName, String dbType) {
        String tableName = null;
        if (prefixTableName.toUpperCase().startsWith(MatchConstant.DB_PREFIX_MARKET)) {
            tableName = prefixTableName.substring(prefixTableName.indexOf(MatchConstant.DB_PREFIX_MARKET) + MatchConstant.DB_PREFIX_MARKET.length());
        } else if (prefixTableName.toUpperCase().startsWith(MatchConstant.DB_PREFIX_PFT)) {
            tableName = prefixTableName.substring(prefixTableName.indexOf(MatchConstant.DB_PREFIX_PFT) + MatchConstant.DB_PREFIX_PFT.length());
        }
        if (MatchConstant.DB_TYPE_ORACLE.equalsIgnoreCase(dbType)) {
            return getOracleColumn(prefixTableName,tableName);
        }else  if (MatchConstant.DB_TYPE_MYSQL.equalsIgnoreCase(dbType)) {
            return getMysqlColumn(prefixTableName,tableName);
        }
        return null;
    }

    public List<TabColumn> getOracleColumn(String prefixTableName,String tableName) {
        List<OracleTabColumn> oracleTabColumns = null;
        if (prefixTableName.toUpperCase().startsWith(MatchConstant.DB_PREFIX_MARKET)) {
            oracleTabColumns = customColumnService.queryFromMarketOracle(tableName);
        }else if (prefixTableName.toUpperCase().startsWith(MatchConstant.DB_PREFIX_PFT)) {
            oracleTabColumns = customColumnService.queryFromPFTOracle(tableName);
        }
        if (CollectionUtils.isEmpty(oracleTabColumns)) {
            return null;
        }
        List<TabColumn> datas = new ArrayList<>();
        TabColumn tabColumn = null;
        for (OracleTabColumn oracleTabColumn : oracleTabColumns) {
            tabColumn = new TabColumn();
            /**
             * COLUMN_NAME ,ORDINAL_POSITION ,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH ,CHARACTER_OCTET_LENGTH ,NUMERIC_PRECISION ,NUMERIC_SCALE ,COLUMN_TYPE
             */
            tabColumn.setColumnName(oracleTabColumn.getColumnName());
            tabColumn.setDataType(oracleTabColumn.getDataType());
            tabColumn.setNumericPrecision(oracleTabColumn.getDataPrecision());
            tabColumn.setNumericScale(oracleTabColumn.getDataScale());
        }
        return datas;
    }

    public List<TabColumn> getMysqlColumn(String prefixTableName,String tableName) {
        List<MysqlTabColumn> mysqlTabColumns = null;
        if (prefixTableName.toUpperCase().startsWith(MatchConstant.DB_PREFIX_MARKET)) {
            mysqlTabColumns = customColumnService.queryFromMarketMysql(tableName);
        }else if (prefixTableName.toUpperCase().startsWith(MatchConstant.DB_PREFIX_PFT)) {
            mysqlTabColumns = customColumnService.queryFromPFTMysql(tableName);
        }
        if (CollectionUtils.isEmpty(mysqlTabColumns)) {
            return null;
        }
        List<TabColumn> datas = new ArrayList<>();
        TabColumn tabColumn = null;
        for (MysqlTabColumn oracleTabColumn : mysqlTabColumns) {
            tabColumn = new TabColumn();
            /**
             * COLUMN_NAME ,ORDINAL_POSITION ,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH ,CHARACTER_OCTET_LENGTH ,NUMERIC_PRECISION ,NUMERIC_SCALE ,COLUMN_TYPE
             */
            tabColumn.setColumnName(oracleTabColumn.getColumnName());
            tabColumn.setDataType(oracleTabColumn.getDataType());
            tabColumn.setNumericPrecision(oracleTabColumn.getNumericPrecision());
            tabColumn.setNumericScale(oracleTabColumn.getNumericScale());
        }
        return datas;
    }
}
