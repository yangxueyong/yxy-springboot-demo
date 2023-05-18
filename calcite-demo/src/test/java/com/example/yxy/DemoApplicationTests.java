package com.example.yxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.calcite.config.Lex;
import org.apache.calcite.schema.SchemaPlus;
//import org.apache.calcite.schema.Table;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.dialect.OracleSqlDialect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.util.SqlBasicVisitor;
import org.apache.calcite.tools.Frameworks;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static org.apache.calcite.sql.SqlKind.*;

@Data
class SqlBloodRes {

    private Map<String, String> sourceTables = new HashMap<>();

    private String sinkTable;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Table {
    private String name;
}

@SpringBootTest
class DemoApplicationTests {

    @Test
    void getTable() {
        SqlParser.Config config = SqlParser.configBuilder().setLex(Lex.MYSQL).build();
        SqlParser sqlParser = SqlParser
                .create("insert into market.tab2 select * from pft.tab1 a inner join pft.tab3 b on a.id = b.id  and b.group_id in (select group_id from pft.table4 where age > 20)", config);

        // 生成 AST 语法树
        SqlNode sqlNode;
        try {
            sqlNode = sqlParser.parseStmt();
        } catch (SqlParseException e) {
            throw new RuntimeException("使用 Calcite 进行语法分析发生了异常", e);
        }
        SqlBloodRes res = new SqlBloodRes();
        // 递归遍历语法树
        getDependencies(sqlNode, res, false);
        System.out.println("-->");
    }

    private SqlBloodRes getDependencies(SqlNode sqlNode, SqlBloodRes res, Boolean fromOrJoin) {
        if (sqlNode.getKind() == JOIN) {
            SqlJoin sqlKind = (SqlJoin) sqlNode;
            getDependencies(sqlKind.getLeft(), res, true);
            getDependencies(sqlKind.getRight(), res, true);
        } else if (sqlNode.getKind() == IDENTIFIER) {
            if (fromOrJoin) {
                // 获取 source 表名
                res.getSourceTables().put(sqlNode.toString(), sqlNode.toString());
            }
        } else if (sqlNode.getKind() == AS) {
            SqlBasicCall sqlKind = (SqlBasicCall) sqlNode;
            if (sqlKind.getOperandList().size() >= 2) {
                getDependencies(sqlKind.getOperandList().get(0), res, fromOrJoin);
            }
        } else if (sqlNode.getKind() == INSERT) {
            SqlInsert sqlKind = (SqlInsert) sqlNode;
            // 获取 sink 表名
            res.setSinkTable(sqlKind.getTargetTable().toString());
            getDependencies(sqlKind.getSource(), res, false);
        } else if (sqlNode.getKind() == SELECT) {
            SqlSelect sqlKind = (SqlSelect) sqlNode;
            List<SqlNode> list = sqlKind.getSelectList().getList();
            for (SqlNode i : list) {
                getDependencies(i, res, false);
            }
            getDependencies(sqlKind.getFrom(), res, true);
        } else if (sqlNode.getKind() == SNAPSHOT) {
            // 处理 Lookup join 的情况
            SqlSnapshot sqlKind = (SqlSnapshot) sqlNode;
            getDependencies(sqlKind.getTableRef(), res, true);
        } else {
            // TODO 这里可根据需求拓展处理其他类型的 sqlNode
        }
        return res;
    }

    public static void main(String[] args) throws SqlParseException {
        new DemoApplicationTests().getTable2();
    }

    /**
     * 得到sql语句中的所有表
     *
     * @throws SqlParseException sql解析异常
     */
    @Test
    void getTable2() throws SqlParseException {
        // 创建Calcite的Schema对象
        SchemaPlus schema = Frameworks.createRootSchema(true);

        // 创建Calcite的解析器对象
        SqlParser parser = SqlParser.create("insert into market.tab2 select * from pft.tab1 a inner join pft.tab3 b on a.id = b.id " +
                " left join (select max(bal) as bal from pft.tab5 t5 inner join pft.tab6 t6 on t5.id=t6.id group by kd) c on c.id=a.id " +
                " left join (select max(hh) from pft.tab7 join pft.tab8 on pft.tab7.cid = pft.tab8.cid) k on k.c=a.id" +
                " and b.group_id in (select group_id from pft.table4 where age > 20)", SqlParser.Config.DEFAULT);

        // 解析SQL语句并构建AST树
        SqlNode node = parser.parseStmt();
        //
        TableVisitor visitor = new TableVisitor();
        node.accept(visitor);
        SqlBloodRes tables = visitor.getTables();
        System.out.println("tables->" + tables);
    }

