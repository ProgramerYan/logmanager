package com.yjw.springtest.dao;

import com.yjw.springtest.entity.Treemenu;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface TreemenuRepository extends JpaRepository<Treemenu,Integer> {
    @Transactional
    public Treemenu findByName(String name);

    public List<Treemenu> findByParentid(int number);

    public void deleteById(int number);

    public List<Treemenu> findById(int id);
}
