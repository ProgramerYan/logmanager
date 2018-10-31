package com.yjw.springtest.dao;

import com.yjw.springtest.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface AgentRepository extends JpaRepository<Agent,Integer> {
    @Transactional
    public void deleteByAgentaddress(String agentaddress);

    public Agent findByAgentaddress(String agentaddress);

    public Agent findByAgentname(String agentname);
}
