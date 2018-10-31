package com.yjw.springtest.service;

import com.yjw.springtest.dao.DemoRepository;
import com.yjw.springtest.entity.Demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService {
    @Autowired
    DemoRepository demoRepository;

    @Cacheable(value = "demo")
    public List<Demo> getDemo() {
        List<Demo> demoList = demoRepository.findAll();
        System.out.println("启用缓存");
        return demoList;
    }

    @Cacheable(value = "demo")
    public Demo getDemoByName(String name) {
        Demo demo = demoRepository.findDemoByName(name);
        System.out.println("查询");
        return demo;
    }

    @CachePut(value = "demo")
    public List<Demo> getDemo2() {
        List<Demo> demoList = demoRepository.findAll();
        System.out.println("CachePut启用缓存");
        return demoList;
    }
}
