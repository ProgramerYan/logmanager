package com.yjw.springtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.RestController;

@RestController  // todo: rest api web servlet（面向服务的思想）　ＳＯＡ  http 传List  soap 重　AXIS2 cxf hessian
@SpringBootApplication
@EnableCaching
public class SpringtestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringtestApplication.class, args);
    }
}