    public class TableVisitor extends SqlBasicVisitor<Void> {
        private SqlBloodRes tables;

        public TableVisitor() {
            this.tables = new SqlBloodRes();
        }

        public SqlBloodRes getTables() {
            return tables;
        }

        @Override
        public Void visit(SqlIdentifier id) {
            String name = id.toString();
            if (id.getKind() == SqlKind.AS) {
                name = id.getComponent(0).toString();
                System.out.println("name -->" + name);
            }
            return super.visit(id);
        }

        @Override
        public Void visit(SqlLiteral literal) {
//            System.out.println("literal ====-> " + literal);
            return super.visit(literal);
        }

        @Override
        public Void visit(SqlDataTypeSpec type) {
//            System.out.println("type ====-> " + type);
            return super.visit(type);
        }

        @Override
        public Void visit(SqlNodeList nodeList) {
//            System.out.println("nodeList ====-> " + nodeList);
            return super.visit(nodeList);
        }

        @Override
        public Void visit(SqlDynamicParam param) {
//            System.out.println("param -> " + param);
            return super.visit(param);
        }

        @Override
        public Void visit(SqlCall call) {
//            System.out.println("call -> " + call);
            String scoreTable = null;
            if(call instanceof SqlInsert){
                SqlNode targetTable = ((SqlInsert) call).getTargetTable();
                tables.setSinkTable(targetTable.toString());
            }else if(call instanceof SqlBasicCall){
                SqlOperator operator = (call).getOperator();
                SqlNode[] operands = ((SqlBasicCall) call).operands;
                if("AS".equals(operator.toString()) && operands != null && operands[0] != null && operands[0] instanceof SqlIdentifier){
                    scoreTable = (operands[0]).toString();
                }
            }else if(call instanceof SqlSelect){
                SqlNode sqlNode = ((SqlSelect) call).getFrom();
                if(sqlNode instanceof SqlIdentifier) {
                    scoreTable = sqlNode.toString();
                }
            }
            if(scoreTable != null) {
                tables.getSourceTables().put(scoreTable, scoreTable);
            }
            return super.visit(call);
        }

        @Override
        public Void visit(SqlIntervalQualifier intervalQualifier) {
//            System.out.println("intervalQualifier 0000000000-> " + intervalQualifier);
            return super.visit(intervalQualifier);
        }
    }


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

    /**
     * 将sql拆解为笛卡尔积sql
     */
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
        if (sqlNode instanceof SqlBasicCall) {
            sqlBasicCall = (SqlBasicCall) sqlNode;
        }
        SqlNode[] operands = sqlBasicCall.operands;
        if (operands == null || operands.length == 0) {
            return;
        }
        List<String> sqls = new ArrayList<>();
        for (SqlNode operand : operands) {
            getSqlNode(operand, sqls);
        }
        for (String sql : sqls) {
            System.out.println("sql->" + sql);
        }
        // 还原某个方言的SQL
        System.out.println(sqlNode.toSqlString(OracleSqlDialect.DEFAULT));
    }

    void getSqlNode(SqlNode operand, List<String> sqls) {
        if (operand instanceof SqlBasicCall) {
            SqlBasicCall sqlBasicCall = (SqlBasicCall) operand;
            String operator = sqlBasicCall.getOperator().toString();
            //如果是and则向下拆解一层
            if ("AND".equals(operator)) {
                SqlNode[] operands = sqlBasicCall.operands;
                StringBuilder sb = null;
                for (SqlNode sqlNode : operands) {
                    if (sqlNode instanceof SqlBasicCall) {
                        SqlBasicCall sqlBasicCall1 = (SqlBasicCall) sqlNode;
                        SqlOperator operator1 = sqlBasicCall1.getOperator();
                        if (operatorSet.contains(operator1.toString())) {
                            if (sb == null) {
                                sb = new StringBuilder();
                            }
                            sb.append(sqlBasicCall1 + " AND ");
                        } else {
                            getSqlNode(sqlNode, sqls);
                        }
                    }
                }
                if (sb != null) {
                    sqls.add(sb.toString());
                }
            } else {
                SqlNode[] operands = sqlBasicCall.operands;
                for (SqlNode sqlNode : operands) {
                    if (sqlNode instanceof SqlBasicCall) {
                        SqlBasicCall sqlBasicCall1 = (SqlBasicCall) sqlNode;
                        SqlOperator operator1 = sqlBasicCall1.getOperator();
                        if (operatorSet.contains(operator1.toString())) {
                            sqls.add(sqlBasicCall1.toString());
                        } else {
                            getSqlNode(sqlNode, sqls);
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