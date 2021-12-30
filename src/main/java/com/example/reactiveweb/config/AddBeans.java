package com.example.reactiveweb.config;

import com.example.reactiveweb.actuator.HttpTraceWrapperRepository;
import com.example.reactiveweb.actuator.SpringDataHttpTraceRepository;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddBeans {
    // Spring Actuator 에서 HTTP Trace 기능 사용할 때 어떤 구현체를 쓸 것인가?
    // InMemoryHttpTraceRepository 는 테스트에 적합(Cloud, MSA 구조에서는 맞지 않음)
//    @Bean
//    HttpTraceRepository traceRepository (){
//        return new InMemoryHttpTraceRepository();
//    }

    // MongoDB를 Repository로 사용하는 HttpTraceRepository 를 최종 빈으로 등록시켜줌
    @Bean
    HttpTraceRepository springDataTraceRepository(HttpTraceWrapperRepository repository){
        return new SpringDataHttpTraceRepository(repository); // Repository 구현체를 Repository 호출 시에 연결되도록 해줌
    }
}
