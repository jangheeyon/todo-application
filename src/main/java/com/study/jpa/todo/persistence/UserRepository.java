package com.study.jpa.todo.persistence;

import com.study.jpa.todo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    //이름으로 유저 정보 찾기
    UserEntity findByUsername(String username);
    //이름으로 유저 존재 여부 확인
    Boolean existsByUsername(String username);
    //이름과 패스워드로 유저 정보 찾기
    UserEntity findByUsernameAndPassword(String username, String password);
}
