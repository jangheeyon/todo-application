package com.study.jpa.todo.controller;

import com.study.jpa.todo.dto.ResponseDTO;
import com.study.jpa.todo.dto.UserDTO;
import com.study.jpa.todo.model.UserEntity;
import com.study.jpa.todo.security.TokenProvider;
import com.study.jpa.todo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    //회원 가입 -> /signup
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody  UserDTO userDTO) {
        try {
            if(userDTO == null || userDTO.getPassword() == null) {
                throw new RuntimeException("Invalid Password value");
            }
            //param으로 받은 유저 정보를 엔티티에 넣기
            UserEntity user = UserEntity.builder()
                    .username(userDTO.getUsername())
                    .password(userDTO.getPassword())
                    .build();
            //유저 정보 저장
            UserEntity registeredUser = userService.create(user);
            //저장된 정보로 dto 객체 생성(응답에 필요한 변수만 값을 담아서 반환)
            UserDTO responseUserDTO = UserDTO.builder()
                    .id(registeredUser.getId())
                    .username(registeredUser.getUsername())
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e){
            ResponseDTO<Object> responseErrorDTO = ResponseDTO.builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(responseErrorDTO);
        }
    }
    //로그인 -> /signin
    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        UserEntity user = userService.getByCredential(userDTO.getUsername(), userDTO.getPassword());

        if(user != null) {
            //토큰 생성 후 dto에 반환
            final String token = tokenProvider.create(user);
            UserDTO responseUserDTO = UserDTO.builder()
                    .username(user.getUsername())
                    .id(user.getId())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            ResponseDTO<Object> responseErrorDTO = ResponseDTO.builder()
                    .error("Login failed")
                    .build();
            return ResponseEntity.badRequest().body(responseErrorDTO);
        }
    }
    /*
    {
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0MDI4ODBiZjhlNDY0ZDFlMDE4ZTQ2NGQzMWMxMDAwMCIsImlzcyI6ImRlbW8gYXBwIiwiaWF0IjoxNzEwNTc2NDU0LCJleHAiOjE3MTA2NjI4NTR9.DnP1vyUDKQospodt9XoqxPgJegE0uuCdg2zatJY5bjV57kUI8nfl9qIHFV9kco3__S2cS6bzQUSvB5WIDfT_dg",
    "username": "test",
    "password": null,
    "id": "402880bf8e464d1e018e464d31c10000"
    }
     */
}
