package org.eagleinvsys.test.converters;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ConverterTestUtils {

    static Map<String, String> getRecordMap(Set<String> headers, int number) {
        Map<String, String> record = new LinkedHashMap<>();
        for (String header : headers) {
            record.put(header, String.format("%s record %s", header, number));
        }
        return record;
    }
}
