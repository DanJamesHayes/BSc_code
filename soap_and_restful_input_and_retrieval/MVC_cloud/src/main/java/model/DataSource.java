package model;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Class to implement connection pool using the HikariCP framework
 * 
 * @author Daniel Hayes
 *
 */
public class DataSource {
	
	private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
     
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
 
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "zxppjCHMJ71ek9Hg";

    // Set pool configuration details
    static {
        config.setDriverClassName(JDBC_DRIVER);
        config.setJdbcUrl(String.format("jdbc:mysql:///%s", "filmsdata"));
        config.setUsername(JDBC_USER);
        config.setPassword(JDBC_PASS);
        config.addDataSourceProperty("socketFactory", 
        		"com.google.cloud.sql.mysql.SocketFactory");
        config.addDataSourceProperty("cloudSqlInstance", 
        		"augmented-voice-263815:europe-west1:myinstance");
        config.addDataSourceProperty("useSSL", "false");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }
    
    /**
     * Get connection from pool
     * 
     * @return Connection
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    
    /**
     * Private construct prevents instance creation, connections can be created
     * with the above method without the need to create an instance of the class
     */
    private DataSource(){}
}
