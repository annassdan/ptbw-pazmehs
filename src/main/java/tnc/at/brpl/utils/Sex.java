package tnc.at.brpl.utils;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
public enum Sex {

    j("Jantan"),

    b("Betina"),

    ui("Unidentified");

    private final String sex;

    Sex(String s){
        this.sex = s;
    }

    public String getSex() {
        return sex;
    }
}
