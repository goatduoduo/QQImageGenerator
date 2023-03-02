package com.duoduo.util.random;

import java.util.Random;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2022/11/13 19:42
 */
public class RandomPercent {
    static Random random = new Random();

    /**
     * 根据百分比计算真假值
     *
     * @param percent 结果为真的百分比
     * @return 真假值
     */
    public static boolean randomTrue(int percent) {
        int ans = random.nextInt(100);
        return ans < percent;
    }

    /**
     * 根据分母计算真假值，适用于极低概率的情况
     * 比如百分之一或者千分之一
     * @param denominator 概率分母，如500则为平均500次抽中1次
     * @return 真假值
     */
    public static boolean randomTrueDenominator(int denominator){
        int ans = random.nextInt(denominator);
        return ans == 1;
    }
}