package com.duoduo.bean;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2023/6/23 20:37
 */
public class ElementItem {
    private Integer id;
    private String key;
    private Boolean prime;

    public String getName(){
        return "元素";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getPrime() {
        return prime;
    }

    public void setPrime(Boolean prime) {
        this.prime = prime;
    }
}
