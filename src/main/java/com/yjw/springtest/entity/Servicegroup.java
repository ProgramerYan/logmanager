package com.yjw.springtest.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Servicegroup {
    @Id
    private int id;
    private String groupname;

    public Servicegroup() {
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

    @Override
    public String toString() {
        return "Servicegroup{" +
                "id=" + id +
                ", groupname='" + groupname + '\'' +
                '}';
    }
}
