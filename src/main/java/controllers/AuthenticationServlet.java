package controllers;

import business.User;
import data.TransactionDB;
import data.UserDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/authenticate")
public class AuthenticationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if (session.getAttribute("menuOptions") == null) {
            String[] menuOptions = {"Add Another Account", "Transfer", "Manage Accounts", "Notifications", "Edit User", "Statistics","Log Out"};
            session.setAttribute("menuOptions", menuOptions);
        }

        String action = req.getParameter("action");
        String url;

        if (action.equals("signIn")) {
            boolean successfulSignIn = validateSignIn(req, resp);
            if (!successfulSignIn)
                url = "/WEB-INF/JSPs/signIn.jsp";
            else{
                req.setAttribute("targetTransaction", TransactionDB.getTransactionsByTargetAccountOwner((User) req.getSession().getAttribute("user")));
                req.setAttribute("sourceTransaction", TransactionDB.getTransactionsBySourceAccountOwner((User) req.getSession().getAttribute("user")));
                url = "/WEB-INF/JSPs/statistics.jsp";
            }
        } else {
            boolean successfulSignUp = validateSignUp(req, resp);
            if (!successfulSignUp)
                url = "/WEB-INF/JSPs/signUp.jsp";
            else
                url = "/WEB-INF/JSPs/makeAccount.jsp";
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

    private boolean validateSignUp(HttpServletRequest req, HttpServletResponse resp) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phoneNumber = req.getParameter("phoneNumber");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String message = null;
        boolean validDataEntered = true;
        User u = new User(username, firstName, lastName, phoneNumber, password);

        if (UserDB.getUserByUsername(username) != null) {
            message = "Username already taken";
            validDataEntered = false;
        } else if (UserDB.getUserByPhoneNumber(phoneNumber) != null) {
            message = "This phone number is already registered";
            validDataEntered = false;
        }

        if (validDataEntered) {
            UserDB.insert(u);
            u = UserDB.getUserByUsername(u.getUsername());
            req.getSession().setAttribute("user", u);
            req.setAttribute("isMainAccount", true);
        } else {
            req.setAttribute("user", u);
            req.setAttribute("message", message);
        }

        return validDataEntered;
    }
}