package com.yjw.springtest.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Servicegroupsecond {
    @Id
    private int id;
    private String groupname;
    private String secondname;

    public Servicegroupsecond() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    @Override
    public String toString() {
        return "Servicegroupsecond{" +
                "id=" + id +
                ", groupname='" + groupname + '\'' +
                ", secondname='" + secondname + '\'' +
                '}';
    }
}
