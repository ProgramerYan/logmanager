package com.yjw.springtest.dao;

import com.yjw.springtest.entity.Tableinfo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface TableRepository extends JpaRepository<Tableinfo,Integer> {
    @Transactional
    public void deleteByTablename(String tablename);
}
