package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.ConvertibleMessage;

import java.util.Map;

public class ConvertibleMessageImpl implements ConvertibleMessage {

    private Map<String, String> recordsMap;

    public ConvertibleMessageImpl(Map<String, String> recordsMap) {
        this.recordsMap = recordsMap;
    }

    @Override
    public String getElement(String elementId) {
        return recordsMap.get(elementId);
    }

}
