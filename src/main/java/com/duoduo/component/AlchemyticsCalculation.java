package com.duoduo.component;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.duoduo.bean.alchemytics.AlchemyticsUser;
import com.duoduo.bean.ElementItem;
import com.duoduo.bean.alchemytics.AlchemyticsVO;
import com.duoduo.bean.alchemytics.MergeNode;
import com.duoduo.config.GlobalConfig;
import com.duoduo.config.UserConfig;
import com.duoduo.controller.SaveAsJpg;
import com.duoduo.util.ConfigUtil;
import com.duoduo.util.SendUtil;
import com.duoduo.util.random.RandomNumber;
import com.sun.javafx.application.PlatformImpl;
import javafx.application.Application;
import javafx.application.Platform;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2023/6/23 19:58
 */
public class AlchemyticsCalculation {
    private static GlobalConfig config = null;

    /**
     * 定义一个hashmap，储存所有元素，id从1开始，规定数组从0开始，检查时注意index
     * 616为默认配置文件的item数量
     */
    HashMap<Integer, ElementItem> elementItemHashMap = new HashMap<>(617);

    /**
     * 储存合成公式，合成时遍历数组
     */
    ArrayList<MergeNode> mergeNodes = new ArrayList<>(1400);

    /**
     * 语言相关
     */
    HashMap<String, String> languageMap = new HashMap<>(617);

    /**
     * 储存已解锁的元素id，有天生的防重复机制简直太棒了
     */
    Set<Integer> unlockedSet = new HashSet<>(617);

    List<AlchemyticsUser> alchemyticsUserList = new ArrayList<>();

    /**
     * 执行炼金游戏，该游戏没有任何提前要做的事情
     * 炼金游戏：
     * 每个人抽取一次已解锁的元素，每一个人都有结合其他人元素的机会
     * 合成新元素后增加100经验值，合成已经合成的元素获得20经验值，合成失败不增加
     * 合成成功后，合成者会得到新的元素，被合成者不会损失元素，并且其他人有机会可以选择合成者的新元素作为目标
     * 我很期望所有元素什么时候被解锁
     * 没有额外需要储存的个人用户信息
     * 需要储存解锁状态
     *
     * @param config
     */
    public void executeOperation(GlobalConfig config) {
        AlchemyticsCalculation.config = config;
        initAlchemist();
        randomPickElement();
        showResult();
    }

    /**
     * 炼金游戏初始化所必须的
     * 读取解锁状态
     * 每个人随机抽取已解锁的元素
     */
    private void initAlchemist() {
        //初始化物品
        InputStream inputStream = getClass().getResourceAsStream("/alchemytics/def/items.json");
        JSONObject jsonObject = JSON.parseObject(inputStream);
        for (String i : jsonObject.keySet()) {
            ElementItem elementItem = new ElementItem();
            JSONObject item = jsonObject.getJSONObject(i);
            elementItem.setId((Integer) item.get("id"));
            elementItem.setKey((String) item.get("key"));
            elementItem.setPrime((Boolean) item.get("prime"));
            elementItemHashMap.put(elementItem.getId(), elementItem);
        }
        //初始化合成公式
        InputStream merges = getClass().getResourceAsStream("/alchemytics/def/merges.json");
        JSONArray mergesJsonArray = JSON.parseArray(merges);
        for (Object i : mergesJsonArray.toArray()) {
            MergeNode mergeNode = new MergeNode();
            JSONObject item = (JSONObject) i;
            mergeNode.setA((Integer) item.get("a"));
            mergeNode.setB((Integer) item.get("b"));
            mergeNode.setMergeResult((Integer) item.get("result"));
            mergeNodes.add(mergeNode);
        }
        //初始化语言
        InputStream language = getClass().getResourceAsStream("/alchemytics/lang/zh_cn.json");
        JSONObject langJsonObject = JSON.parseObject(language);
        for (String i : langJsonObject.keySet()) {
            languageMap.put(i, (String) langJsonObject.get(i));
        }
        //读取配置中的可解锁元素，如果没有就重新开始
        if (config.getUnlockedElements() == null ||config.getUnlockedElements().size() == 0) {
            //重置整个list供工作
            config.setUnlockedElements(new ArrayList<>(elementItemHashMap.size()));
            for (int i = 0; i <= elementItemHashMap.size(); i++) {
                config.getUnlockedElements().add(false);
            }
            //重新定义可解锁的元素
            for (ElementItem item : elementItemHashMap.values()) {
                config.getUnlockedElements().set(item.getId() - 1, item.getPrime());
            }
        }
        //初始化已解锁的元素
        for (int i = 0; i < config.getUnlockedElements().size(); i++) {
            if (config.getUnlockedElements().get(i)) {
                unlockedSet.add(i + 1);
            }
        }
    }

