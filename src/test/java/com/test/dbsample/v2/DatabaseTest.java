package com.test.dbsample.v2;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseTest {

    private static final File TEST_DB_FILE = new File("test_db_file.db");

    @Test(expected = IllegalArgumentException.class)
    public void whenNewLineInKey_shouldThrowIAE() throws IOException {
        Database database = new Database(TEST_DB_FILE);

        database.write("ke\ny", "value");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNewLineInValue_shouldThrowIAE() throws IOException {
        Database database = new Database(TEST_DB_FILE);

        database.write("key", "val\nue");
    }

    @Test
    public void whenNoKeyValuePair_shouldReturnNull() throws IOException {
        Database database = new Database(TEST_DB_FILE);

        String actualValue = database.read("some key");
        assertThat(actualValue).isNull();
    }

    @Test
    public void testReadWriteOperations() throws IOException {
        Database database = new Database(TEST_DB_FILE);
        String key = "some key";
        String value = "some value";

        database.write(key, value);

        String actualValue = database.read(key);
        assertThat(actualValue).isEqualTo(value);
    }

    @Test
    public void testUpdateOperation() throws IOException {
        Database database = new Database(TEST_DB_FILE);
        String key = "key";
        String value = "value";

        // 1. Write entry
        database.write(key, value);

        // 2. Read entry and make sure it matches expected
        String content = database.read(key);
        assertThat(content).isEqualTo(value);

        // 3. Update entry (write another entry with the same key)
        String newValue = "newValue";
        database.write(key, newValue);

        // 4. Read updated entry and make sure it matches expected
        content = database.read(key);
        assertThat(content).isEqualTo(newValue);
    }

    @After
    public void tearDown() {
        // Remove created file
        if (!TEST_DB_FILE.delete()) {
            throw new RuntimeException("Was not able to delete a file " + TEST_DB_FILE.getName());
        }
    }
}
