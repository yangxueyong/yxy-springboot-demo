package com.example.yxy.entity.table.io;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * null实体类
 * <p>
 * id null<br>
 * flinkFieldType null<br>
 * dbOracleFieldType null<br>
 * status null<br>
 * statusDesc null<br>
 * createTime null<br>
 * createUserNo null<br>
 * maintenanceTime null<br>
 * maintenanceUserNo null<br>
*
* @author 系统
* @date Create Time: Sun Nov 13 16:09:39 CST 2022
 */
@ApiModel(value="com.cqrcb.cloud.entity.activity.QueryOracleFieldIO",description="null")
@Data()
@NoArgsConstructor
public class QueryOracleFieldIO {

    @ApiModelProperty(value="tableName",name="tableName")
    @Length(max = 50, message = "tableName长度最长为50")
    private String tableName;

    @ApiModelProperty(value="tableSchema",name="tableSchema")
    @Length(max = 50, message = "tableSchema长度最长为50")
    private String tableSchema;

    @ApiModelProperty(value="fields",name="fields")
    @Length(max = 50, message = "fields长度最长为50")
    private List<String> fields;

    public QueryOracleFieldIO(String tableName, List<String> fields){
        if(tableName != null){
            this.tableName = tableName.toUpperCase();
        }
        if(fields != null){
            List<String> fieldss = new ArrayList<>();
            for (String field : fields) {
                if(field != null) {
                    fieldss.add(field.toUpperCase());
                }
            }
            this.fields = fieldss;
        }
    }
}