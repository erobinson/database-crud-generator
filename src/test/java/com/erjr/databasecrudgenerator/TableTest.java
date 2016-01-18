package com.erjr.databasecrudgenerator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TableTest {

    private static final String TEST_COLUMN_TYPE = "varchar";
    private static final String TEST_TABLE = "test_table";
    private static final Object TEST_TABLE_CAMEL_CASE = "testTable";
    private static final String TEST_COLUMN_NAME = "test_column";
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
