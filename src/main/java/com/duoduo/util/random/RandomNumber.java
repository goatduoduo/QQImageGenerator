package com.duoduo.util.random;

import java.util.Random;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2022/11/13 19:51
 */
public class RandomNumber {
    static Random random= new Random();

    public static int getRandomInt(int lbound,int rbound){
        int temp = random.nextInt();
        if(temp < 0){
            temp = -temp;
        }
        temp %= rbound - lbound + 1;
        return temp + lbound;
    }
}
