package com.erjr.databasecrudgenerator;

public class DatabaseElement {

    String name;
    String nameCamelCase;
    
    public DatabaseElement(String name) {
        setName(name);
    }

    public DatabaseElement setName(String name) {
        this.name = name;
        nameCamelCase = Utils.camelCaseString(name);
        return this;
    }
    
    public String getName() {
        return name;
    }
}