    /**
     * 玩家执行随机抽取元素操作，并且随机与另一位玩家的元素结合
     */
    private void randomPickElement() {

        //取随机数需要转化成list进行
        List<Integer> unlockedArray = new ArrayList<>();
        for (Object i : unlockedSet.toArray()) {
            unlockedArray.add((Integer) i);
        }
        //初始化炼金玩家并抽取随机元素
        for (UserConfig user : config.getUserConfigs()) {
            AlchemyticsUser alchemyticsUser = new AlchemyticsUser();
            alchemyticsUser.setUserName(user.getName());
            alchemyticsUser.setExp(user.getExperience());
            Integer selectedElementId = unlockedArray.get(RandomNumber.getRandomInt(0, unlockedArray.size() - 1));
            alchemyticsUser.setCurrentElementId(selectedElementId);
            alchemyticsUserList.add(alchemyticsUser);
        }
        int size = alchemyticsUserList.size();
        //洗牌然后每个玩家有随机抽取的机会
        Collections.shuffle(alchemyticsUserList);
        for (int i = 0; i < size; i++) {
            int randInt = 0;
            int maxTry = 3;
            //尝试三次，连续三次匹配到自己真的太倒霉了
            do {
                randInt = RandomNumber.getRandomInt(0, size - 1);
                maxTry--;
            } while (randInt == i && maxTry > 0);

            //不允许选择自己，所以出现此情况会被跳过
            if (randInt != i) {
                AlchemyticsUser me = alchemyticsUserList.get(i);
                AlchemyticsUser target = alchemyticsUserList.get(randInt);
                me.setTargetUserIndex(randInt);
                if (target.getNewElementId() != null) {
                    me.setTargetElementId(target.getNewElementId());
                } else {
                    me.setTargetElementId(target.getCurrentElementId());
                }
                me.setNewElementId(getMergeResult(me.getCurrentElementId(), me.getTargetElementId()));
                calculateExpEarned(me);
                //alchemyticsUserList.set(i,me);
            }
        }
        System.out.println("AC");
    }

