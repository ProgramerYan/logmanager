package com.yjw.springtest.dao;

import com.yjw.springtest.entity.Servicegroupsecond;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ServiceGroupSecondRepository extends JpaRepository<Servicegroupsecond,Integer> {
    @Transactional
    public List<Servicegroupsecond> findByGroupname(String groupname);

    @Transactional
    public void deleteBySecondname(String secondname);

    @Transactional
    public void deleteByGroupname(String groupname);
}
