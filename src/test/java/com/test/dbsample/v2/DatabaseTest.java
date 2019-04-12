package com.test.dbsample.v2;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseTest {

    private static final File TEST_DB_FILE = new File("test_db_file.db");

    @Test
    public void testReadWriteOperations() throws IOException {
        String key = "ke\ty";
        String value = "value";

        // 0. Instantiate a Database
        Database database = new Database(TEST_DB_FILE);

        // 1. Read non-existing entry
        String content = database.read(key);
        assertThat(content).isNull();

        // 2. Write entry
        database.write(key, value);

        // 3. Read entry and make sure it matches expected
        content = database.read(key);
        assertThat(content).isEqualTo(value);

        // 4. Update entry (write another entry with the same key)
        String newValue = "newValue";
        database.write(key, newValue);

        // 5. Read updated entry and make sure it matches expected
        content = database.read(key);
        assertThat(content).isEqualTo(newValue);
    }

    /*@After
    public void tearDown() {
        // Remove created file
        if (!TEST_DB_FILE.delete()) {
            throw new RuntimeException("Was not able to delete a file " + TEST_DB_FILE.getName());
        }
    }*/
}
