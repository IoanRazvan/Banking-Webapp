package controllers;

import business.User;
import controllers.exceptions.IncorrectPasswordException;
import controllers.exceptions.UserNotFoundException;
import encoders.NoEncryptionEncoder;
import encoders.PasswordEncoder;
import repository.UserRepository;
import repository.UserRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/logInVerification")
public class LogInVerificationServlet extends HttpServlet {
    private UserRepository userRepository = new UserRepositoryImpl();
    private PasswordEncoder encoder = new NoEncryptionEncoder();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String view = "/WEB-INF/JSPs/menu.jsp";
        try {
            User u = retrieveUser(req);
            req.getSession().setAttribute("user", u);
        } catch (IncorrectPasswordException | UserNotFoundException e) {
            view = "/WEB-INF/JSPs/logIn.jsp";
            req.setAttribute("username", req.getParameter("username"));
            req.setAttribute("message", e.getMessage());
        }
        getServletContext().getRequestDispatcher(view).forward(req, resp);
    }
    
    private User retrieveUser(HttpServletRequest req) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Optional<User> user = userRepository.findByUsername(username);
        user.ifPresentOrElse(u -> {
            if (!encoder.matches(u.getPassword(), password))
                throw new IncorrectPasswordException("wrong credentials");
        }, () -> { throw new UserNotFoundException("wrong credentials"); });
        return user.get();
    }
}
