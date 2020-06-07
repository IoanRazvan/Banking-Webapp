package controllers;

import business.Account;
import business.User;
import data.AccountDB;
import data.TransactionDB;
import data.UserDB;
import util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/insertNewAccount")
public class NewBankAccountServlet extends HttpServlet {

    private boolean validateAccount(HttpServletRequest req, HttpServletResponse res) {
        boolean validDataEntered = true;
        float sold = 0.0f;
        try {
            sold = ValidationUtil.validAmount(req.getParameter("sold"));
        } catch (Exception e) {
            validDataEntered = false;
        }

        if (validDataEntered) {
            String currency = req.getParameter("currency");
            User owner = UserDB.getUserById(Integer.parseInt(req.getParameter("owner_id")));
            boolean mainAccount = Boolean.parseBoolean(req.getParameter("main_account"));
            AccountDB.insert(new Account(
                    owner,
                    new Date(),
                    currency,
                    sold,
                    mainAccount
            ));
        } else {
            boolean mainAccount = Boolean.parseBoolean(req.getParameter("main_account"));
            req.setAttribute("message", "Sold can't be a text or a negative value");
            req.setAttribute("isMainAccount", mainAccount);
        }
        return validDataEntered;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        boolean successfulAccountCreation = validateAccount(req, res);
        boolean isFirstAccount = Boolean.parseBoolean(req.getParameter("main_account"));
        String url = "";
        if (successfulAccountCreation && isFirstAccount) {
            req.setAttribute("targetTransaction", TransactionDB.getTransactionsByTargetAccountOwner((User) req.getSession().getAttribute("user")));
            req.setAttribute("sourceTransaction", TransactionDB.getTransactionsBySourceAccountOwner((User) req.getSession().getAttribute("user")));
            url = "/WEB-INF/JSPs/statistics.jsp";
        } else {
            url = "/WEB-INF/JSPs/makeAccount.jsp";
        }
        getServletContext().getRequestDispatcher(url).forward(req, res);
    }
}
