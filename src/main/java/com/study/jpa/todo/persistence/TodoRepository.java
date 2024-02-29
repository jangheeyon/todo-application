package com.study.jpa.todo.persistence;

import com.study.jpa.todo.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//JpaRepository<테이블에 매핑할 엔티티 클래스, id 키 타입>
@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    /**
     * 기본 제공하는 메서드가 아닌 경우
     * - jpa가 메서드 이름을 파싱해서 쿼리를 작성해서 실행한다!
     * -> SELECT * FROM Todo WHERE userId = '{userId}'
     */
    List<TodoEntity> findByUserId(String userId);

    /**
     * 복잡한 쿼리를 작성하는 경우
     * - @Query 어노테이션을 사용
     * ?1 은 메서드 매개변수의 순서 위치
     */
    //@Query("select * from TodoEntity t where t.userId = ?1")
    //List<TodoEntity> findByUserId2(String userId);
}
