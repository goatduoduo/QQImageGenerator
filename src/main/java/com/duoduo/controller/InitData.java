package com.duoduo.controller;

import com.alibaba.fastjson2.JSONObject;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2023/6/23 17:59
 */
public interface InitData {
    /**
     * 该方法为Controller导入数据，输入对象为json对象
     * @param json
     */
    void initData(String json);
}
