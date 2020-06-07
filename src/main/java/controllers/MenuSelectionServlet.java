package controllers;

import business.Account;
import business.Transaction;
import business.User;
import data.AccountDB;
import data.TransactionDB;
import data.UserDB;
import javafx.util.Pair;
import util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/menu")
public class MenuSelectionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getParameter("option");
        String url = "/index.html";
        HttpSession session = req.getSession();
        User u = (User)session.getAttribute("user");

        if (u == null)
            url = "/index.html";

        else if (option == null || option.equals("") || option.equals("Statistics")){
            req.setAttribute("targetTransaction", TransactionDB.getTransactionsByTargetAccountOwner((User) req.getSession().getAttribute("user")));
            req.setAttribute("sourceTransaction", TransactionDB.getTransactionsBySourceAccountOwner((User) req.getSession().getAttribute("user")));
            url = "/WEB-INF/JSPs/statistics.jsp";
        }

        else if (option.equals("Add Another Account")) {
            req.setAttribute("isMainAccount", false);
            url = "/WEB-INF/JSPs/makeAccount.jsp";
        }

        else if (option.equals("Manage Accounts")) {
            Util.updateAccountsSold();
            List<Account> accountList = AccountDB.getAccountsOfUser(u);
            session.setAttribute("accountList", accountList);
            url = "/WEB-INF/JSPs/manageAccounts.jsp";
        }

        else if (option.equals("Log Out")) {
            req.getSession().setAttribute("user", null);
            url = "/index.html";
        }

        else if (option.equals("Edit User")) {
            ArrayList<Pair<String, String>> fieldValuePairs = new ArrayList<>();
            fieldValuePairs.add(new Pair<>("Username", u.getUsername()));
            fieldValuePairs.add(new Pair<>("First Name", u.getFirstName()));
            fieldValuePairs.add(new Pair<>("Last Name", u.getLastName()));
            fieldValuePairs.add(new Pair<>("Phone Number", u.getPhoneNumber()));
            fieldValuePairs.add(new Pair<>("Password", ""));
            session.setAttribute("fieldValuePairs", fieldValuePairs);
            url = "/WEB-INF/JSPs/editUser.jsp";
        }

        else if (option.equals("Transfer")){
            req.setAttribute("personalAccounts", AccountDB.getAllNonMainAccounts(u));
            req.setAttribute("isPersonal", true);
            url = "/WEB-INF/JSPs/transfer.jsp";
        }

        else if (option.equals("Notifications")) {
            User user = (User) session.getAttribute("user");
            List<Transaction> data = TransactionDB.getWaitingTransactions(user);
            req.setAttribute("data", data);
            url = "/WEB-INF/JSPs/notifications.jsp";
        }
        getServletContext().getRequestDispatcher(url).forward(req, resp);
    }
}
