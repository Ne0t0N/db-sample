package com.test.dbsample.v2;

import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

public class Database {

    private static final String DELIMITER = ":";

    private final File dbFile;

    public Database(File dbFile) throws IOException {
        this.dbFile = dbFile;
        this.dbFile.createNewFile(); // creates a file only if it does not exists yet
    }

    public String read(String key) {
        String sizePrefixedKey = sizePrefixedKey(key);
        try (ReversedLinesFileReader in = new ReversedLinesFileReader(dbFile, Charset.defaultCharset())) {
            String line;
            while ((line = in.readLine()) != null) {
                if (line.startsWith(sizePrefixedKey)) {
                    return line.substring(sizePrefixedKey.length() + DELIMITER.length());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from file " + dbFile);
        }

        return null;
    }

    public void write(String key, String value) {
        if (key.contains("\n") || value.contains("\n")) {
            throw new IllegalArgumentException("New line characters in keys or values are prohibited");
        }

        try (BufferedWriter out = new BufferedWriter(new FileWriter(dbFile, true))) {
            out.write(String.join(DELIMITER, sizePrefixedKey(key), value));
            out.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write data to file " + dbFile);
        }
    }

    private static String sizePrefixedKey(String key) {
        return String.join(DELIMITER, Integer.toString(key.length()), key);
    }
}
