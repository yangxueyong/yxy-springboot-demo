package com.example.yxy.entity.table;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

public class OracleTabColumnWithBLOBs extends OracleTabColumn {
    /**
     * Default value for the column
     */
    @ApiModelProperty(value="Default value for the column",name="dataDefault")
    @Length(max = 0, message = "Default value for the column名长度最长为0")
    private String dataDefault;

    /**
     * The low value in the column
     */
    @ApiModelProperty(value="The low value in the column",name="lowValue")
    private byte[] lowValue;

    /**
     * The high value in the column
     */
    @ApiModelProperty(value="The high value in the column",name="highValue")
    private byte[] highValue;
}