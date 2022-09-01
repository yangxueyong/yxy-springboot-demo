package com.example.yxy.entity.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerVO {
    private String managerId;
    private String managerName;
    private String managerPhone;
}
