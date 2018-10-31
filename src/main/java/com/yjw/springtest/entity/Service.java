package com.yjw.springtest.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Service {
    @Id
    private int id;
    private String servicename;
    private String serviceversion;
    private String maxsrc;
    private String colonyname;
    private String servicestatus;
    private String descrption;
    private String filename;
    private String serviceport;
    private String filepath;
    private String groupname;

    public Service() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getServiceversion() {
        return serviceversion;
    }

    public void setServiceversion(String serviceversion) {
        this.serviceversion = serviceversion;
    }

    public String getMaxsrc() {
        return maxsrc;
    }

    public void setMaxsrc(String maxsrc) {
        this.maxsrc = maxsrc;
    }

    public String getColonyname() {
        return colonyname;
    }

    public void setColonyname(String colonyname) {
        this.colonyname = colonyname;
    }

    public String getServicestatus() {
        return servicestatus;
    }

    public void setServicestatus(String servicestatus) {
        this.servicestatus = servicestatus;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getServiceport() {
        return serviceport;
    }

    public void setServiceport(String serviceport) {
        this.serviceport = serviceport;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", servicename='" + servicename + '\'' +
                ", serviceversion='" + serviceversion + '\'' +
                ", maxsrc='" + maxsrc + '\'' +
                ", colonyname='" + colonyname + '\'' +
                ", servicestatus='" + servicestatus + '\'' +
                ", descrption='" + descrption + '\'' +
                ", filename='" + filename + '\'' +
                ", serviceport='" + serviceport + '\'' +
                ", filepath='" + filepath + '\'' +
                ", groupname='" + groupname + '\'' +
                '}';
    }
}
