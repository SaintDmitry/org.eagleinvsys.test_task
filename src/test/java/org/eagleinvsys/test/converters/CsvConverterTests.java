package org.eagleinvsys.test.converters;

import org.apache.commons.lang3.StringUtils;
import org.eagleinvsys.test.converters.impl.ConvertibleCollectionImpl;
import org.eagleinvsys.test.converters.impl.ConvertibleMessageImpl;
import org.eagleinvsys.test.converters.impl.CsvConverter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.eagleinvsys.test.converters.ConverterTestUtils.getRecordMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvConverterTests {

    private final CsvConverter csvConverter = new CsvConverter();

    @Test
    void convert_test() {
        Set<String> headers = new LinkedHashSet<>();
        headers.add("1 header");
        headers.add("2 header");
        headers.add("3 header");
        headers.add("4 header");
        List<ConvertibleMessage> records = getRecords(headers, 3);

        ConvertibleCollection convertibleCollection = new ConvertibleCollectionImpl(headers, records);
        OutputStream outputStream = new ByteArrayOutputStream();

        csvConverter.convert(convertibleCollection, outputStream);

        String result = outputStream.toString();

        assertTrue(result.contains("1 header;2 header;3 header;4 header;"));
        assertTrue(result.contains("1 header record 1;2 header record 1;3 header record 1;4 header record 1;"));
        assertTrue(result.contains("1 header record 2;2 header record 2;3 header record 2;4 header record 2;"));
        assertTrue(result.contains("1 header record 3;2 header record 3;3 header record 3;4 header record 3;"));
        assertEquals(3, StringUtils.countMatches(result, "\n"));
        assertEquals(0, StringUtils.countMatches(result, "\r"));
    }

    private List<ConvertibleMessage> getRecords(Set<String> headers, int recordsCount) {
        List<ConvertibleMessage> records = new ArrayList<>();
        for (int i = 1; i <= recordsCount; i++) {
            records.add(new ConvertibleMessageImpl(getRecordMap(headers, i)));
        }
        return records;
    }

}