    /**
     * 生成VO，然后渲染图片并发送
     */
    private void showResult(){
        //多线程操作，当线程池用满时，阻塞而非拒绝
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 12, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), Thread::new, new ThreadPoolExecutor.CallerRunsPolicy());
        CountDownLatch countDownLatch = new CountDownLatch(config.getUserConfigs().size());
        final Object lock = new Object();

        //根据游戏结果，多线程生成VO
        String unlockedNum = "";
        StringBuilder unlockedDetail = new StringBuilder();
        StringBuilder globalMessage = new StringBuilder();
        int unlocked = 0;
        int itemIndex = 1;
        for(Boolean i: config.getUnlockedElements()){
            if(i){
                unlocked++;
                unlockedDetail.append(getNameFromId(itemIndex)).append(" ");
            }
            itemIndex++;
        }
        for(AlchemyticsUser alchemyticsUser: alchemyticsUserList){
            if(alchemyticsUser.getMessage().startsWith("合成新元素")){
                globalMessage.append(alchemyticsUser.getMessage()).append('\n');
            }
        }
        unlockedNum = "已解锁（" + unlocked + "/" + config.getUnlockedElements().size() + "）";
        //渲染VO，并且多线程生成图片
        //填入每个玩家的积分变化信息
        for(AlchemyticsUser alchemyticsUser: alchemyticsUserList){
            for(UserConfig userConfig: config.getUserConfigs()){
                if(alchemyticsUser.getUserName().equals(userConfig.getName())){
                    userConfig.setExperience(alchemyticsUser.getExp());
                    String finalUnlockedNum = unlockedNum;
                    executor.submit(() -> {
                        try{
                            AlchemyticsVO alchemyticsVO = new AlchemyticsVO();
                            alchemyticsVO.setGlobalMessage(globalMessage.toString());
                            alchemyticsVO.setUnlockedDetail(unlockedDetail.toString());
                            alchemyticsVO.setUnlockedNum(finalUnlockedNum);
                            alchemyticsVO.setMyName(alchemyticsUser.getUserName()+" "+alchemyticsUser.getExp());
                            alchemyticsVO.setTargetName(alchemyticsUserList.get(alchemyticsUser.getTargetUserIndex()).getUserName()+" "
                                    +alchemyticsUserList.get(alchemyticsUser.getTargetUserIndex()).getExp());
                            alchemyticsVO.setMyEleName(getNameFromId(alchemyticsUser.getCurrentElementId()));
                            alchemyticsVO.setMyEleImg(alchemyticsUser.getCurrentElementId().toString());
                            alchemyticsVO.setTargetEleName(getNameFromId(alchemyticsUser.getTargetElementId()));
                            alchemyticsVO.setTargetEleImg(alchemyticsUser.getTargetElementId().toString());
                            //说明已经合成新元素，new开头的也会有
                            if(alchemyticsUser.getNewElementId()!= null){
                                alchemyticsVO.setNewEleImg(alchemyticsUser.getNewElementId().toString());
                                alchemyticsVO.setNewEleMerge(getNameFromId(alchemyticsUser.getCurrentElementId())+" + "
                                +getNameFromId(alchemyticsUser.getTargetElementId())+" = "
                                +getNameFromId(alchemyticsUser.getNewElementId()));
                            }
                            alchemyticsVO.setMessage(alchemyticsUser.getMessage());

                            // 在应用程序初始化的某个地方调用下面的代码
                            PlatformImpl.startup(() -> {
                                // JavaFX工具包初始化完成后的回调方法
                                SaveAsJpg saveAsJpg = new SaveAsJpg();
                                try {
                                    saveAsJpg.saveImageByFxml("alchemystics",userConfig.getName(),JSON.toJSONString(alchemyticsVO));
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }

                            });

                            //发送qq，进程上锁
                            synchronized (lock) {
                                SendUtil.sendImageToQq(userConfig);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        } finally {
                            countDownLatch.countDown();
                        }
                    });
                    break;
                }
            }
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.exit();
        System.out.println("多线程渲染已完成");
        executor.shutdown();
    }

    /**
     * 获得合成结果
     *
     * @param a
     * @param b
     * @return 合成的新元素或者null表示合成失败
     */
    private Integer getMergeResult(Integer a, Integer b) {
        for (MergeNode node : mergeNodes) {
            //左右的顺序并不重要，因为都可以合成
            if (node.getA() == a && node.getB() == b) {
                return node.getMergeResult();
            }
            if (node.getA() == b && node.getB() == a) {
                return node.getMergeResult();
            }
        }
        return null;
    }

    /**
     * 根据元素Id得到元素的名字
     *
     * @param id 元素Id
     * @return 译名
     */
    private String getNameFromId(Integer id) {
        ElementItem item = elementItemHashMap.get(id);
        return languageMap.get(item.getKey());
    }

    /**
     * 计算合成后获得的exp
     *
     * @param user
     */
    private void calculateExpEarned(AlchemyticsUser user) {

        //合成失败自然跳过
        if (user.getNewElementId() == null) {
            user.setMessage("合成失败：" + user.getUserName() + "合成元素失败了，" + getNameFromId(user.getCurrentElementId()) +
                    " + " + getNameFromId(user.getTargetElementId()) + " 并不能合成任何元素");
            return;
        }
        //该元素之前已经合成过了，得分+20
        if (config.getUnlockedElements().get(user.getNewElementId() - 1)) {
            user.setExp(user.getExp() + 20);
            user.setMessage("合成成功(+20)：" + user.getUserName() + "合成了已有的元素“" + getNameFromId(user.getNewElementId()) + "”，"
                    + getNameFromId(user.getCurrentElementId()) +
                    " + " + getNameFromId(user.getTargetElementId()) + " = " + getNameFromId(user.getNewElementId()));
        } else {
            //合成了新的元素，得分+100
            user.setExp(user.getExp() + 100);
            config.getUnlockedElements().set(user.getNewElementId() - 1, true);
            user.setMessage("合成新元素(+100)：恭喜！" + user.getUserName() + "合成了全新的元素“" + getNameFromId(user.getNewElementId()) + "”，"
                    + getNameFromId(user.getCurrentElementId()) +
                    " + " + getNameFromId(user.getTargetElementId()) + " = " + getNameFromId(user.getNewElementId()));
        }
    }

    public static void main(String[] args){
        AlchemyticsCalculation alchemyticsCalculation = new AlchemyticsCalculation();
        alchemyticsCalculation.executeOperation(ConfigUtil.readUserConfig("mine", "globe", GlobalConfig.class));

    }

}
