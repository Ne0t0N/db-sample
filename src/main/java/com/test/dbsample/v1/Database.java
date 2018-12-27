package com.test.dbsample.v1;

import java.io.*;

public class Database {

    private final File dbFile;

    public Database(File dbFile) throws IOException {
        this.dbFile = dbFile;
        this.dbFile.createNewFile(); // creates a file only if it does not exists yet
    }

    public String read() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(dbFile))) {
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from file " + dbFile);
        }

        return sb.toString();
    }

    public void write(String data) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(dbFile, true))) {
            out.write(data);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write data to file " + dbFile);
        }
    }
}
