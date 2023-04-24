package com.example.yxy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "T_P_R_M_AT")
public class RedAccount {
    @TableField("RED_ACT_NO")
    private String redActNo ;

    @TableField("ACCT_NO")
    private String acctNo ;

    @TableField("ACT_TYPE")
    private String actType ;

    @TableField("MER_NO")
    private String merNo ;

    @TableField("ACT_NO")
    private String actNo ;

    @TableField("MAIN_PRO_NO")
    private String mainProNo ;

    @TableField("sub_Pro_No")
    private String subProNo ;

    @TableField("prod_Quota")
    private BigDecimal prodQuota ;

    @TableField("use_Quota")
    private BigDecimal useQuota ;

    @TableField("TRAN_DAY")
    private String tranDay ;

    @TableField("CREATE_TIME")
    private Date createTime ;
}
