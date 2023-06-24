package com.duoduo.controller;

import com.alibaba.fastjson2.JSONObject;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @Author: goatduoduo
 * @Description: 原先的采矿游戏，已经被废弃
 * @Date: Created in 2023/3/12 19:11
 */
public class MainScene implements Initializable,InitData {
    public Label userNameLabel;
    public Label levelLabel;
    public Rectangle expBarDark;
    public Rectangle expBarLight;
    public Label expDetail;
    public VBox gameAreaVbox;
    public VBox globalMessageVbox;
    public VBox rankVbox;
    public FlowPane rareItemsFlowPane;

    /**
     * todo 初始化方法
     * @param location
     * The location used to resolve relative paths for the root object, or
     * <tt>null</tt> if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or <tt>null</tt> if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void initData(String json) {
        System.out.println("得到数据"+json);
    }
}
