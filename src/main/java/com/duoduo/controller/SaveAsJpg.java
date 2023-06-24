package com.duoduo.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.duoduo.util.ConfigUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2023/3/11 20:57
 */
public class SaveAsJpg extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        saveImageByFxml("mainScene","duoduo","{\n" +
                "\t\"Messages\":[\n" +
                "\t\t\"2023-03-05: 帕特里克真是个倒霉鬼，花费了2000经验值重置矿脉\",\n" +
                "\t\t\"2023-03-05: 艾米莉真的幸运极了，抽中了稀有物品——九转大肠\"\n" +
                "\t]}");

        saveImageByFxml("alchemystics","test2","23");

        // 关闭JavaFX应用程序
        Platform.exit();
    }


    /**
     * 根据数据渲染fxml并保存到本地为图片，保存地址为：/users/{username}.jpg
     * @param fxmlName fxml的文文件名字
     * @param userName 用户名，决定储存的图片名字
     * @param data 一般为JSON，继承了InitData的controller会根据自己的策略读取数据
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void saveImageByFxml(String fxmlName,String userName, String data) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //加载fxml
        String fxmlPath = "fxml/" + fxmlName + ".fxml";
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL fxmlUrl = classLoader.getResource(fxmlPath);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlUrl);
        loader.setClassLoader(classLoader);
        loader.load();

        //通过反射调用initData方法
        Object controller = loader.getController();
        Method method = controller.getClass().getDeclaredMethod("initData", String.class);
        method.invoke(controller,data);

        //将controller保存为图片
        Scene scene = new Scene(loader.getRoot());
        WritableImage image = scene.snapshot(null);
        BufferedImage fxImage = SwingFXUtils.fromFXImage(image, null);
        Graphics2D g2d = fxImage.createGraphics();
        g2d.drawImage(fxImage, null, null);
        g2d.dispose();
        File file = new File(ConfigUtil.CONFIG_FILE_PATH + "\\users\\" + userName + ".jpg");
        try {
            ImageIO.write(fxImage, "png", file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
