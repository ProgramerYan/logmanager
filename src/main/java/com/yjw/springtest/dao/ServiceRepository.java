package com.yjw.springtest.dao;
import com.yjw.springtest.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ServiceRepository extends JpaRepository<Service,Integer> {
    @Transactional
    public void deleteByServicenameAndServiceversion(String servicename,String serviceversion);

    public List<Service> findByServicenameAndServiceversion(String servicename,String serviceversion);

    @Transactional
    public void deleteByGroupname(String groupname);

    public List<Service> findByGroupname(String groupname);
}
