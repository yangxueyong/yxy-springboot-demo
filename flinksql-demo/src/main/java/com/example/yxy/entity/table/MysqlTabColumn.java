package com.example.yxy.entity.table;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

/**
 * mysql表结构
* @author 系统
* @date Create Time: Sun Nov 13 16:22:41 CST 2022
 */
@ApiModel(value="com.cqrcb.cloud.entity.job.MysqlTabColumn",description="mysql表结构")
@Data()
public class MysqlTabColumn {
    /**
     * COLUMN_NAME ,ORDINAL_POSITION ,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH ,CHARACTER_OCTET_LENGTH ,NUMERIC_PRECISION ,NUMERIC_SCALE ,COLUMN_TYPE
     */
    /**
     * Table, view or cluster name
     */
    @ApiModelProperty(value="Table, view or cluster name",name="tableName")
    @Length(max = 128, message = "Table, view or cluster name名长度最长为128")
    private String tableName;

    /**
     * TABLE_SCHEMA
     */
    @ApiModelProperty(value="Table, view or cluster name",name="tableSchema")
    @Length(max = 128, message = "Table, view or cluster name名长度最长为128")
    private String tableSchema;

    /**
     * Column name
     */
    @ApiModelProperty(value="Column name",name="columnName")
    @Length(max = 128, message = "Column name名长度最长为128")
    private String columnName;

    /**
     * Datatype of the column
     */
    @ApiModelProperty(value="ordinalPosition of the column",name="ordinalPosition")
    @Length(max = 128, message = "ordinalPosition of the column名长度最长为128")
    private Integer ordinalPosition;

    /**
     * Datatype of the column
     */
    @ApiModelProperty(value="Datatype of the column",name="dataType")
    @Length(max = 128, message = "Datatype of the column名长度最长为128")
    private String dataType;

    /**
     * CHARACTER_MAXIMUM_LENGTH of the column
     */
    @ApiModelProperty(value="characterMaximumLength of the column",name="characterMaximumLength")
    @Length(max = 128, message = "characterMaximumLength of the column名长度最长为128")
    private Integer characterMaximumLength;

    /**
     * CHARACTER_OCTET_LENGTH of the column
     */
    @ApiModelProperty(value="characterOctetLength of the column",name="characterOctetLength")
    @Length(max = 128, message = "characterOctetLength of the column名长度最长为128")
    private Integer characterOctetLength;

    /**
     * NUMERIC_PRECISION
     */
    @ApiModelProperty(value="Length: decimal digits (NUMBER) or binary digits (FLOAT)",name="numericPrecision")
    private BigDecimal numericPrecision;

    /**
     * NUMERIC_SCALE
     */
    @ApiModelProperty(value="Digits to right of decimal point in a number",name="numericScale")
    private BigDecimal numericScale;

    /**
     * columnType
     */
    @ApiModelProperty(value="Does column allow NULL values?",name="COLUMN_TYPE")
    @Length(max = 1, message = "Does column allow NULL values?名长度最长为1")
    private String columnType;

}