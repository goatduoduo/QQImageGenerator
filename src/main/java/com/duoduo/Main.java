package com.duoduo;

import com.duoduo.component.AlchemyticsCalculation;
import com.duoduo.config.GlobalConfig;
import com.duoduo.util.ConfigUtil;

/**
 * @Author: ${USER}
 * @Description:
 * @Date: Created in ${DATE} ${TIME}
 * 该项目通过配置文件进行操作，需要事先设置参数运行
 * 其实是一个操作序列，每行执行一个方法
 */
public class Main {
    /**
     * 一个支持类程序，通过命令行来直接生成图片到剪切板
     * java -jar QQImageGenerator.jar rc 兔兔
     *
     * @param args
     */
    public static void main(String[] args) {
        //测试环境 ?:\...\QQImageGenerator
        //生产环境 ?:\...\QQImageGenerator\out\artifacts\QQImageGenerator_jar
        //指令为两个参数，1是随机类型 2是用户名字

        GlobalConfig globalConfig = ConfigUtil.readUserConfig("mine", "globe", GlobalConfig.class);
        if (globalConfig == null) {
            globalConfig = new GlobalConfig();
        }
        if(args.length == 0){
            AlchemyticsCalculation alchemyticsCalculation = new AlchemyticsCalculation();
            alchemyticsCalculation.executeOperation(globalConfig);
            //MiningIncCalculation.executeOperation(globalConfig);
        }else{
            switch (args[0]) {
                case "addUser":
                    ConfigEditor.addUser(globalConfig, args[1], args[2], true);
                    break;
                case "deleteUser":
                    ConfigEditor.removeUser(globalConfig, args[1]);
                    break;
                case "clearOperation":
                    ConfigEditor.clearOperation(globalConfig);
                    break;
                case "addOperation":
                    ConfigEditor.addOperation(globalConfig, args[1]);
                    break;
                default:
                    break;
            }
        }
        ConfigUtil.writeConfig("mine", "globe", globalConfig);
    }
}