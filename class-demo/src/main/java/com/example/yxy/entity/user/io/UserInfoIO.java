package com.example.yxy.entity.user.io;

import com.example.yxy.entity.base.BaseIO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoIO extends BaseIO {
    private String param;
}
