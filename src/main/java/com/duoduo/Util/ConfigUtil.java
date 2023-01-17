package com.duoduo.Util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.duoduo.User.UserConfig;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2023/1/15 22:01
 */
public class ConfigUtil {

    /**
     * 配置文件的路径
     */
    private static String CONFIG_FILE_PATH = System.getProperty("user.dir") + "\\";

    public static <T> T readUserConfig(String fileName, Class<T> classz)  {
        String file_path =  CONFIG_FILE_PATH + fileName + ".json";
        T ans = null;
        try {
            InputStream is = new FileInputStream(file_path);
            int iAvail = is.available();
            byte[] bytes = new byte[iAvail];
            is.read(bytes);
            String text = new String(bytes);
            ans = JSON.parseObject(text, classz);
            is.close();

        } catch (FileNotFoundException e) {
            System.out.println("暂无" + file_path + "正在新建……");
            File file = new File(file_path);
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ans;
    }

    public static <T> void writeConfig(String fileName,T content){
        File file = new File(CONFIG_FILE_PATH + fileName + ".json");
        OutputStream out = null;
        BufferedWriter bw = null;

        if(file.exists()){
            try {
                out = Files.newOutputStream(file.toPath());
                bw = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
                bw.write(JSON.toJSONString(content, JSONWriter.Feature.PrettyFormat));
                bw.flush();
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }else{
            System.out.println(CONFIG_FILE_PATH + fileName + ".json" + "不存在！");
        }
    }
}
