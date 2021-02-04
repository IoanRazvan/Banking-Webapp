package controllers;

import business.Account;
import business.Transaction;
import business.TransactionPK;
import business.User;
import data.AccountDB;
import data.TransactionDB;
import util.TransactionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/notifications")
public class NotificationsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        Account source = AccountDB.getAccountById(Integer.parseInt(req.getParameter("sourceId")));
        Account target = AccountDB.getAccountById(Integer.parseInt(req.getParameter("targetId")));
        String stringData = req.getParameter("date");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy");
        Date date = null;
        try {
            date = simpleDateFormat.parse(stringData);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String url = "/WEB-INF/JSPs/notifications.jsp";
        TransactionPK trPk = new TransactionPK(source.getAccountId(), target.getAccountId(), date);
        Transaction tr = TransactionDB.getByPrimaryKey(trPk);
        float amount = Float.parseFloat(req.getParameter("amount"));
        if (action.equals("decline")) {
            tr.setStatus("declined");
            TransactionDB.update(tr);
        }
        if (action.equals("accept")) {
            float factor = TransactionUtil.getFactor(source.getCurrency(), target.getCurrency());
            target.setSold(target.getSold() + factor * amount);
            AccountDB.update(target);
            tr.setStatus("accepted");
            TransactionDB.update(tr);
        }
        req.setAttribute("data", TransactionDB.getWaitingTransactions((User) req.getSession().getAttribute("user")));
        getServletContext().getRequestDispatcher(url).forward(req, resp);
    }
}
