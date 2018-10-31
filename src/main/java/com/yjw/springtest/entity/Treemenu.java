package com.yjw.springtest.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Null;

@Entity
public class Treemenu {
    @Id
    private int id;
    private int parentid;
    private String name;

    public Treemenu() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Treemenu{" +
                "id=" + id +
                ", parentid=" + parentid +
                ", name='" + name + '\'' +
                '}';
    }
}
