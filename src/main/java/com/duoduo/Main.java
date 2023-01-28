package com.duoduo;

import com.duoduo.component.MiningIncCalculation;
import com.duoduo.component.RandomColorComponent;
import com.duoduo.component.TenRoundsComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: ${USER}
 * @Description:
 * @Date: Created in ${DATE} ${TIME}
 */
public class Main {
    /**
     * 一个支持类程序，通过命令行来直接生成图片到剪切板
     * java -jar QQImageGenerator.jar rc 兔兔
     * @param args
     */
    public static void main(String[] args) {
        //测试环境 ?:\...\QQImageGenerator
        //生产环境 ?:\...\QQImageGenerator\out\artifacts\QQImageGenerator_jar
        //指令为两个参数，1是随机类型 2是用户名字
        List<String> users = new ArrayList<>(Arrays.asList(args).subList(1, args.length));
        if(args.length >= 2 ){
            switch (args[0]){
                case "rc":
                    new RandomColorComponent(args[1]).render();
                    break;
                case "tr":
                    new TenRoundsComponent(args[1]). render();
                    break;
                case "lootMine":
                    MiningIncCalculation.lootGameOn(users);
                    break;
                case "mineOn":
                    MiningIncCalculation.mineGameOn(users);
                    break;
                default:break;
            }
        }else{
            //生成默认图片
            new RandomColorComponent("测试").render();
        }

    }
}