package com.yjw.springtest.entity;

public class ChildrenInTree {
    private String text;

    public ChildrenInTree() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ChildrenInTree{" +
                "text='" + text + '\'' +
                '}';
    }
}
