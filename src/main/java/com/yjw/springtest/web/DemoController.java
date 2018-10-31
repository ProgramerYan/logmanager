package com.yjw.springtest.web;

import com.yjw.springtest.entity.Demo;
import com.yjw.springtest.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {
    @Autowired
    DemoService demoService;

    @GetMapping("/getDemo")
    public List<Demo> getDemo () {
        return demoService.getDemo();
    }
    @GetMapping("getDemo2")
    public List<Demo> getDemo2() {
        return demoService.getDemo2();
    }

    @GetMapping("getDemo3")
    public Demo getDemo3(@RequestParam("name") String name) {
        return demoService.getDemoByName(name);
    }
}
