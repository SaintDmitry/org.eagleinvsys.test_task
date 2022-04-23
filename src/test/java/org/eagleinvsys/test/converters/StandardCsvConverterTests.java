package org.eagleinvsys.test.converters;

import org.apache.commons.lang3.StringUtils;
import org.eagleinvsys.test.converters.impl.CsvConverter;
import org.eagleinvsys.test.converters.impl.StandardCsvConverter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.*;

import static org.eagleinvsys.test.converters.ConverterTestUtils.getRecordMap;
import static org.junit.jupiter.api.Assertions.*;

class StandardCsvConverterTests {

    @Test
    void convert_OK_test() {
        StandardCsvConverter standardCsvConverter = new StandardCsvConverter(new CsvConverter());
        OutputStream outputStream = new ByteArrayOutputStream();

        standardCsvConverter.convert(getCollectionToConvert(false), outputStream);

        String result = outputStream.toString();

        assertTrue(result.contains("1 header;2 header;3 header;4 header;"));
        assertTrue(result.contains("1 header record 1;2 header record 1;3 header record 1;4 header record 1;"));
        assertTrue(result.contains("1 header record 2;2 header record 2;3 header record 2;4 header record 2;"));
        assertTrue(result.contains("1 header record 3;2 header record 3;3 header record 3;4 header record 3;"));
        assertEquals(3, StringUtils.countMatches(result, "\n"));
        assertEquals(0, StringUtils.countMatches(result, "\r"));
    }

    @Test
    void convert_IncorrectFormat_test() {
        StandardCsvConverter standardCsvConverter = new StandardCsvConverter(new CsvConverter());
        OutputStream outputStream = new ByteArrayOutputStream();

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                standardCsvConverter.convert(getCollectionToConvert(true), outputStream));

        assertEquals("Incorrect format of collection to convert", exception.getMessage());
    }

    private List<Map<String, String>> getCollectionToConvert(Boolean makeBad) {
        Set<String> headers = new LinkedHashSet<>();
        headers.add("1 header");
        headers.add("2 header");
        headers.add("3 header");
        headers.add("4 header");

        List<Map<String, String>> collectionToConvert = new ArrayList<>();

        for (int i = 1; i <= 3; i++){
            if (makeBad) {
                headers.add(String.valueOf(i));
            }
            collectionToConvert.add(getRecordMap(headers, i));
        }
        return collectionToConvert;
    }
}