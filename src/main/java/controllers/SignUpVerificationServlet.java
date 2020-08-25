package controllers;

import business.User;
import data.UserDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signUpVerification")
public class SignUpVerificationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url;
        if (signedUpSuccessfully(req)) {
            User newUser = constructUserObjectFromRequestParameters(req);
            insertNewUserIntoDatabase(newUser);
            registerUserOfSession(req, newUser);
            url = "/WEB-INF/JSPs/makeAccount.jsp";
        } else {
            setRequestParametersAsRequestAttributes(req);
            url = "/WEB-INF/JSPs/signUp.jsp";
        }
        getServletContext().getRequestDispatcher(url).forward(req, resp);
    }

    private boolean signedUpSuccessfully(HttpServletRequest req) {
        return checkForUniqueUsername(req.getParameter("username"), req) && checkForUniquePhoneNumber(req.getParameter("phoneNumber"), req);
    }

    private boolean checkForUniqueUsername(String username, HttpServletRequest req) {
        if (UserDB.getUserByUsername(username) == null)
            return true;
        registerErrorMessage("The username entered is already registered.", req);
        return false;
    }

    private boolean checkForUniquePhoneNumber(String phoneNumber, HttpServletRequest req) {
        if (UserDB.getUserByPhoneNumber(phoneNumber) == null)
                return true;
        registerErrorMessage("The phone number entered is already registered.", req);
        return false;
    }

    private void registerErrorMessage(String message, HttpServletRequest req) {
        req.setAttribute("message", message);
    }

    private User constructUserObjectFromRequestParameters(HttpServletRequest req) {
        User newUser = new User();
        newUser.setFirstName(req.getParameter("firstName"));
        newUser.setLastName(req.getParameter("lastName"));
        newUser.setUsername(req.getParameter("username"));
        newUser.setPhoneNumber(req.getParameter("phoneNumber"));
        newUser.setPassword(req.getParameter("password"));
        return newUser;
    }

    private void insertNewUserIntoDatabase(User newUser) {
        UserDB.insert(newUser);
    }

    private void registerUserOfSession(HttpServletRequest req, User newUser) {
        req.getSession().setAttribute("user", newUser);
        req.setAttribute("isMainAccount", true);
    }

    private void setRequestParametersAsRequestAttributes(HttpServletRequest req) {
        req.setAttribute("firstName", req.getParameter("firstName"));
        req.setAttribute("lastName", req.getParameter("lastName"));
        req.setAttribute("phoneNumber", req.getParameter("phoneNumber"));
        req.setAttribute("username", req.getParameter("username"));
    }
}