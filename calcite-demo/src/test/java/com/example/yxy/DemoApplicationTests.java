package com.example.yxy;

import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.SqlBasicCall;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlOperator;
import org.apache.calcite.sql.dialect.OracleSqlDialect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.*;

@SpringBootTest
class DemoApplicationTests {
 
    /**
     * 保存数据到交易库
     */
    @Test
    void getSqlNode1() {
        // 解析配置 - mysql设置
        SqlParser.Config mysqlConfig = SqlParser.configBuilder().setLex(Lex.MYSQL).build();
        // 创建解析器
        SqlParser parser = SqlParser.create("", mysqlConfig);
        // Sql语句
        String sql = "select * from emps where ((dengji = '金卡' or dengji='白金') and ((dengji='财富' and dengji='zs') or dengji='ph')) and (sex='男' or sex='女') or (age=20 or age=80)";
        // 解析sql
        SqlNode sqlNode = null;
        try {
            sqlNode = parser.parseQuery(sql);
        } catch (SqlParseException e) {
            throw new RuntimeException(e);
        }
        // 还原某个方言的SQL
        System.out.println(sqlNode.toSqlString(OracleSqlDialect.DEFAULT));
    }

    @Test
    void getSqlNode2() {
        // 解析配置 - mysql设置
        SqlParser.Config mysqlConfig = SqlParser.configBuilder().setLex(Lex.MYSQL).build();
        String exp = "((dengji = '金卡' or dengji='白金' or dengji in ('a','b','d') or aa>timestamp '2022-12-12') and ((dengji='财富' and dengji='zs') or dengji='ph')) and (sex='男' or sex='女') or (age=20 or age=80)";
        SqlParser expressionParser = SqlParser.create(exp, mysqlConfig);
        // Sql语句
        // 解析sql
        SqlNode sqlNode = null;
        try {
            sqlNode = expressionParser.parseExpression();
        } catch (SqlParseException e) {
            throw new RuntimeException(e);
        }
        SqlBasicCall sqlBasicCall = null;
        if(sqlNode instanceof SqlBasicCall){
            sqlBasicCall = (SqlBasicCall)sqlNode;
        }
        SqlNode[] operands = sqlBasicCall.operands;
        if(operands == null || operands.length == 0){
            return;
        }
        List<String> sqls = new ArrayList<>();
        for (SqlNode operand : operands) {
            getSqlNode(operand,sqls);
        }
        // 还原某个方言的SQL
        System.out.println(sqlNode.toSqlString(OracleSqlDialect.DEFAULT));
    }

    void getSqlNode(SqlNode operand,List<String> sqls){
        if(operand instanceof SqlBasicCall){
            SqlBasicCall sqlBasicCall = (SqlBasicCall)operand;
            String operator = sqlBasicCall.getOperator().toString();
            //如果是and则向下拆解一层
            if("AND".equals(operator)){
                SqlNode[] operands = sqlBasicCall.operands;
                StringBuilder sb = null;
                for (SqlNode sqlNode : operands) {
                    if(sqlNode instanceof SqlBasicCall){
                        SqlBasicCall sqlBasicCall1 = (SqlBasicCall)sqlNode;
                        SqlOperator operator1 = sqlBasicCall1.getOperator();
                        if(operatorSet.contains(operator1.toString())){
                            if(sb == null){
                                sb = new StringBuilder();
                            }
                            sb.append(sqlBasicCall1 + " AND ");
                        }else{
                            getSqlNode(sqlNode,sqls);
                        }
                    }
                }
                if(sb != null){
                    sqls.add(sb.toString());
                }
            }else{
                SqlNode[] operands = sqlBasicCall.operands;
                for (SqlNode sqlNode : operands) {
                    if (sqlNode instanceof SqlBasicCall) {
                        SqlBasicCall sqlBasicCall1 = (SqlBasicCall)sqlNode;
                        SqlOperator operator1 = sqlBasicCall1.getOperator();
                        if(operatorSet.contains(operator1.toString())){
                            sqls.add(sqlBasicCall1.toString());
                        }else{
                            getSqlNode(sqlNode,sqls);
                        }
                    }
                }
            }
        }
    }

    public static final Set<String> operatorSet = new HashSet<>();
    static {
        operatorSet.add("=");
        operatorSet.add(">");
        operatorSet.add(">=");
        operatorSet.add("<");
        operatorSet.add("<=");
        operatorSet.add("<>");
        operatorSet.add("!=");
    }
}