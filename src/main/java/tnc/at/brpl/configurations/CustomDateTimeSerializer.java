package tnc.at.brpl.configurations;

import com.fasterxml.jackson.databind.JsonSerializer;
import tnc.at.brpl.utils.Brpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@SuppressWarnings("unused")
public class CustomDateTimeSerializer extends JsonSerializer<Date> {


    @Override
    public void serialize(Date date, com.fasterxml.jackson.core.JsonGenerator jsonGenerator, com.fasterxml.jackson.databind.SerializerProvider serializerProvider) throws IOException, com.fasterxml.jackson.core.JsonProcessingException {
        SimpleDateFormat formatter = new SimpleDateFormat(Brpl.DATE_TIME_PATTERN);
        String formattedDate = formatter.format(date);
        jsonGenerator.writeString(formattedDate);

    }
}
