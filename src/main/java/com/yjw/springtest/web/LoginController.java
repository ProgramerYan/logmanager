package com.yjw.springtest.web;

import com.yjw.springtest.dao.UserRepository;
import com.yjw.springtest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// jpa ddd 面向领域的设计 面向对象  面向服务 面向函数
@RestController
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/checkLogin")
    public boolean checkLogin(@RequestParam("username") String username,@RequestParam("userpass") String userpass) {
        User user = userRepository.findByUsernameAndUserpass(username,userpass);
        if(user != null) {
            return true;
        }
        return false;
    }

 }
