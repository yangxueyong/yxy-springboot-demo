package com.example.yxy.entity.table;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

/**
 * null实体类
 * <p>
 * tableName Table, view or cluster name<br>
 * columnName Column name<br>
 * dataType Datatype of the column<br>
 * dataTypeMod Datatype modifier of the column<br>
 * dataTypeOwner Owner of the datatype of the column<br>
 * dataLength Length of the column in bytes<br>
 * dataPrecision Length: decimal digits (NUMBER) or binary digits (FLOAT)<br>
 * dataScale Digits to right of decimal point in a number<br>
 * nullable Does column allow NULL values?<br>
 * columnId Sequence number of the column as created<br>
 * defaultLength Length of default value for the column<br>
 * numDistinct The number of distinct values in the column<br>
 * density The density of the column<br>
 * numNulls The number of nulls in the column<br>
 * numBuckets The number of buckets in histogram for the column<br>
 * lastAnalyzed The date of the most recent time this column was analyzed<br>
 * sampleSize The sample size used in analyzing this column<br>
 * characterSetName Character set name<br>
 * charColDeclLength Declaration length of character type column<br>
 * globalStats Are the statistics calculated without merging underlying partitions?<br>
 * userStats Were the statistics entered directly by the user?<br>
 * avgColLen The average length of the column in bytes<br>
 * charLength The maximum length of the column in characters<br>
 * charUsed C is maximum length given in characters, B if in bytes<br>
 * v80FmtImage Is column data in 8.0 image format?<br>
 * dataUpgraded Has column data been upgraded to the latest type version format?<br>
 * histogram null<br>
 * defaultOnNull Is this a default on null column?<br>
 * identityColumn Is this an identity column?<br>
 * evaluationEdition Name of the evaluation edition assigned to the column expression<br>
 * unusableBefore Name of the oldest edition in which the column is usable<br>
 * unusableBeginning Name of the oldest edition in which the column becomes perpetually unusable<br>
 * collation Collation name<br>
 * dataDefault Default value for the column<br>
 * lowValue The low value in the column<br>
 * highValue The high value in the column<br>
*
* @author 系统
* @date Create Time: Sun Nov 13 16:22:41 CST 2022
 */
@ApiModel(value="com.cqrcb.cloud.entity.job.OracleTabColumn",description="null")
@Data()
public class OracleTabColumn {
    /**
     * Table, view or cluster name
     */
    @ApiModelProperty(value="Table, view or cluster name",name="tableName")
    @Length(max = 128, message = "Table, view or cluster name名长度最长为128")
    private String tableName;

    /**
     * Column name
     */
    @ApiModelProperty(value="Column name",name="columnName")
    @Length(max = 128, message = "Column name名长度最长为128")
    private String columnName;

    /**
     * Datatype of the column
     */
    @ApiModelProperty(value="Datatype of the column",name="dataType")
    @Length(max = 128, message = "Datatype of the column名长度最长为128")
    private String dataType;

    /**
     * Datatype modifier of the column
     */
    @ApiModelProperty(value="Datatype modifier of the column",name="dataTypeMod")
    @Length(max = 3, message = "Datatype modifier of the column名长度最长为3")
    private String dataTypeMod;

    /**
     * Owner of the datatype of the column
     */
    @ApiModelProperty(value="Owner of the datatype of the column",name="dataTypeOwner")
    @Length(max = 128, message = "Owner of the datatype of the column名长度最长为128")
    private String dataTypeOwner;

    /**
     * Length of the column in bytes
     */
    @ApiModelProperty(value="Length of the column in bytes",name="dataLength")
    private BigDecimal dataLength;

    /**
     * Length: decimal digits (NUMBER) or binary digits (FLOAT)
     */
    @ApiModelProperty(value="Length: decimal digits (NUMBER) or binary digits (FLOAT)",name="dataPrecision")
    private BigDecimal dataPrecision;

    /**
     * Digits to right of decimal point in a number
     */
    @ApiModelProperty(value="Digits to right of decimal point in a number",name="dataScale")
    private BigDecimal dataScale;

    /**
     * Does column allow NULL values?
     */
    @ApiModelProperty(value="Does column allow NULL values?",name="nullable")
    @Length(max = 1, message = "Does column allow NULL values?名长度最长为1")
    private String nullable;

    /**
     * Sequence number of the column as created
     */
    @ApiModelProperty(value="Sequence number of the column as created",name="columnId")
    private BigDecimal columnId;

    /**
     * Length of default value for the column
     */
    @ApiModelProperty(value="Length of default value for the column",name="defaultLength")
    private BigDecimal defaultLength;

    /**
     * The number of distinct values in the column
     */
    @ApiModelProperty(value="The number of distinct values in the column",name="numDistinct")
    private BigDecimal numDistinct;

