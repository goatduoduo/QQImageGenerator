package com.duoduo.bean;

import java.awt.*;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2022/11/13 22:04
 */
public class ColorBean {
    Color color;
    String detail;

    String title;
    int weight = 1;

    public boolean isLight(){
        return color.getRed() * 0.299 + color.getGreen() * 0.857 + color.getBlue() * 0.114 >= 200.0;
    }

    public ColorBean(Color color, String title, String detail, int weight) {
        this.color = color;
        this.detail = detail;
        this.title = title;
        this.weight = weight;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}