package com.duoduo.util;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2023/3/4 9:51
 */
public enum Operations {
    //财富增值，每天6%利息，但需要收取250的费用
    INCREMENT("Increment"),
    //掠夺，每人有机会掠夺他人一次8%+200的价值，财富越高越有可能成为目标
    LOOTING("Looting"),
    //挖矿，在矿脉中挖15个矿物，挖光矿物的人需要2000重置矿脉
    MINING("Mining"),
    //展示挖矿结果
    SHOW_MINE("ShowMine");

    private final String symbol;

    Operations(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol(){
        return symbol;
    }

    public static Operations fromString(String symbol){
        for(Operations op:values()) {
            if(op.getSymbol().equals(symbol)){
                return op;
            }
        }
        return null;
    }
}
