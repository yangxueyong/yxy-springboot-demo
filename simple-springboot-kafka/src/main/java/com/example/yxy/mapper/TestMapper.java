package com.example.yxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.yxy.entity.TestAutoIdEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TestMapper extends BaseMapper<TestAutoIdEntity> {
    Map selectIntMap();

    int selectInt();

    List selectTimeOut(Map param);

    int saveReturnPK(TestAutoIdEntity testAutoIdEntity);
}