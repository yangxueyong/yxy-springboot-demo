package com.example.yxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.yxy.entity.TestAutoIdEntity;
import com.example.yxy.entity.TestAutoIdEntity2;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TestMapper2 extends BaseMapper<TestAutoIdEntity2> {

}