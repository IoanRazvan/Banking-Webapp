package controllers;

import business.Transaction;
import business.User;
import com.google.gson.Gson;
import data.TransactionDB;
import statistics.ExchangedMoney;
import statistics.TransactionData;
import statistics.TransactionFrame;
import util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/statistics")
public class StatisticsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        String data = "";
        String curr = req.getParameter("curr");
        String type = req.getParameter("type");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            data = serializeData(req, resp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PrintWriter out = resp.getWriter();
        out.print(data);
        out.flush();
    }

    // type = target (bani primit), type = source(bani trimisi)
    private List<ExchangedMoney> getExchangedMoney(HttpServletRequest req, HttpServletResponse resp, String type, String curr) throws ParseException {
        User user = (User) req.getSession().getAttribute("user");
        List<Transaction> transactions = TransactionDB.getTransactionsByOwnershipAndCurrency(user, type, curr);
        List<ExchangedMoney> exchangedMoney = new ArrayList<>();
        if (transactions != null) {
            for(Transaction t : transactions) {
                if(type.equals("source")){
                    exchangedMoney.add(new ExchangedMoney(t.getAmount(), t.getTransactionTimestamp()));
                } else {
                    float factor = TransactionUtil.getFactor(t.getSourceAccount().getCurrency(), t.getTargetAccount().getCurrency());
                    exchangedMoney.add(new ExchangedMoney(factor * t.getAmount(), t.getTransactionTimestamp()));
                }
            }
        }
        return exchangedMoney;
    }

    private String serializeData(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
        String[] currencies = {"ron", "dollar", "euro"};
        List<TransactionFrame> transactionFrames = new ArrayList<>();
        for(String currency : currencies) {
            List<ExchangedMoney> sentMoney = getExchangedMoney(req, resp, "source", currency);
            List<ExchangedMoney> recievedMoney = getExchangedMoney(req, resp, "target", currency);
            transactionFrames.add(new TransactionFrame(currency, new TransactionData(sentMoney, recievedMoney)));
        }
        return new Gson().toJson(transactionFrames);
    }
}
