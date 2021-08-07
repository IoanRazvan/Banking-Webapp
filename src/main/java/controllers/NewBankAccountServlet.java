package controllers;

import business.Account;
import business.User;
import mappers.RequestToObjectMapper;
import repository.AccountRepository;
import repository.AccountRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/insertNewAccount")
public class NewBankAccountServlet extends HttpServlet {
    RequestToObjectMapper<Account> mapper = RequestToObjectMapper.from(Account.class);
    AccountRepository accountRepo = new AccountRepositoryImpl();
    UserRepository userRepo = new UserRepositoryImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Account newAccount = mapper.map(req);
        User u = (User)req.getSession().getAttribute("user");
        newAccount.setOwner(u);
        String view = "/WEB-INF/JSPs/statistics.jsp";
        accountRepo.save(newAccount);
        if (u.getMainAccount() == null) {
            u.setMainAccount(newAccount);
            userRepo.update(u);
        }
        getServletContext().getRequestDispatcher(view).forward(req, res);
    }
}
