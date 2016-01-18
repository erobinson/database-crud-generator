package com.erjr.databasecrudgenerator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ColumnTest {
    private Column column;

    @Before
    public void before() {
        column = new Column(TableTest.TEST_COLUMN_NAME, TableTest.TEST_COLUMN_TYPE);
    }
    
    @Test
    public void setTypeIntTest() {
        column.setType(TableTest.TEST_COLUMN_TYPE_INT);
        assertEquals(TableTest.TEST_COLUMN_TYPE_INT, column.getType());
        assertEquals(TableTest.TEST_COLUMN_TYPE_INT, column.getTypeCamelCase());
    }

}
