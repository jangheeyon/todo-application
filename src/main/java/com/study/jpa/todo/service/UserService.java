package com.study.jpa.todo.service;

import com.study.jpa.todo.model.UserEntity;
import com.study.jpa.todo.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //유저 등록(회원 가입)
    public UserEntity create(final UserEntity userEntity) {
        //유저 정보 들어온게 없으면 예외 처리
        if(userEntity == null || userEntity.getUsername() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        //유저 이름으로 유저 정보가 존재하는지 확인 -> 존재하면 가입 안됨
        final String username = userEntity.getUsername();
        if(userRepository.existsByUsername(username)) {
            log.warn("Username already exists {}", username);
            throw new RuntimeException("Username already exists");
        }
        return userRepository.save(userEntity);
    }

    //로그인 시 인증에 사용
    public UserEntity getByCredential(final String username, final String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
