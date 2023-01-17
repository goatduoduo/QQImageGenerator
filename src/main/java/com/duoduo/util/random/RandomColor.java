package com.duoduo.util.random;

import com.duoduo.bean.ColorBean;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2022/11/13 20:18
 */
public class RandomColor {
    public static List<ColorBean> COLORS = new ArrayList<>(20);

    public static ColorBean randomColor(){
        int totalWeight = 0;
        for(ColorBean e:COLORS){
            totalWeight+=e.getWeight();
        }
        int rand = RandomNumber.getRandomInt(1,totalWeight);
        for(ColorBean e:COLORS){
            rand -= e.getWeight();
            if(rand<=0){
                return e;
            }
        }
        return COLORS.get(0);
    }
    public void test(){
        URL url=this.getClass().getResource("/data/colors.csv");
        System.out.println(url);
    }
}

