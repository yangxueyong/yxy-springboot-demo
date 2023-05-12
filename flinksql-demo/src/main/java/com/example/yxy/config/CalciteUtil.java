package com.example.yxy.config;

import com.example.yxy.config.entity.SqlBloodRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.tools.Frameworks;

@Slf4j
public class CalciteUtil {
    public static SqlBloodRes getSqlBloodRes(String sql) throws SqlParseException {
        // 创建Calcite的解析器对象
        SqlParser parser = SqlParser.create(sql, SqlParser.Config.DEFAULT);

        // 解析SQL语句并构建AST树
        SqlNode node = parser.parseStmt();

        //
        CustomTableVisitor visitor = new CustomTableVisitor();
        node.accept(visitor);
        SqlBloodRes tables = visitor.getTables();
        return tables;
    }
}
