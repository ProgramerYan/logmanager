package com.yjw.springtest.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Agent {
    @Id
    private int id;
    private String agentname;
    private String agentaddress;
    private int agentport;

    public Agent() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAgentport() {
        return agentport;
    }

    public void setAgentport(int agentport) {
        this.agentport = agentport;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "id=" + id +
                ", agentname='" + agentname + '\'' +
                ", agentaddress='" + agentaddress + '\'' +
                ", agentport=" + agentport +
                '}';
    }
}
