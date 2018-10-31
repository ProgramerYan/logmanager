package com.yjw.springtest.web;

import com.yjw.springtest.dao.AgentRepository;
import com.yjw.springtest.dao.ServiceRepository;
import com.yjw.springtest.entity.Agent;
import com.yjw.springtest.entity.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class SystemController {
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private AgentRepository agentRepository;

    @GetMapping("/getInfoCounts")
    public List<HashMap<String,String>> getInfoCounts() {
        List<HashMap<String,String>> hashMapList = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();
        List<Agent> agentList = agentRepository.findAll();
        List<Service> serviceList = serviceRepository.findAll();
        hashMap.put("agentcount",String.valueOf(agentList.size()));
        hashMap.put("servicecount",String.valueOf(serviceList.size()));
        hashMapList.add(hashMap);
        return hashMapList;
    }
}
