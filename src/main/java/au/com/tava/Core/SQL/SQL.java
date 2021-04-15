package au.com.tava.Core.SQL;

import org.testng.log4testng.Logger;

import java.sql.*;

/**
 * Created by Greg on 22/11/2016.
 * https://stackoverflow.com/questions/3182282/how-to-install-jdbc-and-how-to-use-it-to-connect-to-mysql
 *
 * Using with Maven
 * add dependency for DB connection:
 * Sample iwth maria, similar for MS SQL mySQL postGresSQL
 <dependency>
 <groupId>org.mariadb.jdbc</groupId>
 <artifactId>mariadb-java-client</artifactId>
 <version>2.1.2</version>
 </dependency>

 set a connection string to
 jdbc:(mysql|mariadb):[replication:|failover:|sequential:|aurora:]//<hostDescription>[,<hostDescription>...]/[database]
 [?<key1>=<value1>[&<key2>=<value2>]]

 * This code may need to address non-default schema's
 */
public class SQL extends SQLBase {

    private Logger logger = Logger.getLogger(this.getClass());

    protected  Connection conn;
    private Statement statement;
    private ResultSet resultSet ;
    private CallableStatement cStmt;

    public  void getConnection(String driver, String url, String username, String password) throws ClassNotFoundException, SQLException
    {
        Connection connection = null;

        //Class.forName(driver);
        conn = DriverManager.getConnection(url, username, password);
    }

    public void runSqlStatement( String sqlStatement ) throws SQLException {
        statement = conn.createStatement();
        resultSet = statement.executeQuery( sqlStatement ) ;
    }


    public ResultSet getResultSet() {
        return resultSet;
    }

    public  void close(Connection connection)
    {
        try
        {
            if (connection != null)
            {
                connection.close();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public  void close(Statement statement)
    {
        try
        {
            if (statement != null)
            {
                statement.close();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void close(ResultSet resultSet)
    {
        try
        {
            if (resultSet != null)
            {
                resultSet.close();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
