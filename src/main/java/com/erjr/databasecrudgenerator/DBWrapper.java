package com.erjr.databasecrudgenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.bitbucket.krausening.Krausening;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBWrapper {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(DBWrapper.class);
    private static String dbName;
    private static String server;
    private static String password;
    private static String userName;
    private static Properties connectionProperties;
    private Connection conn;
    private Connection connMaster;


    static {
        // initialize Krausening:
        Krausening krausening = Krausening.getInstance();
        krausening.loadProperties();
        connectionProperties = krausening.getProperties("connection.properties");
        dbName = connectionProperties.getProperty("database.name");
        server = connectionProperties.getProperty("database.server");
        password = connectionProperties.getProperty("database.password");
        userName = connectionProperties.getProperty("database.userName");
    }

    protected void connect() throws Exception {
        if(conn != null) {
            return;
        }
        Class.forName(connectionProperties.getProperty("database.driver"));

        // TODO: update w/ owner-support for encyrpted properties, which is not
        // working in Krausening 2:
        String password = connectionProperties.getProperty("password");

        String connectionString = connectionProperties.getProperty("database.connection.string");
        
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Using connection string: "
                    + connectionString.replace(password, "*******"));
        }

        conn = DriverManager.getConnection(connectionString);

        Assert.assertNotNull(conn);
    }

//    // I don't think that this is used in the anonymizer.
//    protected void connectMaster() throws Exception {
//        if(connMaster != null) {
//            return;
//        }
//        Class.forName(anonymizerProperties.getDatabaseDriver());
//
//        // TODO: update w/ owner-support for encyrpted properties, which is not
//        // working in Krausening 2:
//        String password = Krausening.getInstance()
//                .getProperties("connection.properties").getProperty("password");
//
//        StringBuilder connectionString = new StringBuilder();
//        connectionString.append("jdbc:jtds:sqlserver://")
//                .append(anonymizerProperties.getServer()).append(':')
//                .append(anonymizerProperties.getPort()).append(";DatabaseName=")
//                .append("master").append(";user=")
//                .append(anonymizerProperties.getUsername()).append(";password=")
//                .append(password);
//
//        if (LOGGER.isDebugEnabled()) {
//            LOGGER.debug("Using connection string: "
//                    + connectionString.toString().replace(password, "*******"));
//        }
//
//        connMaster = DriverManager.getConnection(connectionString.toString());
//
//        Assert.assertNotNull(connMaster);
//    }

    public ResultSet select(String sql) {
        Statement stmt;
        try {
            connect();
            stmt = conn.createStatement();
            LOGGER.debug("running sql : " + sql);
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            LOGGER.error("Unable to run select query : "+sql, e);
            return null;
        } catch (Exception e) {
            LOGGER.error("Unable to run select query : "+sql, e);
            return null;
        }
    }

    public boolean execute(String[] sqlStrings) {
        try {
            Statement stmt = conn
                    .createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
            conn.setAutoCommit(false);
            for (String sql : sqlStrings) {
                stmt.addBatch(sql);
            }
            int[] reses = stmt.executeBatch();
            for (int res : reses) {
                if (res < 0) {
                    conn.rollback();
                    return false;
                }
            }
            conn.commit();
            LOGGER.info("Batch executed");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

//    public static boolean runSqlFile(File sqlToRunFile) {
//        String windowscmd = "sqlcmd -b -S tcp:" + server + " -d " + dbName
//                + " -i \"" + sqlToRunFile.getAbsolutePath() + "\" ";
//        if(userName != null && password != null){
//            windowscmd += "-U "+userName+" -P "+password;
//        }
//        LOGGER.info("INFO: About to Run sql: " + windowscmd);
//
//        try {
//            // Run "netsh" Windows command
//            Process process = Runtime.getRuntime().exec(windowscmd);
//            // Get input streams
//            BufferedReader stdInput = new BufferedReader(new InputStreamReader(
//                    process.getInputStream()));
//            BufferedReader stdError = new BufferedReader(new InputStreamReader(
//                    process.getErrorStream()));
//
//            // Read command standard output
//            String s;
//            LOGGER.info("Standard output: ");
//            while ((s = stdInput.readLine()) != null) {
//                LOGGER.info(s);
//            }
//            // Read command errors
//            LOGGER.info("Standard error: ");
//            while ((s = stdError.readLine()) != null) {
//                LOGGER.info(s);
//            }
//
//            LOGGER.info("Error Level of sql: " + process.exitValue());
//            if (process.exitValue() == 0) {
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            e.printStackTrace(System.err);
//            return false;
//        }
//    }
//
//    private static File createRandFile(File tempOutputDir, String fileName) {
//        Random rand = new Random();
//        while (true) {
//            File f = new File(tempOutputDir + "\\" + fileName + "_"
//                    + rand.nextInt(1000) + ".sql");
//            if (!f.exists()) {
//                return f;
//            }
//        }
//    }
//
//    public boolean runSqlTemplateFile(String filePath,
//            Map<String, Object> parameters) {
//        File baseDir = new File("src/test/resources/"
//                + filePath.substring(0, filePath.lastIndexOf("\\")));
//        File tempOutputDir = new File("target/temp/sql/");
//        tempOutputDir.mkdirs();
//        String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
//        File tempOutputFile = createRandFile(tempOutputDir, fileName);
//
//        Configuration cfg = new Configuration();
//        try {
//            cfg.setDirectoryForTemplateLoading(baseDir);
//            cfg.setDefaultEncoding("UTF-8");
//            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
//            Template template = cfg.getTemplate(fileName);
//            tempOutputFile.createNewFile();
//            FileWriter fw = new FileWriter(tempOutputFile);
//            Writer out = new BufferedWriter(fw);
//            template.process(parameters, out);
//            out.close();
//            fw.close();
//
//            return runSqlFile(tempOutputFile);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (TemplateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public void beginTransaction() {
//        // TODO Auto-generated method stub
//    }
//
//    public void commitTransaction() {
//        // TODO Auto-generated method stub
//    }

//    public boolean executeOnMaster(String sql) {
//        try {
//            connectMaster();
//            return execute(sql, connMaster);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//
//    }

    public boolean execute(String sql) {
        try {
            connect();
            return execute(sql, conn);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean execute(String sql, Connection conn) {
        Statement stmt;
        try {
            stmt = conn.createStatement();
            LOGGER.debug("running sql : " + sql);
            stmt.execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

