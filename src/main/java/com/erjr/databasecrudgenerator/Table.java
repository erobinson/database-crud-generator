package com.erjr.databasecrudgenerator;

import java.util.HashMap;

public class Table extends DatabaseElement {

    HashMap<String, Column> columns = new HashMap<String, Column>();
    
    public Table(String name) {
        super(name);
    }

    public Table addColumn(String name, String type) {
        Column column = new Column(name, type);
        columns.put(name, column);
        return this;
    }
}
