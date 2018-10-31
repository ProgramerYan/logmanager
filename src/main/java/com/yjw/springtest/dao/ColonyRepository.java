package com.yjw.springtest.dao;

import com.yjw.springtest.entity.Colony;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ColonyRepository extends JpaRepository<Colony,Integer> {
    @Transactional
    public List<Colony> findByColonyname(String colonyname);
    @Transactional
    public void deleteByColonynameAndAgentaddress(String colonyname,String agentaddress);
}
