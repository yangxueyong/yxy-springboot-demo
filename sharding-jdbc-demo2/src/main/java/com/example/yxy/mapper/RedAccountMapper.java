package com.example.yxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.yxy.entity.RedAccount;
import com.example.yxy.entity.io.RedAccountIO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RedAccountMapper extends BaseMapper<RedAccount> {
    List<RedAccount> selectByKey(RedAccountIO io);

    List<RedAccount> selectByRangeKey(RedAccountIO io);
}