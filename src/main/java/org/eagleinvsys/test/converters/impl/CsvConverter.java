package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.Converter;
import org.eagleinvsys.test.converters.ConvertibleCollection;
import org.eagleinvsys.test.converters.ConvertibleMessage;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class CsvConverter implements Converter {

    private final byte[] DELIMITER = ";".getBytes();
    private final byte[] LINE_BREAK = "\n".getBytes();

    /**
     * Converts given {@link ConvertibleCollection} to CSV and outputs result as a text to the provided {@link OutputStream}
     *
     * @param collectionToConvert collection to convert to CSV format
     * @param outputStream        output stream to write CSV conversion result as text to
     */
    @Override
    public void convert(ConvertibleCollection collectionToConvert, OutputStream outputStream) {

        BufferedOutputStream bos = new BufferedOutputStream(outputStream);

        try {
            for (String header : collectionToConvert.getHeaders()) {
                bos.write(escapeLineBreaks(header).getBytes());
                bos.write(DELIMITER);
            }

            for (ConvertibleMessage msg : collectionToConvert.getRecords()) {
                bos.write(LINE_BREAK);
                for (String header : collectionToConvert.getHeaders()) {
                    bos.write(escapeLineBreaks(msg.getElement(header)).getBytes());
                    bos.write(DELIMITER);
                }
            }
            bos.flush();
            bos.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String escapeLineBreaks(String data) {
        if (Objects.isNull(data)) {
            return "";
        }
        return data.replaceAll("\\R", "");
    }

}