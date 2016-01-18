package com.erjr.databasecrudgenerator;

public class Column extends DatabaseElement {

    String typeCamelCase;
    String type;

    
    public Column(String name, String type) {
        super(name);
        setType(type);
    }

    public Column setType(String type) {
        this.type = type;
        this.typeCamelCase = Utils.camelCaseString(type);
        return this;
    }

    public String getType() {
        return type;
    }

    public String getTypeCamelCase() {
        return typeCamelCase;
    }

}
