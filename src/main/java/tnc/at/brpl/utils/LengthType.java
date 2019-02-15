package tnc.at.brpl.utils;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
public enum LengthType {

    FL("Fork Length"),
    SL("Standard Length"),
    TL("Total Length"),
    CL("Carcas Length"),
    ML("Mantel Length");

    private final String description;

    LengthType(String value) {
        this.description = value;
    }


    public String getDescription() {
        return description;
    }
}
