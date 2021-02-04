package controllers;

import business.Account;
import business.User;
import data.AccountDB;
import util.AccountUtil;
import util.Util;
import util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/manageAccounts")
public class ManageAccountsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action.equals("modifySold")) {
            if (validateTransaction(req, resp)) {
                Account accountToModify = (Account)req.getAttribute("accountToModify");
                float amount = (float) req.getAttribute("amount");
                if (req.getParameter("modifyType").equals("withdraw"))
                    amount = -amount;
                accountToModify.setSold(accountToModify.getSold() + amount);
                AccountDB.update(accountToModify);
            }
        }

        else if (action.equals("changeMainAccount")) {
            User u = (User)req.getSession().getAttribute("user");
            int newMainAccountId = Integer.parseInt(req.getParameter("chooseAccount"));
            AccountDB.updateMainAccount(u, newMainAccountId);
            List<Account> accountList = AccountDB.getAccountsOfUser(u);
            req.setAttribute("accountList", accountList);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/JSPs/manageAccounts.jsp").forward(req, resp);
    }

    @SuppressWarnings("unchecked")
    private boolean validateTransaction(HttpServletRequest req, HttpServletResponse resp) {
        Util.updateAccountsSold();
        float amount = 0.0f;
        try {
            amount = ValidationUtil.validAmount(req.getParameter("quantity"));
        } catch (IllegalArgumentException ex) {
            req.setAttribute("message", "Amount can't be text or a negative value!");
            req.setAttribute("accountId", req.getParameter("accountId"));
            return false;
        }

        int accountId = Integer.parseInt(req.getParameter("accountId"));
        Account accountToModify = AccountUtil.getAccountFromList(accountId, (List<Account>)req.getSession().getAttribute("accountList"));

        if (req.getParameter("modifyType").equals("withdraw") && amount > accountToModify.getSold()) {
            req.setAttribute("accountId", req.getParameter("accountId"));
            req.setAttribute("message", "You can't withdraw more than you have");
            return false;
        }

        req.setAttribute("accountToModify", accountToModify);
        req.setAttribute("amount", amount);
        return true;
    }
}
