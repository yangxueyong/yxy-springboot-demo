package com.example.springboot3r2dbcdemo.service;
 
import com.example.springboot3r2dbcdemo.dao.UserRepository;
import com.example.springboot3r2dbcdemo.entity.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
 
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;
 
    @Override
    public Flux<User> getAllUserInfo() {
        return userRepository.findAll();
    }
 
    @Override
    public Mono<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }
 
    @Override
    public Mono<User> saveUser(User user) {
        user.setNewData(true);
        return userRepository.save(user);
    }
 
    @Override
    public Mono<User> updateUser(User user) {
        return userRepository.updateUser(user);
    }
 
    @Override
    public Mono<Void> deleteUser(Integer id) {
        return userRepository.deleteById(id);
    }
}