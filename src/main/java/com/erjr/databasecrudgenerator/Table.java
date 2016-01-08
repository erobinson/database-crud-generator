package com.erjr.databasecrudgenerator;

import java.util.ArrayList;
import java.util.List;

public class Table extends DatabaseElement {

    List<Field> fields = new ArrayList<Field>();
    
    public Table addField(String name, String type) {
        Field field = new Field(name, type);
        if(!fields.contains(field)) {
            fields.add(field);    
        }
        return this;
    }
}
