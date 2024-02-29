package com.study.jpa.todo.controller;

import com.study.jpa.todo.dto.ResponseDTO;
import com.study.jpa.todo.dto.TestRequestBodyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping()
    public String testController() {
        return"Hello World!";
    }

    @GetMapping("testGetMapping")
    public String testControllerWithPath() {
        return "Hello World! Test get mapping!";
    }

    @GetMapping("/{id}")
    public String testPathValiables(@PathVariable(required = false) int id) {
        return "test PathValiables! ID = " + id;
    }

    @GetMapping("testRequestParam")
    public String testRequestParam(@RequestParam(required = false) int id) {
        return "test RequestParam! ID = " + id;
    }

    /**
     * 클라이언트는 요청 바디로 json 형태의 문자열을 보냄(다양한 자료형, 오브젝트 등)
     * @RequestBody로 json을 받음
     * dto 오브젝트로 변환해서 가져옴
     */
    @GetMapping("testRequestBody")
    public String testRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
        return "test RequestBody! ID = " + testRequestBodyDTO.getId() + " message : " + testRequestBodyDTO.getMessage();
    }

    /**
     * 서버는 응답을 바디에 담아 보내면 json 형태로 리턴됨
     * 직렬화 : 오브젝트 -> JSON 변환
     * 역직렬화 : JSON -> 오브젝트로 변환
     */
    @GetMapping("testResponseBody")
    public ResponseDTO<String> testResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("This is responseBody test");
        return ResponseDTO.<String>builder().data(list).build();
    }

    /**
     * ResponseEntity 반환
     * - HTTP 응답 + 여러 매개변수들 보낼 수 있음
     */
    @GetMapping("testResponseEntity")
    public ResponseEntity<?>  testResponseEntity() {
        List<String> list = new ArrayList<>();
        list.add("This is responseEntity, and you got 400 error code!");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.badRequest().body(response);
    }

}
