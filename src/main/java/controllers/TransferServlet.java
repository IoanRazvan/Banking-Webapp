package controllers;

import business.Account;
import business.Transaction;
import business.User;
import data.AccountDB;
import data.TransactionDB;
import data.UserDB;
import util.TransactionUtil;
import util.Util;
import util.ValidationUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;


@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String option = req.getParameter("option");

        if (option != null && option.equals("personal"))
            sendPersonalAccountsTable(req, resp);
        else if (option != null && option.equals("others"))
            sendOtherUsersTable(req, resp);
    }

    private void sendPersonalAccountsTable(HttpServletRequest req, HttpServletResponse resp) {
        User u = (User) req.getSession().getAttribute("user");
        List<Account> accounts = AccountDB.getAllNonMainAccounts(u);

        resp.setContentType("/text/html;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>AccountID</th>");
            out.println("<th>CreationDate</th>");
            out.println("<th>Sold</th>");
            out.println("<th>Currency</th>");
            out.println("<th></th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            if (accounts != null) {
                for (Account acc : accounts) {
                    out.println("<tr>");
                    out.format("<td>%s</td>\n", acc.getAccountId());
                    out.format("<td>%s</td>\n", acc.getCreationDate());
                    out.format("<td>%s</td>\n", acc.getSold());
                    out.format("<td>%s</td>\n", acc.getCurrency());
                    out.format("<td><button id=\"%s\" class=\"btn btn-success\" data-toggle=\"modal\" data-target=\"#sendMoneyModal\" onclick=\"addMultipleHiddenInputs('formModify', {accountId: '%s', isPersonal: true})\">" +
                            "<i class=\"fas fa-arrow-left\"></i>" +
                            "</button></td>", acc.getAccountId(), acc.getAccountId());
                    out.println("</tr>");
                }
            }
            out.println("</tbody>");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    private void sendOtherUsersTable(HttpServletRequest req, HttpServletResponse resp) {
        User u = (User) req.getSession().getAttribute("user");
        List<Account> mainAccounts = AccountDB.getMainAccountEntries(u);

        resp.setContentType("/text/html;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>Username</th>");
            out.println("<th>First Name</th>");
            out.println("<th>Last Name</th>");
            out.println("<th>Phone Number</th>");
            out.println("<th></th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            if (mainAccounts != null) {
                for (Account a : mainAccounts) {
                    User usr = a.getOwner();
                    Integer mainAccId = a.getAccountId();
                    out.println("<tr>");
                    out.format("<td>%s</td>\n", usr.getUsername());
                    out.format("<td>%s</td>\n", usr.getFirstName());
                    out.format("<td>%s</td>\n", usr.getLastName());
                    out.format("<td>%s</td>\n", usr.getPhoneNumber());
                    out.format("<td><button id=\"%s\" class=\"btn btn-success\" data-toggle=\"modal\" data-target=\"#sendMoneyModal\" onclick=\"addMultipleHiddenInputs('formModify', {accountId: '%s', isPersonal: false})\">" +
                            "<i class=\"fas fa-arrow-left\"></i>" +
                            "</button></td>", mainAccId,  mainAccId);
                    out.println("</tr>");
                }
            }
            out.println("</tbody>");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isPersonal = Boolean.parseBoolean(req.getParameter("isPersonal"));
        if (validateTransaction(req, resp)) {
            Account targetAccount = AccountDB.getAccountById(Integer.parseInt(req.getParameter("accountId")));
            Account sourceAccount = (Account)req.getAttribute("mainAccount");
            if (targetAccount == null || sourceAccount == null) {
                System.out.println("hora hora");
                return;
            }
            float amount = Float.parseFloat(req.getParameter("quantity"));
            Transaction tr = new Transaction(sourceAccount, targetAccount, new Date(), amount, "");
            sourceAccount.setSold(sourceAccount.getSold()-amount);
            AccountDB.update(sourceAccount);
            if (isPersonal) {
                float factor = TransactionUtil.getFactor(sourceAccount.getCurrency(), targetAccount.getCurrency());
                targetAccount.setSold(targetAccount.getSold() + factor * amount);
                AccountDB.update(targetAccount);
                tr.setStatus("accepted");

            }
            else {
                tr.setStatus("waiting");
            }
            TransactionDB.insert(tr);
        }

        else {
            req.setAttribute("buttonId", req.getParameter("accountId"));
        }

        req.setAttribute("isPersonal", isPersonal);

        if (isPersonal)
            req.setAttribute("personalAccounts", AccountDB.getAllNonMainAccounts((User)req.getSession().getAttribute("user")));
        else
            req.setAttribute("mainAccounts", AccountDB.getMainAccountEntries((User)req.getSession().getAttribute("user")));
        getServletContext().getRequestDispatcher("/WEB-INF/JSPs/transfer.jsp").forward(req, resp);
    }

    private boolean validateTransaction(HttpServletRequest req, HttpServletResponse resp) {
        User usr = (User)req.getSession().getAttribute("user");
        float amount = 0.0f;
        String message;

        try {
            amount = ValidationUtil.validAmount(req.getParameter("quantity"));
        } catch (IllegalArgumentException ex) {
            req.setAttribute("message", "Amount can't be a negative value!");
            return false;
        }

        if (amount < 10) {
            req.setAttribute("message", "Minimum transfer amount is 10!");
            return false;
        }

        Util.updateAccountsSold();
        Account mainAcc = AccountDB.getMainAccount(usr);
        if (mainAcc != null && mainAcc.getSold() < amount) {
            req.setAttribute("message", "Your main account has insufficient funds to complete this transaction!");
            return false;
        }
        req.setAttribute("mainAccount", mainAcc);
        return true;
    }
}
