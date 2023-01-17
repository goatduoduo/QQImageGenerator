package com.duoduo.component.entity;

import com.duoduo.component.Drawable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2022/11/13 13:17
 */
public class ChildEntity implements Drawable {
    @Override
    public void render(Graphics2D g2d) {
        System.out.println("this doesn't implemented");
    }
    protected Float x = Float.valueOf(0);
    protected Float y = Float.valueOf(0);
    private List<ChildEntity> children = new ArrayList<>();



    public List<ChildEntity> getChildren() {
        return children;
    }

    public void setChildren(List<ChildEntity> children) {
        this.children = children;
    }
}
