package tnc.at.brpl.utils;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
public enum UnpredictableBoolean {

    na("n/a"),
    t("t"),
    y("y");


    private final String booleanValue;

    UnpredictableBoolean(String s){
        this.booleanValue = s;
    }

    public String getBooleanValue() {
        return booleanValue;
    }
}
