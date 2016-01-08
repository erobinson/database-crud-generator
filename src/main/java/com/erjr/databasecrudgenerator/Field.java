package com.erjr.databasecrudgenerator;

public class Field extends DatabaseElement {

    String typeCamelCase;
    String type;

    
    public Field(String name, String type) {
        super(name);
        setType(type);
    }

    public Field setType(String type) {
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
