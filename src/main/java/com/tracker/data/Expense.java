package com.tracker.data;

import com.tracker.servlets.DatabaseServlet;

import java.sql.*;

public class Expense {
    //test table data
    private Connection connection;

//    public static enum CategoryType {A, B, C, D};
    private int id;                                         //thes are the two columns with getters and setters
    private String employee;                                     //TODO DAO definition
    private int amount;
    private String expDate;
    private String categoryType;

    private boolean exists = false;

    public Expense() {     //constructors
    }

    public Expense(int id) {
        load(id);
    }  //constructors...loads the object based off the id

    // This is the READ in CRUD
    public void load(int id) {
        try
        {
            connection = DatabaseServlet.getConnection();

            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM expense WHERE id= "+id;
            ResultSet rs = stmt.executeQuery(query);
            rs.next();                                                                                                          //only one id so wont need to do while.rs.next()
            this.id = id;
            this.setEmployee(rs.getString("employee"));                                                                                   //setting dao from rs.
            this.setAmount(rs.getInt("amount"));                                                                                   //setting dao from rs.
            this.setExpDate(rs.getString("expDate"));                                                                                   //setting dao from rs.
            this.setCategoryType(rs.getString("categoryType"));                                                            //setting dao from rs.
            exists = true;                                                                                                      // global variable is default
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
        }
    }

    // This is the CREATE in CRUD
    public void saveNew() {                                                                                                     //create a new row
        if(!exists){                                                                                                            //if it already exists dont save...this is only for saving new...and doesnt exist
            try
            {
                connection = DatabaseServlet.getConnection();

                String query = "INSERT INTO expense (employee, amount, expDate, categoryType) VALUES (?, ?, ?, ?);";
//                String query = "INSERT INTO expense (employee) VALUES (?);";
                PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1,this.getEmployee());
                preparedStatement.setInt(2,this.getAmount());
                preparedStatement.setString(3,this.getExpDate());
                preparedStatement.setString(4,this.getCategoryType());
                ResultSet rs = preparedStatement.getGeneratedKeys();                                                            //from prepareStatement...getGeneratedKey execute and select to return an id
                if (rs != null && rs.next()) {
                    this.id = rs.getInt(1);
                    exists = true;
                }
            }
            catch(SQLException sqle){
                sqle.printStackTrace();
            }
        } else {
            System.out.println("ERROR: Object already exists in database. Don't use saveNew(), use update().");
        }
    }

    // This is the UPDATE in CRUD
    private void update() {                                         //call it if it already exists
        if(exists){
            try
            {
                connection = DatabaseServlet.getConnection();

                String query = "UPDATE expense SET employee = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1,this.getEmployee());
                preparedStatement.setInt(2,this.getId());

                preparedStatement.executeUpdate();
            }
            catch(SQLException sqle){
                sqle.printStackTrace();
            }
        } else {
            System.out.println("ERROR: Object does not exist in database yet. Don't use update(), use saveNew().");
        }
    }

    // This is the DELETE in CRUD
    private void delete(){
        if(exists){
            try
            {
                connection = DatabaseServlet.getConnection();

                String query = "DELETE FROM expense WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,this.getId());
                preparedStatement.executeUpdate();
                exists = false;
            }
            catch(SQLException sqle){
                sqle.printStackTrace();
            }
        } else {
            System.out.println("ERROR: Object does not exist in database yet. You must load() object before you can delete()");
        }
    }

    public int getId() {return id;}
    public void setId(int id) {
        this.id = id;}

    public String getEmployee() {return employee;}
    public void setEmployee(String employee) {
        this.employee = employee;}

    public int getAmount() {return amount;}
    public void setAmount(int amount) {
        this.amount = amount;}

    public String getExpDate() {return expDate;}
    public void setExpDate(String expDate) {
        this.expDate = expDate;}

    public String getCategoryType() {return categoryType;}
    public void setCategoryType(String categoryType) {this.categoryType = categoryType;}

//    public CategoryType getCategoryType() {return categoryType;}
//    public void setCategoryType(String categoryType) {this.categoryType = categoryType;}
}


