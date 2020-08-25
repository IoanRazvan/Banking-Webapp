package controllers;

import business.User;
import data.TransactionDB;
import data.UserDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/authenticate")
public class AuthenticationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url;

        if (validateSignIn(req, resp)) {
            req.setAttribute("targetTransaction", TransactionDB.getTransactionsByTargetAccountOwner((User) req.getSession().getAttribute("user")));
            req.setAttribute("sourceTransaction", TransactionDB.getTransactionsBySourceAccountOwner((User) req.getSession().getAttribute("user")));
            url = "/WEB-INF/JSPs/statistics.jsp";
        } else {
            url = "/WEB-INF/JSPs/signIn.jsp";
        }

        getServletContext().getRequestDispatcher(url).forward(req, resp);
    }

    private boolean validateSignIn(HttpServletRequest req, HttpServletResponse resp) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String message = "";

        boolean validDataEntered = true;

        User user = UserDB.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            message = "Invalid credentials entered";
            validDataEntered = false;
        }

        if (validDataEntered)
            req.getSession().setAttribute("user", user);
        else {
            req.setAttribute("username", username);
            req.setAttribute("password", password);
            req.setAttribute("message", message);
        }

        return validDataEntered;
    }
}