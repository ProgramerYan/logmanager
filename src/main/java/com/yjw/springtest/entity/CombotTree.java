package com.yjw.springtest.entity;

import java.util.List;

public class CombotTree {
    private String text;
    private String state;
    private List<ChildrenInTree> children;

    public CombotTree() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<ChildrenInTree> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenInTree> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "CombotTree{" +
                "text='" + text + '\'' +
                ", state='" + state + '\'' +
                ", children=" + children +
                '}';
    }
}
