package com.duoduo.Util;

import com.alibaba.fastjson2.JSON;
import com.duoduo.User.IncConfig;
import com.duoduo.User.UserConfig;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2023/1/15 20:58
 */
public class IncConfigUtil {

    /**
     * 配置文件的路径
     */
    private static String CONFIG_FILE_PATH = System.getProperty("user.dir") + "\\";

    public static List<IncConfig> incs = null;

    public static void readIncsConfig()  {
        CONFIG_FILE_PATH +=  "Incs.json";
        try {
            InputStream is = new FileInputStream(CONFIG_FILE_PATH);
            int iAvail = is.available();
            byte[] bytes = new byte[iAvail];
            is.read(bytes);
            String text = new String(bytes);
            incs = JSON.parseArray(text, IncConfig.class);
            JSON.toJSONString(incs);
            is.close();

        } catch (FileNotFoundException e) {
            System.out.println("暂无" + CONFIG_FILE_PATH + "正在新建……");
            incs = new ArrayList<>();
            File file = new File(CONFIG_FILE_PATH);
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeIncsConfig(){
        File file = new File(CONFIG_FILE_PATH);
        OutputStream out = null;
        BufferedWriter bw = null;

        if(file.exists()){
            try {
                out = Files.newOutputStream(file.toPath());
                bw = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
                bw.write(JSON.toJSONString(incs));
                bw.flush();
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            System.out.println(CONFIG_FILE_PATH + "不存在！");
        }
    }
}
