package com.duoduo.component;

import com.duoduo.bean.LevelBean;
import com.duoduo.bean.MinimalUser;
import com.duoduo.config.GlobalConfig;
import com.duoduo.util.Operations;
import com.duoduo.util.SendUtil;
import com.duoduo.util.random.RandomNumber;
import com.duoduo.config.UserConfig;
import com.duoduo.util.ConfigUtil;
import com.duoduo.util.random.RandomPercent;
import com.duoduo.util.random.RandomUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @Author: goatduoduo
 * @Description: 所有人分为两大职业：老板和矿工，老板会开设公司，而员工会负责挖矿。
 * 挖到的矿物会被公司转化为价值直接回馈给老板，但需要付给员工工资，而工资的组成取决于不同的公司，主要为基本工资+提成。
 * 员工的收入来源只有老板发的工资
 * 每个人需要付出200的日供，如果欠款则会收到政府低保以保持生存。
 * 后期会加入随机事件
 * @Date: Created in 2023/1/15 20:05
 */
public class MiningIncCalculation {

    private static GlobalConfig config = null;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 执行一系列操作的方法
     *
     * @param config
     */
    public static void executeOperation(GlobalConfig config) {
        if (config.getOperationList().size() == 0) {
            System.out.println("你是不是忘记配置操作序列了？");
        }
        for (String e : config.getOperationList()) {
            long start = System.currentTimeMillis();
            switch (Objects.requireNonNull(Operations.fromString(e))) {
                case MINING:
                    mineGameOn(config);
                    break;
                case LOOTING:
                    lootGameOn(config);
                    break;
                case INCREMENT:
                    expIncrease(config);
                    break;
                case SHOW_MINE:
                    showMine(config);
                    break;
                default:
                    System.out.println(236);
                    break;
            }
            long end = System.currentTimeMillis();
            System.out.println("该操作" + e + "执行时间：" + (end - start) + "ms");
        }
    }


    /**
     * 十连抽的玩法补充，每个人在挖矿前随机找1名受害者掠夺经验值
     * 经验值越高的玩家越有可能成为目标
     */
    public static void lootGameOn(GlobalConfig config) {
        final float lootPercent = 0.08f;
        final int lootBase = 200;

        int size = config.getUserConfigs().size();
        for (int i = 0; i < size; i++) {
            int randInt = 0;
            int maxTry = 3;
            //尝试三次，连续三次匹配到自己真的太倒霉了
            do {
                randInt = RandomNumber.getRandomInt(0, size - 1);
                maxTry--;
            } while (randInt == i && maxTry > 0);

            //不允许掠夺自己，所以出现此情况会被跳过
            if (randInt != i) {
                UserConfig looter = config.getUserConfigs().get(i);
                UserConfig victim = config.getUserConfigs().get(randInt);
                int looted = (int) (victim.getExperience() * lootPercent) + lootBase;
                //掠夺游戏开始
                looter.setExperience(looter.getExperience() + looted);
                if (looter.getMessages() == null) {
                    looter.setMessages(new ArrayList<>());
                }
                looter.getMessages().add(sdf.format(new Date()) + ": " + "你掠夺了" + victim.getName() + " " + looted + " 经验值;");
                victim.setExperience(victim.getExperience() - looted);
                if (victim.getMessages() == null) {
                    victim.setMessages(new ArrayList<>());
                }
                victim.getMessages().add(sdf.format(new Date()) + ": " + looter.getName() + "掠夺了你 " + looted + " 经验值;");

                //System.out.println(looter.getName() + "掠夺了" + victim.getName() + looted + "经验值;");
            }
        }
        System.out.println("掠夺游戏结束");
    }

