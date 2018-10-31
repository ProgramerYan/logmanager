package com.yjw.springtest.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tableinfo {
    @Id
    private int id;
    private String tablename;
    private String colonyname;
    private String tabletype;
    private String descrption;

    public Tableinfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getColonyname() {
        return colonyname;
    }

    public void setColonyname(String colonyname) {
        this.colonyname = colonyname;
    }

    public String getTabletype() {
        return tabletype;
    }

    public void setTabletype(String tabletype) {
        this.tabletype = tabletype;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    @Override
    public String toString() {
        return "Tableinfo{" +
                "id=" + id +
                ", tablename='" + tablename + '\'' +
                ", colonyname='" + colonyname + '\'' +
                ", tabletype='" + tabletype + '\'' +
                ", descrption='" + descrption + '\'' +
                '}';
    }
}
