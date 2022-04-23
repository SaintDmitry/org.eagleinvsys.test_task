package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.ConvertibleCollection;
import org.eagleinvsys.test.converters.ConvertibleMessage;

import java.util.*;

public class ConvertibleCollectionImpl implements ConvertibleCollection {

    private Set<String> headers;

    private List<ConvertibleMessage> records;

    public ConvertibleCollectionImpl(Set<String> headers, List<ConvertibleMessage> records) {
        this.headers = headers;
        this.records = records;
    }

    @Override
    public Collection<String> getHeaders() {
        return headers;
    }

    @Override
    public Iterable<ConvertibleMessage> getRecords() {
        return records;
    }

    public static ConvertibleCollection of(List<Map<String, String>> collectionToConvert) {

        if (!isCorrectFormat(collectionToConvert)) {
            throw new IllegalArgumentException("Incorrect format of collection to convert");
        }

        List<ConvertibleMessage> records = new ArrayList<>();
        Set<String> headers = new LinkedHashSet<>(collectionToConvert.get(0).keySet());
        for (Map<String, String> recordsMap : collectionToConvert) {
            records.add(new ConvertibleMessageImpl(recordsMap));
        }
        return new ConvertibleCollectionImpl(headers, records);
    }

    private static boolean isCorrectFormat(List<Map<String, String>> collectionToConvert) {
        if (!collectionToConvert.isEmpty() && !collectionToConvert.get(0).isEmpty()) {
            Set<String> headers = collectionToConvert.get(0).keySet();
            for (Map<String, String> record : collectionToConvert) {
                if (!Objects.equals(headers, record.keySet())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