    /**
     * 执行采矿算法
     *
     * @param config
     */
    public static void mineGameOn(GlobalConfig config) {
        //一名玩家的挖矿数量
        int rounds = 15;
        //重置矿脉的费用
        int resetCost = 2000;
        //洗牌算法打乱玩家顺序
        for (int i = 0; i < config.getUserConfigs().size(); i++) {
            int userA = RandomNumber.getRandomInt(0, config.getUserConfigs().size() - 1);
            int userB = RandomNumber.getRandomInt(0, config.getUserConfigs().size() - 1);
            if (userA != userB) {
                UserConfig temp = config.getUserConfigs().get(userB);
                config.getUserConfigs().set(userB, config.getUserConfigs().get(userA));
                config.getUserConfigs().set(userA, temp);
            }
        }

        config.initMine();
        //全局信息每一次运行都会被重置，因为之后，每个玩家在生成图片时都会把这些信息插入其中
        config.setGlobalMessages(new ArrayList<>());

        for (UserConfig user : config.getUserConfigs()) {
            //开始挖矿
            user.getNormalMineIndexList().clear();
            user.getRareMineIndexList().clear();
            for (int j = 0; j < rounds; j++) {
                //1/300抽命中稀有物品
                if (RandomPercent.randomTrueDenominator(300)) {
                    config.getGlobalMessages().add(sdf.format(new Date()) + ": " + user.getName() + "真的幸运极了，抽中了" + "" + "稀有物品——九转大肠");
                    user.getRareMineIndexList().add("九转大肠");
                    System.out.println(user.getName() + "真的幸运极了，抽中了" + "" + "稀有物品");
                }
                //平平无奇的抽奖流程
                int totalWeight = 0;
                for (int i = 0; i < config.getItemsLeft().size(); i++) {
                    totalWeight += config.getItemsLeft().get(i);
                }
                if (totalWeight == 0) {
                    break;
                }
                //加权随机算法
                int rand = RandomNumber.getRandomInt(1, totalWeight);
                for (int i = 0; i < config.getItemsLeft().size(); i++) {
                    if (config.getItemsLeft().get(i) == 0) {
                        continue;
                    }
                    rand -= config.getItemsLeft().get(i);
                    if (rand <= 0) {
                        user.getNormalMineIndexList().add(i);
                        //此时抽中后将会从奖池里拿出，这是最大的不同点
                        config.getItemsLeft().set(i, config.getItemsLeft().get(i) - 1);
                        break;
                    }
                }
            }
            //将用户采集到的矿物从高到低排序
            user.getNormalMineIndexList().sort((o1, o2) -> {
                if (o1 > o2) {
                    return -1;
                } else if (o1 < o2) {
                    return 1;
                }
                return 0;
            });
            //todo 计算挖矿收益
            //真是倒霉鬼，最后一个挖完矿物的要去重置了
            if (config.initMine()) {
                user.setExperience(user.getExperience() - resetCost);
                config.getGlobalMessages().add(sdf.format(new Date()) + ": " + user.getName() + "真是个倒霉鬼，花费了" + resetCost + "经验值重置矿脉");
                System.out.println(user.getName() + "真是个倒霉鬼，花费了" + resetCost + "经验值重置矿脉");
            }
        }
        System.out.println("采矿游戏结束");
    }

    /**
     * 经验值银行，会倍增1.06倍，但需要支付250元保管费……
     *
     * @param config
     */
    public static void expIncrease(GlobalConfig config) {
        for (UserConfig user : config.getUserConfigs()) {
            int afterIncreasingExp = (int) (user.getExperience() * 1.06f) - 250;
            user.getMessages().add(sdf.format(new Date()) + ": " + user.getName() + "在经验银行获得的利息是" +
                    (int) (user.getExperience() * 0.06f) + "，并扣除保管费250");
            user.setExperience(afterIncreasingExp);
        }
        System.out.println("财富增值结束");
    }

    /**
     * 渲染挖矿后的结果……
     * 生成图片并发送到qq
     *
     * @param config
     */
    public static void showMine(GlobalConfig config) {
        //多线程操作，当线程池用满时，阻塞而非拒绝
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 12, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), Thread::new, new ThreadPoolExecutor.CallerRunsPolicy());
        CountDownLatch countDownLatch = new CountDownLatch(config.getUserConfigs().size());
        final Object lock = new Object();

        //执行此操作以初始化等级，请勿在多线程内执行此方法
        new LevelBean();

        //先排行后多线程渲染图片再发送给qq
        RandomUser.sortUserByMostExp(config.getUserConfigs());
        config.getUserRank().clear();
        for (UserConfig e : config.getUserConfigs()) {
            config.getUserRank().add(new MinimalUser(e.getName(), e.getExperience()));
        }
        RandomUser.sortUserByDefault(config.getUserConfigs());
        System.out.println("开始多线程渲染");

        //多线程渲染操作
        for (UserConfig userConfig : config.getUserConfigs()) {
            executor.submit(() -> {
                try {
                    //首先渲染图片
                    TenRoundsComponent component = new TenRoundsComponent(userConfig, config);
                    component.saveImage(component.render(), ConfigUtil.CONFIG_FILE_PATH + "\\users\\" + userConfig.getName() + ".jpg");
                    //生成图片之后发送qq，但仅允许1个线程使用这个
                    synchronized (lock) {
                        // 需要同步的代码段
                        SendUtil.sendImageToQq(userConfig);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("多线程渲染已完成");
        executor.shutdown();
        //单线程发送qq
//        for (UserConfig e : config.getUserConfigs()) {
//            SendUtil.sendImageToQq(e);
//        }
    }
}
