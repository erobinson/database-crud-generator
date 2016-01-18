package com.erjr.databasecrudgenerator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import org.bitbucket.krausening.Krausening;

public class DatabaseStructure {

    HashMap<String, Table> tables = new HashMap<String, Table>();
    private static final String SQL_GET_TABLE_NAMES_MYSQL = "select table_name from information_schema.tables where table_schema = ?";
    private static final String SQL_GET_TABLE_NAMES_MSSQL = "select table_name from information_schema.tables where table_schema = ?";
    private static final String SQL_GET_COLUMN_NAMES_MYSQL = "select table_name, column_name, data_type from information_schema.columns where table_schema = ?";
    private static final String SQL_GET_COLUMN_NAMES_MSSQL = "select table_name, column_name, data_type from information_schema.columns where table_schema = ?";
    private static final String DATABASE_MYSQL = "mysql";
    private static final String DATABASE_MSSQL = "mssql";
    DBWrapper db = new DBWrapper();
    private String databaseName;
    private String databaseType;

    public DatabaseStructure() {
        Properties connectionProperties = Krausening.getInstance().getProperties("connection.properties");
        this.databaseName = connectionProperties.getProperty("database.name");
        this.databaseType = DATABASE_MSSQL.equals(connectionProperties.getProperty("database.type")) ? DATABASE_MSSQL
                : DATABASE_MYSQL;
    }

    public void queryDatabaseForStructure() throws SQLException {
        // TODO query the database for the table and field structure
        buildTablesFromDatabase();
        buildFieldsFromDatabase();
    }

    private void buildFieldsFromDatabase() throws SQLException {
        // TODO Auto-generated method stub
        String statementToUse = DATABASE_MSSQL.equals(databaseType) ? SQL_GET_TABLE_NAMES_MSSQL
                : SQL_GET_TABLE_NAMES_MYSQL;
        PreparedStatement preparedStatement = db.getConnection().prepareStatement(statementToUse);
        preparedStatement.setString(1, databaseName);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            String tableName = rs.getString("table_name");
            tables.put(tableName, new Table(tableName));
        }
    }

    private void buildTablesFromDatabase() throws SQLException {
        String statementToUse = DATABASE_MSSQL.equals(databaseType) ? SQL_GET_COLUMN_NAMES_MSSQL
                : SQL_GET_COLUMN_NAMES_MYSQL;
        PreparedStatement preparedStatement = db.getConnection().prepareStatement(statementToUse);
        preparedStatement.setString(1, databaseName);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            addField(rs.getString("table_name"), rs.getString("column_name"), rs.getString("data_type"));
        }
    }

    private void addField(String tableName, String columnName, String columnType) {
        if (!tables.containsKey(tableName)) {
            tables.put(tableName, new Table(tableName));
        }
        Table table = tables.get(tableName);
        table.addColumn(columnName, columnType);
    }

}
