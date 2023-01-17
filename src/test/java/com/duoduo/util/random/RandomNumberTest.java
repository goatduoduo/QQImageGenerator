package com.duoduo.util.random;

import org.junit.Test;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2022/11/20 17:31
 */
public class RandomNumberTest {

    @Test
    public void TestRandom(){
        int[] ans=new int[105];

        for(int i=0;i<10000;i++){
            ans[RandomNumber.getRandomInt(0,100)]++;
        }
        for(int i=0;i<105;i++){
            System.out.println(i+" count = "+ans[i]);
        }
    }
}