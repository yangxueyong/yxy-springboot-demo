package com.example.yxy.controller;
 
import com.example.yxy.entity.User;
import com.example.yxy.entity.res.ApiResponse;
import com.example.yxy.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
 
import java.util.List;
 
 
@Tag(name = "用户管理", description = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
 
    @Operation(summary = "获取全部用户信息", description = "获取全部用户信息")
    @GetMapping(value = "getAllUserInfo")
    public Mono<ApiResponse<List<User>>> getAllUserInfo() {
        Flux<User> userInfo = userService.getAllUserInfo();
        return userInfo.collectList().map(user -> ApiResponse.success(user));
    }
 
    @Operation(summary = "根据ID获取用户信息", description = "根据ID获取用户信息")
    @GetMapping(value = "getUserById/{id}")
    public Mono<ApiResponse<User>> getUserById(@PathVariable("id") Integer id) {
        Mono<User> user = userService.getUserById(id);
        Mono<ApiResponse<User>> userMono = user.map(ApiResponse::success);
        return userMono;
    }
 
    @Operation(summary = "新增用户", description = "新增用户")
    @PostMapping(value = "saveUser")
    public Mono<ApiResponse> saveUser(@RequestBody User user) {
        Mono<User> userMono = userService.saveUser(user);
        return userMono.thenReturn(ApiResponse.success());
    }
 
    @Operation(summary = "更新用户", description = "更新用户")
    @PostMapping(value = "updateUser")
    public Mono<ApiResponse> updateUser(@RequestBody User user) {
        Mono<User> userMono = userService.updateUser(user);
        return userMono.thenReturn(ApiResponse.success());
    }
 
    @Operation(summary = "删除用户", description = "删除用户")
    @DeleteMapping(value = "deleteUser/{id}")
    public Mono<ApiResponse> deleteUser(@PathVariable("id") Integer id) {
        Mono<Void> userMono = userService.deleteUser(id);
        return userMono.thenReturn(ApiResponse.success());
    }
}