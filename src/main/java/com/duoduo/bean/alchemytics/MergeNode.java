package com.duoduo.bean.alchemytics;

/**
 * @Author: goatduoduo
 * @Description: 合成的元素a和b，然后是合成的结果，一般来说合成没有先后顺序
 * @Date: Created in 2023/6/24 8:05
 */
public class MergeNode {
    private int a,b;
    private int mergeResult;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getMergeResult() {
        return mergeResult;
    }

    public void setMergeResult(int mergeResult) {
        this.mergeResult = mergeResult;
    }
}
