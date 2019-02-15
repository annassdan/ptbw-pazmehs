package tnc.at.brpl.configurations;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import tnc.at.brpl.utils.Brpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
public class CustomDateSerializer extends JsonSerializer<Date> {


    public static String toPattern(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(Brpl.DATE_PATTERN);
        return formatter.format(date);
    }


    @Override
    public void serialize(Date o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        SimpleDateFormat formatter = new SimpleDateFormat(Brpl.DATE_PATTERN);
        String formattedDate = formatter.format(o);
        jsonGenerator.writeString(formattedDate);
    }


}
