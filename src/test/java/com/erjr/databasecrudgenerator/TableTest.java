package com.erjr.databasecrudgenerator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TableTest {

    static final String TEST_COLUMN_TYPE = "varchar";
    static final String TEST_TABLE = "test_table";
    static final Object TEST_TABLE_CAMEL_CASE = "testTable";
    static final String TEST_COLUMN_NAME = "test_column";
    public static final String TEST_COLUMN_TYPE_INT = "int";
    private Table table;

    @Before
    public void before() {
        table = new Table(TEST_TABLE);
    }
    
    @Test
    public void tableNameTest() {
        assertEquals(TEST_TABLE, table.getName());
    }
    
    @Test
    public void tableNameTestCamelCase() {
        assertEquals(TEST_TABLE_CAMEL_CASE, table.getNameCamelCase());
    }
    
    @Test
    public void addFieldTest() {
        table.addColumn(TEST_COLUMN_NAME, TEST_COLUMN_TYPE);
        assertEquals(1, table.columns.size());
        Column column = table.columns.get(TEST_COLUMN_NAME);
        assertEquals(TEST_COLUMN_NAME, column.getName());
    }
    
    @Test
    public void addFieldTwiceTest() {
        table.addColumn(TEST_COLUMN_NAME, TEST_COLUMN_TYPE);
        table.addColumn(TEST_COLUMN_NAME, TEST_COLUMN_TYPE);
        table.addColumn(TEST_COLUMN_NAME, TEST_COLUMN_TYPE);
        assertEquals(1, table.columns.size());
    }
    
}
