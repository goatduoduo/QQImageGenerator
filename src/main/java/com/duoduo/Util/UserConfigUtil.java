package com.duoduo.Util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.duoduo.User.UserConfig;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2022/12/4 20:24
 */
public class UserConfigUtil {
    /**
     * 配置文件的路径
     */
    private static String CONFIG_FILE_PATH = System.getProperty("user.dir") + "\\";

    public static UserConfig userConfig = null;
    public static void readUserConfig(String userName)  {
        CONFIG_FILE_PATH += userName + ".json";
        try {
            InputStream is = new FileInputStream(CONFIG_FILE_PATH);
            int iAvail = is.available();
            byte[] bytes = new byte[iAvail];
            is.read(bytes);
            String text = new String(bytes);
            userConfig = JSON.parseObject(text, UserConfig.class);
            JSON.toJSONString(userConfig);
            is.close();

        } catch (FileNotFoundException e) {
            System.out.println("暂无" + CONFIG_FILE_PATH + "正在新建……");
            userConfig = new UserConfig(userName);
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

    public static void writeUserConfig(){
        File file = new File(CONFIG_FILE_PATH);
        OutputStream out = null;
        BufferedWriter bw = null;

        if(file.exists()){
            try {
                out = Files.newOutputStream(file.toPath());
                bw = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
                bw.write(JSON.toJSONString(userConfig, JSONWriter.Feature.PrettyFormat));
                bw.flush();
                bw.close();
            } catch (IOException e) {
                System.out.println(CONFIG_FILE_PATH);
                throw new RuntimeException(e);
            }

        }else{
            System.out.println(CONFIG_FILE_PATH + "不存在！");
        }
    }
}
