package com.yjw.springtest.dao;

import com.yjw.springtest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Transactional
    public User findByUsernameAndUserpass(String username,String userpass);
}
