package com.yjw.springtest.service;

import com.yjw.springtest.dao.AgentRepository;
import com.yjw.springtest.entity.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 节点服务：负责节点信息缓存
 */
@Service
public class AgentService {
    @Autowired
    private AgentRepository agentRepository;

    /**
     * 从缓存中获得节点信息
     * @return
     */
    @CachePut(value = "agent")
    public List<Agent> getAgentService() {
        List<Agent> list = agentRepository.findAll();
        return list;
    }

    /**
     * 通过IP地址查询单个节点信息
     * @param agentaddress
     * @return
     */
    @CachePut(value = "agent")
    public Agent addAgentService(String agentaddress) {
        return agentRepository.findByAgentaddress(agentaddress);
    }

    /**
     * 通过 节点名称 查询单节点信息
     * @param agentname
     * @return
     */
    @Cacheable(value = "agent")
    public Agent getAgentByNameService(String agentname) {
        return agentRepository.findByAgentname(agentname);
    }

    /**
     * 刷新节点缓存信息，并且返回最新节点信息
     * @return
     */
    @CachePut(value = "agent")
    public void refreshAgentService() {
        agentRepository.findAll();
    }
}
