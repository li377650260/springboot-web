package com.tonpower.springbootweb.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 将long值转化为字符串
 */
public class LongToStringSerializer extends JsonSerializer<Long> {
 
    @Override
    public void serialize(Long value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        try {
            jsonGenerator.writeString(value == null ? StringUtils.EMPTY : String.valueOf(value));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}