    /**
     * The density of the column
     */
    @ApiModelProperty(value="The density of the column",name="density")
    private BigDecimal density;

    /**
     * The number of nulls in the column
     */
    @ApiModelProperty(value="The number of nulls in the column",name="numNulls")
    private BigDecimal numNulls;

    /**
     * The number of buckets in histogram for the column
     */
    @ApiModelProperty(value="The number of buckets in histogram for the column",name="numBuckets")
    private BigDecimal numBuckets;

    /**
     * The date of the most recent time this column was analyzed
     */
    @ApiModelProperty(value="The date of the most recent time this column was analyzed",name="lastAnalyzed")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date lastAnalyzed;

    /**
     * The sample size used in analyzing this column
     */
    @ApiModelProperty(value="The sample size used in analyzing this column",name="sampleSize")
    private BigDecimal sampleSize;

    /**
     * Character set name
     */
    @ApiModelProperty(value="Character set name",name="characterSetName")
    @Length(max = 44, message = "Character set name名长度最长为44")
    private String characterSetName;

    /**
     * Declaration length of character type column
     */
    @ApiModelProperty(value="Declaration length of character type column",name="charColDeclLength")
    private BigDecimal charColDeclLength;

    /**
     * Are the statistics calculated without merging underlying partitions?
     */
    @ApiModelProperty(value="Are the statistics calculated without merging underlying partitions?",name="globalStats")
    @Length(max = 3, message = "Are the statistics calculated without merging underlying partitions?名长度最长为3")
    private String globalStats;

    /**
     * Were the statistics entered directly by the user?
     */
    @ApiModelProperty(value="Were the statistics entered directly by the user?",name="userStats")
    @Length(max = 3, message = "Were the statistics entered directly by the user?名长度最长为3")
    private String userStats;

    /**
     * The average length of the column in bytes
     */
    @ApiModelProperty(value="The average length of the column in bytes",name="avgColLen")
    private BigDecimal avgColLen;

    /**
     * The maximum length of the column in characters
     */
    @ApiModelProperty(value="The maximum length of the column in characters",name="charLength")
    private BigDecimal charLength;

    /**
     * C is maximum length given in characters, B if in bytes
     */
    @ApiModelProperty(value="C is maximum length given in characters, B if in bytes",name="charUsed")
    @Length(max = 1, message = "C is maximum length given in characters, B if in bytes名长度最长为1")
    private String charUsed;

    /**
     * Is column data in 8.0 image format?
     */
    @ApiModelProperty(value="Is column data in 8.0 image format?",name="v80FmtImage")
    @Length(max = 3, message = "Is column data in 8.0 image format?名长度最长为3")
    private String v80FmtImage;

    /**
     * Has column data been upgraded to the latest type version format?
     */
    @ApiModelProperty(value="Has column data been upgraded to the latest type version format?",name="dataUpgraded")
    @Length(max = 3, message = "Has column data been upgraded to the latest type version format?名长度最长为3")
    private String dataUpgraded;

    /**
     * null
     */
    @ApiModelProperty(value="null",name="histogram")
    @Length(max = 15, message = "null名长度最长为15")
    private String histogram;

    /**
     * Is this a default on null column?
     */
    @ApiModelProperty(value="Is this a default on null column?",name="defaultOnNull")
    @Length(max = 3, message = "Is this a default on null column?名长度最长为3")
    private String defaultOnNull;

    /**
     * Is this an identity column?
     */
    @ApiModelProperty(value="Is this an identity column?",name="identityColumn")
    @Length(max = 3, message = "Is this an identity column?名长度最长为3")
    private String identityColumn;

    /**
     * Name of the evaluation edition assigned to the column expression
     */
    @ApiModelProperty(value="Name of the evaluation edition assigned to the column expression",name="evaluationEdition")
    @Length(max = 128, message = "Name of the evaluation edition assigned to the column expression名长度最长为128")
    private String evaluationEdition;

    /**
     * Name of the oldest edition in which the column is usable
     */
    @ApiModelProperty(value="Name of the oldest edition in which the column is usable",name="unusableBefore")
    @Length(max = 128, message = "Name of the oldest edition in which the column is usable名长度最长为128")
    private String unusableBefore;

    /**
     * Name of the oldest edition in which the column becomes perpetually unusable
     */
    @ApiModelProperty(value="Name of the oldest edition in which the column becomes perpetually unusable",name="unusableBeginning")
    @Length(max = 128, message = "Name of the oldest edition in which the column becomes perpetually unusable名长度最长为128")
    private String unusableBeginning;

    /**
     * Collation name
     */
    @ApiModelProperty(value="Collation name",name="collation")
    @Length(max = 100, message = "Collation name名长度最长为100")
    private String collation;
}