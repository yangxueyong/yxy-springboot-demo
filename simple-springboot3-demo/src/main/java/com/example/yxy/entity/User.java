package com.example.yxy.entity;
 
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
 
 
@Schema(name = "User", description = "用户类")
@Table(name = "user")
@Data
public class User implements Persistable {
    @Id
    @Schema(name = "id", description = "用户ID")
    private Integer id;

    @Schema(name = "name", description = "用户名称")
    private String name;
 
    @Schema(name = "age", description = "用户年龄")
    private Integer age;


    @Transient
    @Schema(name = "newData", hidden = true)
    private boolean newData;

    @Override
    @Schema(name = "isNew", hidden = true)
    public boolean isNew() {
        return newData;
    }
}