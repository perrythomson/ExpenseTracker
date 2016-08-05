package com.tracker.servlets;

import com.tracker.data.Expense;
import com.tracker.data.util.ExpenseDataUtil;
//import com.newExpenseTracker.data.DataCacheJson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by perrythomson on 8/4/16.
 */
public class ExpenseControllerServlet extends HttpServlet {   //MVC
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        String uri = request.getRequestURI();
        System.out.println("Requested URI: "+uri);

        String jspName = uri.substring(uri.lastIndexOf('/')+1);
        System.out.println("JSP Name: "+jspName);

        if(jspName.equalsIgnoreCase("viewAllExpenseRecords")) {           //jsp for view all data
            ArrayList<Expense> expenses = ExpenseDataUtil.getAllExpenses();     //calls the helper TesDataUtil from getAllTests
            request.setAttribute("expenses",expenses);
        } else if (jspName.equalsIgnoreCase("viewExpenseData")) {
            String expenseId = request.getParameter("id");
            Expense expense = new Expense(Integer.parseInt(expenseId));
            request.setAttribute("expense",expense);
        } else if(jspName.equalsIgnoreCase("addNewExpenseRecord")) {
            Expense saveNewExpense = new Expense();
            saveNewExpense.setEmployee(request.getParameter("employee"));
            saveNewExpense.setAmount(request.getIntHeader("amount"));
            saveNewExpense.setExpDate(request.getParameter("expDate"));
            saveNewExpense.setCategoryType(request.getParameter("categoryType"));
//            saveNewExpense.setCategoryType(Expense.CategoryType.valueOf(request.getParameter("categoryType")));
//            DataCacheJson.setExpenseReport(saveNewExpense);
            jspName = "viewAllExpenseRecords";
        }
// else if (jspName.equalsIgnoreCase("updateExpenseReport")) {
//            String petId = request.getParameter("petid");
//            Pet updatePet = DataCacheJson.getPet(petId);
//            updatePet.setName(request.getParameter("name"));
//            updatePet.setPetType(Pet.PetType.valueOf(request.getParameter("pettype")));
//            updatePet.setOwnerId(request.getParameter("ownerid"));
//            DataCacheJson.setPet(updatePet);
//            jspName = "viewAllPets";


            RequestDispatcher view = request.getRequestDispatcher("/expenseViews/"+jspName+".jsp");
        view.forward(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request,response);
    }
}


