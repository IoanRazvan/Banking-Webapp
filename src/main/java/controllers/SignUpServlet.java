package controllers;

import business.User;
import business.UserInformation;
import encoders.NoEncryptionEncoder;
import encoders.PasswordEncoder;
import mappers.RequestToObjectMapper;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import util.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

@WebServlet("/signUp")
@MultipartConfig
public class SignUpServlet extends HttpServlet {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final RequestToObjectMapper<UserInformation> mapper = RequestToObjectMapper.from(UserInformation.class);
    private final PasswordEncoder encoder = new NoEncryptionEncoder();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserInformation userInformation = mapper.map(req);
        User newUser = new User(userInformation);

        try {
            newUser.setPassword(encoder.encode(newUser.getPassword()));
            userRepository.save(newUser);
            req.getSession().setAttribute("user", newUser);
            resp.sendRedirect("openAccount");
        } catch (ConstraintViolationException e) {
            ConstraintViolation<?> firstConstraintViolation = e.getConstraintViolations().iterator().next();
            HttpUtils.sendResponse(resp, firstConstraintViolation.getMessage(), HttpServletResponse.SC_CONFLICT);
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null)
            getServletContext().getRequestDispatcher("/WEB-INF/Views/signUp.html").forward(req, resp);
        else
            resp.sendRedirect("editUser");
    }
}