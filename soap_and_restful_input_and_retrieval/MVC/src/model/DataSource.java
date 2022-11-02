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
    private static final String JDBC_DB_URL = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/hayesd";
    private static final String JDBC_USER = "hayesd";
    private static final String JDBC_PASS = "jemptOll3";
    
    // Set pool configuration details
    static {
        config.setDriverClassName(JDBC_DRIVER);
        config.setJdbcUrl(JDBC_DB_URL);
        config.setUsername(JDBC_USER);
        config.setPassword(JDBC_PASS);
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
