package com.duoduo;

import com.duoduo.Component.MiningIncCalculation;
import com.duoduo.Component.RandomColorComponent;
import com.duoduo.Component.TenRoundsComponent;
import com.duoduo.RandomUtil.InitRandom;
import com.duoduo.RandomUtil.RandomColor;
import com.duoduo.User.UserConfig;
import com.duoduo.Util.Images;
import com.duoduo.Util.UserConfigUtil;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
        //初始化操作
        InitRandom initRandom = new InitRandom();
        initRandom.initCsvs();
        //测试环境 C:\CodeArea\QQImageGenerator
        //生产环境 C:\CodeArea\QQImageGenerator\out\artifacts\QQImageGenerator_jar
        //指令为两个参数，1是随机类型 2是用户名字
        if(args.length >= 2 ){
            switch (args[0]){
                case "rc":
                    new RandomColorComponent(args[1]).render();
                    UserConfigUtil.writeUserConfig();
                    break;
                case "tr":
                    UserConfigUtil.readUserConfig(args[1]);
                    new TenRoundsComponent(). render();
                    UserConfigUtil.writeUserConfig();
                    break;
                case "lootMine":
                    List<String> users = new ArrayList<>();
                    for(int i=1;i<args.length;i++){
                        users.add(args[i]);
                    }
                    MiningIncCalculation.lootGameOn(users);
                default:break;
            }
        }else{
            //生成默认图片
            new RandomColorComponent("测试").render();
        }

    }
}