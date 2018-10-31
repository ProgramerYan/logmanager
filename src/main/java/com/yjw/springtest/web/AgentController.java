package com.yjw.springtest.web;

import com.yjw.springtest.dao.AgentRepository;
import com.yjw.springtest.entity.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AgentController {

    @Autowired
    private AgentRepository agentRepository;

    /**
     * 查询全部节点
     *
     * @return
     */
    @GetMapping("/getAgent")
    public List<Agent> getAgent() {
        List<Agent> list = agentRepository.findAll();
        return list;
    }

    /**
     * 添加代理节点
     */
    @GetMapping("/addAgent")
    public boolean addAgent(@RequestParam("agentname") String agentname, @RequestParam("agentaddress") String agentaddress) {
        Agent old_agent = agentRepository.findByAgentaddress(agentaddress);
        if (old_agent == null) {
            Agent agent = new Agent();
            agent.setAgentname(agentname);
            agent.setAgentaddress(agentaddress);
            agent.setAgentport(9090);
            Agent save_agent = agentRepository.save(agent);
            if (save_agent != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除单节点
     */
    @PostMapping("/deleteAgent")
    public void deleteAgent(@RequestParam("agentaddress") String agentaddress) {
        agentRepository.deleteByAgentaddress(agentaddress);
    }
}