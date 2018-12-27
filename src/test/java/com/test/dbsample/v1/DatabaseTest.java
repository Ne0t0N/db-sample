package com.test.dbsample.v1;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseTest {

    private static final File TEST_DB_FILE = new File("test_db_file.db");

    @Test
    public void testReadWriteOperations() throws IOException {
        // 0. Instantiate a Database
        Database database = new Database(TEST_DB_FILE);

        // 1. Read database and make sure that it is empty
        String content = database.read();
        assertThat(content).isEmpty();

        // 2. Write some data
        String dataToWrite = "Lorem ipsum dolor sit amet, consectetur adipiscing elit...";
        database.write(dataToWrite);

        // 3. Read data and make sure that it is exactly what was written there
        content = database.read();
        assertThat(content).isEqualTo(dataToWrite);

        // 4. Write more data
        String moreDataToWrite = "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua...";
        database.write(moreDataToWrite);

        // 5. Read data and and check for equality
        content = database.read();
        assertThat(content).isEqualTo(dataToWrite + moreDataToWrite);
    }

    @After
    public void tearDown() {
        // Remove created file
        if (!TEST_DB_FILE.delete()) {
            throw new RuntimeException("Was not able to delete a file " + TEST_DB_FILE.getName());
        }
    }
}
