package com.yjw.springtest.dao;

import com.yjw.springtest.entity.Demo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface DemoRepository extends JpaRepository<Demo,Integer> {
    @Transactional
    public Demo findDemoByName(String name);
}
