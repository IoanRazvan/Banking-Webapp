package controllers;

import business.User;
import data.AccountDB;
import data.TransactionDB;
import data.UserDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signInVerification")
public class SignInVerificationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        redirectToJsp(req, resp);
    }

    private void redirectToJsp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (enteredCredentialsAreValid(req))
            handleSuccessfulSignIn(req, resp);
        else
            handleUnsuccessfulSignIn(req, resp);
    }

    private boolean enteredCredentialsAreValid(HttpServletRequest req) {
        User u = UserDB.getUserByUsername(req.getParameter("username"));
        String password = req.getParameter("password");
        return u != null && enteredPasswordIsCorrect(u, password);
    }

    private boolean enteredPasswordIsCorrect(User user, String password) {
        return user.getPassword().equals(password);
    }

    private void handleSuccessfulSignIn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = UserDB.getUserByUsername(req.getParameter("username"));
        registerUserAsSessionHolder(req, u);
        if (userHasAlreadyCreatedABankAccount(u))
            redirectUserWithAccount(req, resp, u);
        else
            redirectUserWithoutAnyAccount(req, resp);
    }

    private void registerUserAsSessionHolder(HttpServletRequest req, User u) {
        req.getSession().setAttribute("user", u);
    }

    private boolean userHasAlreadyCreatedABankAccount(User u) {
        return AccountDB.getMainAccount(u) != null;
    }

    private void redirectUserWithoutAnyAccount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("isMainAccount", true);
        forwardRequestAndResponseToUrl(req, resp, "/WEB-INF/JSPs/makeAccount.jsp");
    }

    private void redirectUserWithAccount(HttpServletRequest req, HttpServletResponse resp, User u) throws ServletException, IOException {
        addUserTransactionsToRequest(req, u);
        forwardRequestAndResponseToUrl(req, resp, "/WEB-INF/JSPs/statistics.jsp");
    }

    private void addUserTransactionsToRequest(HttpServletRequest req, User u) {
        req.setAttribute("targetTransaction", TransactionDB.getTransactionsByTargetAccountOwner(u));
        req.setAttribute("sourceTransaction", TransactionDB.getTransactionsBySourceAccountOwner(u));
    }

    private void handleUnsuccessfulSignIn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("message","Invalid credentials entered.");
        req.setAttribute("username", req.getParameter("username"));
        forwardRequestAndResponseToUrl(req, resp, "/WEB-INF/JSPs/signIn.jsp");
    }

    private void forwardRequestAndResponseToUrl(HttpServletRequest req, HttpServletResponse resp, String url) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(url).forward(req, resp);
    }
}