package com.harry.mall.log4j;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.harry.mall.common.utils.JsonHelper;
import org.apache.logging.log4j.message.Message;

import java.util.HashMap;
import java.util.Map;

public class JsonMessage implements Message {

    private static final long serialVersionUID = 538439258494853324L;
    private String messageString;

    public JsonMessage() throws JsonProcessingException {
        this(null);
    }

    public JsonMessage(String messageString) throws JsonProcessingException {
        Map<String, String> msgObj = new HashMap<>();
        msgObj.put("message", messageString);
        parseMessageAsJson(msgObj);
    }

    public JsonMessage(Object msgObj) throws JsonProcessingException {
        parseMessageAsJson(msgObj);
    }

    private void parseMessageAsJson(Object msgObj) throws JsonProcessingException {
        messageString = JsonHelper.parse(msgObj);
    }

    @Override
    public String getFormattedMessage() {
        return messageString;
    }

    @Override
    public String getFormat() {
        return messageString;
    }

    @Override
    public Object[] getParameters() {
        return null;
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }
}
