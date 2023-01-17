package com.duoduo.Bean;

import java.awt.*;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2022/12/4 22:05
 */
public class RewardBean implements Comparable<RewardBean>{
    private int weight;
    private int cash;
    private Color color;
    private String title;
    private int dynamicFix;

    private String imagePath;

    private int dynamicAdd;

    public RewardBean(int weight, int cash, Color color, String title, int dynamicFix) {
        this.weight = weight;
        this.cash = cash;
        this.color = color;
        this.title = title;
        this.dynamicFix = dynamicFix;
    }

    public RewardBean(int weight, int cash, Color color, String title, int dynamicFix, int dynamicAdd) {
        this.weight = weight;
        this.cash = cash;
        this.color = color;
        this.title = title;
        this.dynamicFix = dynamicFix;
        this.dynamicAdd = dynamicAdd;
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

    public int getDynamicFix() {
        return dynamicFix;
    }

    public void setDynamicFix(int dynamicFix) {
        this.dynamicFix = dynamicFix;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getDynamicAdd() {
        return dynamicAdd;
    }

    public void setDynamicAdd(int dynamicAdd) {
        this.dynamicAdd = dynamicAdd;
    }

    public RewardBean(int weight, int cash, Color color, String title, int dynamicFix,int dynamicAdd ,String imagePath) {
        this.weight = weight;
        this.cash = cash;
        this.color = color;
        this.title = title;
        this.dynamicFix = dynamicFix;
        this.imagePath = imagePath;
        this.dynamicAdd = dynamicAdd;
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
