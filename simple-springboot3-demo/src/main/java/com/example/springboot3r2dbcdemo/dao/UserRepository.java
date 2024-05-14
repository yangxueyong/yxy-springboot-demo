package com.example.springboot3r2dbcdemo.dao;

import com.example.springboot3r2dbcdemo.entity.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
 
 
@Repository
public interface UserRepository extends R2dbcRepository<User, Integer> {
    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @Query("update user set name = :#{#user.name}, age = :#{#user.age} where id = :#{#user.id}")
    Mono<User> updateUser(@Param("user") User user);
 
 
    /**
     * 动态更新用户信息
     *
     * @param user
     * @return
     */
    @Query("UPDATE User SET " +
            "name = CASE WHEN :#{#user.name} IS NULL THEN name ELSE :#{#user.name} END, " +
            "age = CASE WHEN :#{#user.age} IS NULL THEN age ELSE :#{#user.age} END " +
            "WHERE id = :#{#user.id}")
    Mono<User> updateUserQuery(@Param("user") User user);
}