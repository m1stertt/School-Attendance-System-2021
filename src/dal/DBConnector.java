package dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;

public class DBConnector {

    private SQLServerDataSource dataSource;

    protected DBConnector()
    {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setDatabaseName("  ");
        dataSource.setUser("  ");
        dataSource.setPassword("  ");
        dataSource.setPortNumber(1433);
    }

    protected Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }
}
