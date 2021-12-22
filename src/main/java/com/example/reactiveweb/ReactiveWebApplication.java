package com.example.reactiveweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.thymeleaf.TemplateEngine;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class ReactiveWebApplication {
    public static void main(String[] args) {
        // BlockHound: Java Agent API 를 이용해 블록킹 메서드를 검출, 해당 스레드가 블록킹 메서드를 허용하는지 검사)
        // SpringBootApplication 보다 먼저 실행되면서 Bytecode 조작이 가능해짐 + -XX:+AllowRedefinitionToAddDeleteMethods
        BlockHound.builder()
                        // 초기화면 브라우저에서 실행시 블록킹 메서드 검출되어서 TemplateEngine 처리하는 부분 예외 적용(최대한 좁은 범위를 설정해야함)
                        .allowBlockingCallsInside(TemplateEngine.class.getCanonicalName(), "process")
                  .install();

        SpringApplication.run(ReactiveWebApplication.class, args);
    }
}
