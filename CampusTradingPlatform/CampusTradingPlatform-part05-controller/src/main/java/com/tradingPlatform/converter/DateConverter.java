package com.tradingPlatform.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}
