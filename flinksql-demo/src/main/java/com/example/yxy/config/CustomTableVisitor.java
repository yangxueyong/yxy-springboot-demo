package com.example.yxy.config;

import com.example.yxy.config.entity.SqlBloodRes;
import org.apache.calcite.sql.*;
import org.apache.calcite.sql.util.SqlBasicVisitor;

public class CustomTableVisitor extends SqlBasicVisitor<Void> {
    private SqlBloodRes tables;

    public CustomTableVisitor() {
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
        }
        return super.visit(id);
    }

    @Override
    public Void visit(SqlLiteral literal) {
        return super.visit(literal);
    }

    @Override
    public Void visit(SqlDataTypeSpec type) {
        return super.visit(type);
    }

    @Override
    public Void visit(SqlNodeList nodeList) {
        return super.visit(nodeList);
    }

    @Override
    public Void visit(SqlDynamicParam param) {
        return super.visit(param);
    }

    /**
     * 拆解源表和sink表
     *
     * @param call 调用
     * @return {@link Void}
     */
    @Override
    public Void visit(SqlCall call) {
        String scoreTable = null;
        if (call instanceof SqlInsert) {
            SqlNode targetTable = ((SqlInsert) call).getTargetTable();
            tables.setSinkTable(targetTable.toString());
        } else if (call instanceof SqlBasicCall) {
            SqlOperator operator = (call).getOperator();
            SqlNode[] operands = ((SqlBasicCall) call).operands;
            if ("AS".equals(operator.toString())
                    && operands != null
                    && operands[0] != null
                    && operands[0] instanceof SqlIdentifier) {
                scoreTable = (operands[0]).toString();
            }
        } else if (call instanceof SqlSelect) {
            SqlNode sqlNode = ((SqlSelect) call).getFrom();
            if (sqlNode instanceof SqlIdentifier) {
                scoreTable = sqlNode.toString();
            }
        }
        if (scoreTable != null) {
            SqlBloodRes.MySourceTable mySourceTable = new SqlBloodRes.MySourceTable();
            mySourceTable.setTableName(scoreTable);

            tables.getSourceTables().add(mySourceTable);
        }
        return super.visit(call);
    }

    @Override
    public Void visit(SqlIntervalQualifier intervalQualifier) {
        return super.visit(intervalQualifier);
    }
}