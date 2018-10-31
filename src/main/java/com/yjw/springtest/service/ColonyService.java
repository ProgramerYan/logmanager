package com.yjw.springtest.service;

import com.yjw.springtest.dao.ColonyRepository;
import com.yjw.springtest.entity.Colony;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColonyService {
    @Autowired
    private ColonyRepository colonyRepository;

    /**
     * 查找集群信息
     * @return
     */
    @Cacheable(value = "colony")
    public List<Colony> getColonyService() {
        return colonyRepository.findAll();
    }

    /**
     * 通过集群名称查找集群信息
     * @param colonyname
     * @return
     */
    @Cacheable(value = "colony")
    public List<Colony> getColonyByNameService(String colonyname) {
        return colonyRepository.findByColonyname(colonyname);
    }

    /**
     * 刷新缓存中的集群信息
     */
    @CachePut(value = "colony")
    public void refreshColonyService() {
        colonyRepository.findAll();
    }
}
