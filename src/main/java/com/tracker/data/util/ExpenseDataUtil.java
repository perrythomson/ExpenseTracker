package com.tracker.data.util;

import com.tracker.data.Expense;
import com.tracker.servlets.DatabaseServlet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ExpenseDataUtil {

    public static ArrayList<Expense> getAllExpenses() {  //array of DAO (data access objects)
        ArrayList<Expense> expenses = new ArrayList<>();
        try
        {
            Connection connection = DatabaseServlet.getConnection();

            Statement stmt = connection.createStatement();
            String query = "SELECT id FROM expense";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                System.out.println(rs.getInt("id"));
                //loop as long as there are results in row(s)
                Expense expense = new Expense(rs.getInt("id"));
                expenses.add(expense);
            }
        }
        catch(SQLException sqle){
            sqle.printStackTrace();
        }

        return expenses;
    }
}


