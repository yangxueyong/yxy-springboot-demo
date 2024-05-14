package com.example.yxy.fun;

import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.FunctionHint;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.types.Row;

@FunctionHint(output = @DataTypeHint("ROW<bdsId STRING,bdsFz STRING>"))
public class CustomSplitScoreFun extends TableFunction<Row> {
        public void eval(String str,String del1,String del2){
            String[] sp1 = str.split(del1);
            for (String s : sp1) {
                String[] sp2 = s.split(del2);
                collect(Row.of(sp2[0],sp2[1]));
            }
        }
    }