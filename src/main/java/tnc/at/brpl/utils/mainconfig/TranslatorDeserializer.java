package tnc.at.brpl.utils.mainconfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import java.io.IOException;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@SuppressWarnings("unused")
public class TranslatorDeserializer<R> extends KeyDeserializer {


    @Override
    public R deserializeKey(String s, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        return null;
    }
}
