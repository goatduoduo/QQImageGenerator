package com.duoduo.bean;

import java.awt.*;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2022/12/4 22:05
 */
public class RewardBean implements Comparable<RewardBean> {
    private int weight;
    private int cash;
    private Color color;
    private String title;
    private String imagePath;

    public RewardBean(int weight, int cash, Color color, String title, String imagePath) {
        this.weight = weight;
        this.cash = cash;
        this.color = color;
        this.title = title;
        this.imagePath = imagePath;
    }


    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public int compareTo(RewardBean o) {
        return o.getCash() - this.getCash();
    }
}
