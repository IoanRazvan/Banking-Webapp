package controllers;

import business.User;
import controllers.exceptions.IncorrectPasswordException;
import controllers.exceptions.UserNotFoundException;
import encoders.NoEncryptionEncoder;
import encoders.PasswordEncoder;
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
import java.util.Optional;

@WebServlet("/logIn")
@MultipartConfig
public class LogInServlet extends HttpServlet {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final PasswordEncoder encoder = new NoEncryptionEncoder();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User u = authenticatedUser(req);
            req.getSession().setAttribute("user", u);
            if (u.getMainAccount() == null)
                resp.sendRedirect("openAccount");
            else
                resp.sendRedirect("editUser");
        } catch (IncorrectPasswordException | UserNotFoundException e) {
            HttpUtils.sendResponse(resp, e.getMessage(), HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
    
    private User authenticatedUser(HttpServletRequest req) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Optional<User> user = userRepository.findByUsername(username);
        user.ifPresentOrElse(u -> {
            if (!encoder.matches(u.getPassword(), password))
                throw new IncorrectPasswordException("wrong credentials");
        }, () -> { throw new UserNotFoundException("wrong credentials"); });
        return user.get();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null)
            getServletContext().getRequestDispatcher("/WEB-INF/Views/logIn.html").forward(req, resp);
        else
            resp.sendRedirect("editUser");
    }
}
