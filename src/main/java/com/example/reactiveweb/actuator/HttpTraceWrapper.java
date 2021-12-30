package com.example.reactiveweb.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.data.annotation.Id;

@RequiredArgsConstructor
public class HttpTraceWrapper {
    @Id
    private String id;
    private final HttpTrace httpTrace;

    public HttpTrace getHttpTrace(){
        return httpTrace;
    }
}
