package com.tracker.servlets;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.*;

public class DatabaseServlet extends HttpServlet {

    private static ComboPooledDataSource connectionPool = null;
    private static final String JDBC_DRIVER = "org.hsqldb.jdbcDriver";
    private static final String DB_URL = "jdbc:hsqldb:expense_tracker_db";
    private static final String USER = "sa";
    private static final String PASS = "password";
    //run before starting compared to destroy
    public void init(ServletConfig config) throws ServletException {                //init unique to servlets-it is not a constructor
        try                                                                         //used only in servlet container-scans and identifies resources ready to go for constructing
        {                                                                           //pool of connections waiting to go...open connection...database is ready look in Maven C3P0
            final ComboPooledDataSource cpds = new ComboPooledDataSource();
            cpds.setDriverClass(JDBC_DRIVER);
            cpds.setJdbcUrl(DB_URL);
            cpds.setUser(USER);
            cpds.setPassword(PASS);
            connectionPool = cpds;
            System.out.println("NOTE: DATABASE CONNECTION POOL STARTED");
            testInitalLoad();
            expenseInitalLoad();//loads test data logs
            expenseCategoriesInitalLoad();
//            dropExpenseTable();
//            dropTestTable();
        }
        catch (PropertyVetoException pve)
        {
            pve.printStackTrace();
        }
    }
    private void dropTestTable() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            Connection conn = DriverManager.getConnection("jdbc:hsqldb:expense_tracker_db","sa","");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DROP TABLE test;");

            stmt.close();
            conn.close();
            System.out.println("NOTE: Finished test table drop Update");
        } catch(Exception e) {
            System.out.println("No Note test table didn't drop");
        }
    }

    private void dropExpenseTable() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            Connection conn = DriverManager.getConnection("jdbc:hsqldb:expense_tracker_db","sa","");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DROP TABLE expense;");

            stmt.close();
            conn.close();
            System.out.println("NOTE: Finished expense table drop Update");
        } catch(Exception e) {
            System.out.println("No Note expense table didn't drop");
        }
    }


    public static Connection getConnection()  //call this getConnection to get a connection from the pool
    {
        try
        {
            return connectionPool.getConnection();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private void testInitalLoad() {
        try {
            Connection connection = DatabaseServlet.getConnection();
            if(connection != null) {
                Statement stmt = connection.createStatement();
                String query = "SELECT * FROM test";
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
            } else {
                System.out.println("ERROR: connection is NULL");
            }
        } catch(SQLException sqle){
            try {
                System.out.println("NOTE: DATABASE DOES NOT EXIST, CREATING DATABASE");
                update("CREATE TABLE test (id INTEGER IDENTITY PRIMARY KEY, str VARCHAR(30))");
                update("INSERT INTO test (str) VALUES('Test1')");
                update("INSERT INTO test (str) VALUES('Test2')");
                update("INSERT INTO test (str) VALUES('Test3')");
                System.out.println("NOTE: DATABASE FINISHED CREATING");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void expenseInitalLoad() {
        try {
            Connection connection = DatabaseServlet.getConnection();
            if(connection != null) {
                Statement stmt = connection.createStatement();
                String query = "SELECT * FROM expense";
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
            } else {
                System.out.println("ERROR: connection is NULL");
            }
        }
        catch(SQLException sqle){
            try {
                System.out.println("NOTE: DATABASE DOES NOT EXIST, CREATING DATABASE");
                update("CREATE TABLE expense (id INTEGER IDENTITY PRIMARY KEY, employee VARCHAR(30), " +
                        "amount INTEGER, expDate VARCHAR(30), categoryType VARCHAR(10)");
                update("INSERT INTO expense (employee, amount, expDate, categoryType) VALUES('emp1', 100, '1/1/2020', A )");
                update("INSERT INTO expense (employee, amount, expDate, categoryType) VALUES('emp2', 200, '1/2/2020', B )");
                update("INSERT INTO expense (employee, amount, expDate, categoryType) VALUES('emp3', 300, '1/3/2020', C )");
                update("INSERT INTO expense (employee, amount, expDate, categoryType) VALUES('emp4', 400, '1/4/2020', D )");
                System.out.println("NOTE: DATABASE FINISHED CREATING");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void expenseCategoriesInitalLoad() {
        try {
            Connection connection = DatabaseServlet.getConnection();
            if(connection != null) {
                Statement stmt = connection.createStatement();
                String query = "SELECT * FROM expenseCategories";
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
            } else {
                System.out.println("ERROR: connection is NULL");
            }
        }
        catch(SQLException sqle){
            try {
                System.out.println("NOTE: DATABASE DOES NOT EXIST, CREATING DATABASE");
                update("CREATE TABLE expenseCategories (id INTEGER IDENTITY PRIMARY KEY, categoryType VARCHAR(10)");
                update("INSERT INTO expense (categoryType) VALUES('A')");
                update("INSERT INTO expense (categoryType) VALUES('B')");
                update("INSERT INTO expense (categoryType) VALUES('C')");
                update("INSERT INTO expense (categoryType) VALUES('D')");
                System.out.println("NOTE: DATABASE FINISHED CREATING");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void readAllJoinedRecords(boolean printMe) throws Exception{
        Class.forName("org.hsqldb.jdbcDriver");
        Connection conn = DriverManager.getConnection("jdbc:hsqldb:expense_tracker_db","sa","");
        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT * from note");
        ResultSet rs = stmt.executeQuery("SELECT expense.employee, expense.amount, expense.expDate, expense.categoryType" +
                "FROM expense LEFT JOIN expenseCategories " +
                "ON expense.categoryType = expenseCategories.id;");
        while(rs.next()) {
            String rowResults = rs.getString("expense.employee")+" -- "+rs.getString("expense.amount")
                    +" -- "+rs.getString("expense.expDate"+" -- "+rs.getString("expense.categoryType"));
            if(printMe) {
                System.out.println(rowResults);
            }
        }

        rs.close();
        stmt.close();
        conn.close();
    }

    private synchronized void update(String sqlExpression) throws SQLException {  //used on by inital load
        Connection connection = DatabaseServlet.getConnection();
        if(connection != null) {
            System.out.println("========= sqlExpression: "+sqlExpression);
            Statement stmt = connection.createStatement();
            int i = stmt.executeUpdate(sqlExpression);
            if (i == -1) {
                System.out.println("ERROR: database error in update "+sqlExpression);
            }
        }  else {
            System.out.println("ERROR: connection is NULL");
        }
    }

    public void destroy()  //lives in servlets...if the server goes down..before you shut down run this method
    {
        try
        {
            DataSources.destroy(connectionPool);  //close all connections to database
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {}

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {}
}
