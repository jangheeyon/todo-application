package com.study.jpa.todo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class UserEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)   //OAuth를 이용해 SSO를 이용해 로그인 할 것이라 패스워드 null 이여도됨->db에 null 입력 가능, 컨트롤러에서 패스워드 입력하도록 함
    private String username;

    private String password;
    private String role;
    private String authProvider;
}
