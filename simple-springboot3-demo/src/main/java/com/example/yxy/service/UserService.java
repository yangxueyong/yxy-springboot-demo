package com.example.yxy.service;
 
import com.example.yxy.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
 
public interface UserService {
    /**
     * 查询全部用户信息
     *
     * @return
     */
    Flux<User> getAllUserInfo();
 
    /**
     * 根据ID获取用户信息
     *
     * @param id
     * @return
     */
    Mono<User> getUserById(Integer id);
 
    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    Mono<User> saveUser(User user);
 
    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    Mono<User> updateUser(User user);
 
    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    Mono<Void> deleteUser(Integer id);
}