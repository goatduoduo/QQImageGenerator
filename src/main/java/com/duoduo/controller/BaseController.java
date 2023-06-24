package com.duoduo.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.Map;


/**
 * @author duoduo
 * 所有从属控件需要继承这个，而直接调用的程序不要继承。
 */
public class BaseController {

    private Application application;

    private Window window;

    /**
     * 传值的关键
     */
    private Map<String, Object> data;

    private Parent root;

    public static <T> T load(Class<T> targetClass) {
        return load(targetClass, null);
    }

    public static <T> T load(Class<T> targetClass, Map<String, Object> extraData) {
        if (targetClass == null) {
            return null;
        }
        //类加载器，将class（字节码）文件加载到jvm虚拟姬
        ClassLoader classLoader = targetClass.getClassLoader();
        String className = targetClass.getName();
        if (className == null || "".equals(className.trim())) {
            return null;
        }
        T targetController = null;

        String fxmlPath = "../../../fxml/" + className + ".fxml";
        //嗯，url也可以用来找到文件呢
        URL fxmlUrl = classLoader.getResource(fxmlPath);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(fxmlUrl);
        fxmlLoader.setClassLoader(classLoader);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        targetController = fxmlLoader.getController();
        //开始注射数据
        if (targetController instanceof BaseController) {
            BaseController baseController = (BaseController) targetController;
            //获取父容器
            baseController.root = fxmlLoader.getRoot();
            //好吧，又来了，我看透你了
            //将影响UI的部分放入runLater方法，以便他与UI其余部分在线程运行
            Platform.runLater(() -> {
                //注射数据，在子类重写这个方法
                baseController.onInitializeWithData(extraData);
                baseController.initData();
                baseController.initView();
            });
        }
        return targetController;
    }


    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }
//--------------controller标准化的方法-----------------//

    /**
     * 给controller传递数据
     *
     * @param extraData
     */
    protected void onInitializeWithData(Map<String, Object> extraData) {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 初始化页面
     */
    protected void initView() {
    }

    /**
     * 收集页面信息
     */
    public void collectData() {
    }

}
