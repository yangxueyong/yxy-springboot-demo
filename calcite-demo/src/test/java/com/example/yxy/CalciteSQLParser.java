package com.example.yxy;

import org.apache.calcite.config.Lex;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.parser.SqlParserPos;
import org.apache.calcite.sql.parser.impl.SqlParserImpl;
import org.apache.calcite.sql.validate.SqlConformanceEnum;
//import org.apache.calcite.util.ImmutableBeans;
 
 
/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: yuanhongjun
 * DateTime: 2021-04-13 9:25
 */
public class CalciteSQLParser {
 
    private static SqlParser.Config config = SqlParser.configBuilder().setLex(Lex.MYSQL).setCaseSensitive(true).setConformance(SqlConformanceEnum.MYSQL_5).build();
    //.setConformance(SqlConformanceEnum.MYSQL_5)  limit 10,10
    //.setCaseSensitive(true)大小写敏感 
 
//    private static SqlParser.Config DEFAULT = (ImmutableBeans.create(SqlParser.Config.class)).withLex(Lex.MYSQL).withIdentifierMaxLength(128).withConformance(SqlConformanceEnum.DEFAULT).withParserFactory(SqlParserImpl.FACTORY);
 
 
    public static void main(String[] args) throws Exception {
 
 
//        String sql = "select sum(b) as dd , b.c from db.d where e = x and f not in (x,d)";
 
//        String sql = "SELECT sum(x.dd) as xx ,2 from db.a x where id = xx and c = 'zz' group by xx order by dd limit 10 " ;
//        String sql = "SELECT sum(x.dd) as xx ,2 from db.a x where id = xx and c = 'zz' order by dd limit 10 ";
//        String sql = "SELECT sum(f) as xx,e FROM db.B left join B.dd on dd.xx=b.cc WHERE g = h";
//        String sql = "SELECT sum(f) as xx,e FROM db.B left join (select xx from B.dd union select xx from d.dddddd) as bdd on dd.xx=b.cc WHERE g = h";
        String sql = "SELECT sum(x.dd) as xx ,2 from db.a x where id = xx and c = 'zz'  " +
                "union all SELECT sum(f) as xx,e FROM db.B left join B.dd on dd.xx=b.cc WHERE g = h limit 10,10";
        //当存在子查询和order by的时候都可能需要传入到Select
 
        SqlParser sqlParser = SqlParser.create(sql, config);
        try {
            SqlNode sqlNode = sqlParser.parseQuery();
 
            System.out.println(sqlNode.toString());
 
            hanlerSQL(sqlNode);
 
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
 
    }
 
    private static void hanlerSQL(SqlNode sqlNode) {
        SqlKind kind = sqlNode.getKind();
        switch (kind) {
            case SELECT:
                hanlerSelect(sqlNode);
                break;
            case UNION:
                ((SqlBasicCall) sqlNode).getOperandList().forEach(node -> {
                    hanlerSQL(node);
                });
 
                break;
            case ORDER_BY:
                handlerOrderBy(sqlNode);
                break;
        }
    }
 
    private static void handlerOrderBy(SqlNode node) {
        SqlOrderBy sqlOrderBy = (SqlOrderBy) node;
        SqlNode query = sqlOrderBy.query;
        hanlerSQL(query);
        SqlNodeList orderList = sqlOrderBy.orderList;
        handlerField(orderList);
    }
 
 
    private static void hanlerSelect(SqlNode select) {
 
        SqlSelect sqlSelect = (SqlSelect) select;
 
        //TODO 改写SELECT的字段信息
 
        SqlNodeList selectList = sqlSelect.getSelectList();
        //字段信息
        selectList.getList().forEach(list -> {
            handlerField(list);
        });
 
        handlerFrom(sqlSelect.getFrom());
 
        if (sqlSelect.hasWhere()) {
            handlerField(sqlSelect.getWhere());
        }
 
        if (sqlSelect.hasOrderBy()) {
            handlerField(sqlSelect.getOrderList());
        }
 
        SqlNodeList group = sqlSelect.getGroup();
        if (group != null) {
            group.forEach(groupField -> {
                handlerField(groupField);
            });
        }
 
 
        SqlNode fetch = sqlSelect.getFetch();
        if (fetch != null) {
            //TODO limit
        }
 
    }
 
    private static void handlerFrom(SqlNode from) {
        SqlKind kind = from.getKind();
 
        switch (kind) {
            case IDENTIFIER:
                //最终的表名
                SqlIdentifier sqlIdentifier = (SqlIdentifier) from;
                //TODO 表名的替换，所以在此之前就需要获取到模型的信息
                System.out.println("==tablename===" + sqlIdentifier.toString());
                break;
            case AS:
                SqlBasicCall sqlBasicCall = (SqlBasicCall) from;
                SqlNode selectNode = sqlBasicCall.getOperandList().get(0);
                hanlerSQL(selectNode);
                break;
            case JOIN:
                SqlJoin sqlJoin = (SqlJoin) from;
                SqlNode left = sqlJoin.getLeft();
                hanlerSQL(left);
                SqlNode right = sqlJoin.getRight();
                hanlerSQL(right);
                SqlNode condition = sqlJoin.getCondition();
                handlerField(condition);
                break;
            case SELECT:
                hanlerSQL(from);
                break;
        }
    }
 
 
    private static void handlerField(SqlNode field) {
        SqlKind kind = field.getKind();
        switch (kind) {
            case AS:
                SqlNode[] operands_as = ((SqlBasicCall) field).operands;
                SqlNode left_as = operands_as[0];
                handlerField(left_as);
                break;
            case IDENTIFIER:
                //表示当前为子节点
                SqlIdentifier sqlIdentifier = (SqlIdentifier) field;
                System.out.println("===field===" + sqlIdentifier.toString());
                break;
            default:
                if (field instanceof SqlBasicCall) {
                    SqlNode[] nodes = ((SqlBasicCall) field).operands;
                    for (int i = 0; i < nodes.length; i++) {
                        handlerField(nodes[i]);
                    }
                }
                if (field instanceof SqlNodeList) {
                    ((SqlNodeList) field).getList().forEach(node -> {
                        handlerField(node);
                    });
                }
                break;
        }
 
 
    }
 
 
}