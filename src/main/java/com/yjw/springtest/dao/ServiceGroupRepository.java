package com.yjw.springtest.dao;

import com.yjw.springtest.entity.Servicegroup;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ServiceGroupRepository extends JpaRepository<Servicegroup,Integer> {
    @Transactional
    public List<Servicegroup> findByGroupname(String groupname);

    @Transactional
    public void deleteByGroupname(String groupname);
}
