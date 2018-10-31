package com.yjw.springtest.web;

import com.yjw.springtest.dao.TableRepository;
import com.yjw.springtest.entity.Tableinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TableController {
    @Autowired
    private TableRepository tableRepository;

    /**
     * 得到图表信息
     * @return
     */
    @GetMapping("/getTable")
    public List<Tableinfo> getTable() {
        List<Tableinfo> tableinfoList = tableRepository.findAll();
        return tableinfoList;
    }

    /**
     * 添加图表信息
     * @param tablename
     * @param colonyname
     * @param tabletype
     * @param descrption
     * @return
     */
    @GetMapping("/addTable")
    public boolean addTable(@RequestParam("tablename") String tablename,@RequestParam("colonyname") String colonyname,@RequestParam("tabletype") String tabletype,@RequestParam("descrption") String descrption) {
        Tableinfo tableinfo = new Tableinfo();
        tableinfo.setTablename(tablename);
        tableinfo.setColonyname(colonyname);
        tableinfo.setTabletype(tabletype);
        tableinfo.setDescrption(descrption);
        tableRepository.save(tableinfo);
        return true;
    }

    /**
     * 删除图表信息
     * @param tablename
     */
    @GetMapping("/deleteTable")
    public void deleteTable(@RequestParam("tablename") String tablename) {
        tableRepository.deleteByTablename(tablename);
    }

}
