package com.yjw.springtest.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Colony {
    @Id
    private int id;
    private String colonyname;
    private String agentname;
    private String agentaddress;

    public Colony() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColonyname() {
        return colonyname;
    }

    public void setColonyname(String colonyname) {
        this.colonyname = colonyname;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getAgentaddress() {
        return agentaddress;
    }

    public void setAgentaddress(String agentaddress) {
        this.agentaddress = agentaddress;
    }

    @Override
    public String toString() {
        return "Colony{" +
                "id=" + id +
                ", colonyname='" + colonyname + '\'' +
                ", agentname='" + agentname + '\'' +
                ", agentaddress='" + agentaddress + '\'' +
                '}';
    }
}
