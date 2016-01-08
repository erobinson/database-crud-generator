package com.erjr.databasecrudgenerator;

import java.util.ArrayList;
import java.util.Properties;

import org.bitbucket.krausening.Krausening;

public class DatabaseStructure {

    ArrayList<Table> tables;
    private static final String SQL_GET_TABLE_NAMES_MYSQL = "select table_name from information_schema.tables where table_schema = %s";
    private static final String SQL_GET_TABLE_NAMES_SQL = "select table_name from information_schema.tables where table_schema = %s";
    private static final String SQL_GET_FIELD_NAMES_MYSQL = "select table_name, column_name, data_type from information_schema.columns where table_schema = %s";
    private static final String SQL_GET_FIELD_NAMES_SQL = "select table_name, column_name, data_type from information_schema.columns where table_schema = %s";
    DBWrapper db = new DBWrapper();
    private String databaseName;

    public void queryDatabaseForStructure(String databaseName) {
        // TODO query the database for the table and field structure
        Properties connectionProperties = Krausening.getInstance().getProperties("connection.properties");
        this.databaseName = connectionProperties.getProperty("database.name");
        buildTablesFromDatabase();
        buildFieldsFromDatabase();
    }

    private void buildFieldsFromDatabase() {
        // TODO Auto-generated method stub

    }

    private void buildTablesFromDatabase() {

    }

}
