package com.duoduo.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;

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

    /**
     * 打开json文件并读取到对象中
     *
     * @param fileName 文件名字，自动加上 .json
     * @param classz   类名，例如UserConfig.class
     * @param <T>      泛型
     * @return 读取的对象
     */
    public static <T> T readUserConfig(String fileName, Class<T> classz) {
        String filePath = CONFIG_FILE_PATH + fileName + ".json";
        T ans = null;
        try {
            InputStream is = new FileInputStream(filePath);
            int iAvail = is.available();
            byte[] bytes = new byte[iAvail];
            //noinspection ResultOfMethodCallIgnored
            is.read(bytes);
            String text = new String(bytes);
            ans = JSON.parseObject(text, classz);
            is.close();

        } catch (FileNotFoundException e) {
            System.out.println("暂无" + filePath + "正在新建……");
            File file = new File(filePath);
            try {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ans;
    }

    /**
     * 将对象写入到json文件中
     *
     * @param fileName 文件名字，自动加上 .json
     * @param content  类名，例如UserConfig.class
     * @param <T>      泛型
     */
    public static <T> void writeConfig(String fileName, T content) {
        File file = new File(CONFIG_FILE_PATH + fileName + ".json");
        OutputStream out = null;
        BufferedWriter bw = null;

        if (file.exists()) {
            try {
                out = Files.newOutputStream(file.toPath());
                bw = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
                bw.write(JSON.toJSONString(content, JSONWriter.Feature.PrettyFormat));
                bw.flush();
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            System.out.println(CONFIG_FILE_PATH + fileName + ".json" + "不存在！");
        }
    }

    public static <T> T readUserConfig(String fileName, String directory, Class<T> classz) {
        File folder =  new File(CONFIG_FILE_PATH+"\\" + directory) ;
        folder.mkdir();
        return readUserConfig(directory + "\\" + fileName, classz);
    }

    public static <T> void writeConfig(String fileName, String directory, T content) {
        File folder =  new File(CONFIG_FILE_PATH+"\\" + directory) ;
        folder.mkdir();
        writeConfig(directory + "\\" + fileName, content);
    }
}
