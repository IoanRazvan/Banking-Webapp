package controllers;

import business.Account;
import business.User;
import mappers.RequestToObjectMapper;
import repository.AccountRepository;
import repository.AccountRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import util.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/openAccount")
@MultipartConfig // https://stackoverflow.com/questions/10292382/html5-formdata-returns-null-in-java-servlet-request-getparameter
public class OpenAccountServlet extends HttpServlet {
    private final RequestToObjectMapper<Account> mapper = RequestToObjectMapper.from(Account.class);
    private final AccountRepository accountRepo = new AccountRepositoryImpl();
    private final UserRepository userRepo = new UserRepositoryImpl();
    private static final String SUCCESS_MESSAGE = "You're account was successfully created";
    private static final String ERROR_MESSAGE = "A problem was encountered while trying to create your account";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account newAccount = mapper.map(req);
        User u = (User)req.getSession().getAttribute("user");
        newAccount.setOwner(u);
        try {
            accountRepo.save(newAccount);
            if (u.getMainAccount() == null) {
                u.setMainAccount(newAccount);
                userRepo.update(u);
                resp.sendRedirect("editUser");
            }
            HttpUtils.sendResponse(resp, SUCCESS_MESSAGE, HttpServletResponse.SC_OK);
        } catch (Exception ex) {
            HttpUtils.sendResponse(resp, ERROR_MESSAGE, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null)
            resp.sendRedirect("logIn");
        else
            getServletContext().getRequestDispatcher("/WEB-INF/Views/openAccount.jsp").forward(req, resp);
    }
}
