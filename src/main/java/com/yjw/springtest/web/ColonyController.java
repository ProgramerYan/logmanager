package com.yjw.springtest.web;

import com.yjw.springtest.dao.AgentRepository;
import com.yjw.springtest.dao.ColonyRepository;
import com.yjw.springtest.entity.Agent;
import com.yjw.springtest.entity.Colony;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ColonyController {
    @Autowired
    private ColonyRepository colonyRepository;
    @Autowired
    private AgentRepository agentRepository;

    /**
     * 添加集群
     * @param colonyname
     * @param colony
     * @return
     */
    @GetMapping("/addColony")
    public boolean addColony(@RequestParam("colonyname") String colonyname,@RequestParam("colony") String colony[]) {
        List<Colony> colonyList = colonyRepository.findByColonyname(colonyname);
        if(colonyList.size() > 0) {
            return false;
        }
        if(colony.length == 0) {
            return false;
        }else {
            for(int i = 0;i<colony.length;i++) {
                Agent agent = agentRepository.findByAgentname(colony[i]);
                Colony colony1 = new Colony();
                colony1.setColonyname(colonyname);
                colony1.setAgentname(colony[i]);
                colony1.setAgentaddress(agent.getAgentaddress());
                colonyRepository.save(colony1);
            }
            return true;
        }
    }

    /**
     * 获得集群,负责给前端选择集群下拉列表
     * @return
     */
    @GetMapping("/getColonyName")
    public List<String> getColony() {
        List<Colony> colonies = colonyRepository.findAll();
        List<String> stringList = new ArrayList<>();
        for(int i = 0;i<colonies.size();i++) {
            stringList.add(colonies.get(i).getColonyname());
        }
        for(int i = 0;i<stringList.size()-1;i++) {
            for(int j = stringList.size()-1; j>i;j--) {
                if(stringList.get(j).equals(stringList.get(i))) {
                    stringList.remove(j);
                }
            }
        }

        return stringList;
    }

    /**
     * 获取集群信息，传递给前端集群表格
     * @param colonyname
     * @return
     */
    @GetMapping("/getColonyData")
    public List<Colony> getColonyData(@RequestParam("colonyname") String colonyname) {
        List<Colony> colonyList = colonyRepository.findByColonyname(colonyname);
        return colonyList;
    }

    /**
     * 从集群中删除单个节点
     * @param colonyname
     * @param agentaddress
     */
    @GetMapping("/deleteAgentInColony")
    public void deleteAgentInColony(@RequestParam("colonyname") String colonyname,@RequestParam("agentaddress") String agentaddress) {
        colonyRepository.deleteByColonynameAndAgentaddress(colonyname,agentaddress);
    }
}
