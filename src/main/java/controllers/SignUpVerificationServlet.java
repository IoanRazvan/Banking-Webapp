package controllers;

import business.User;
import mappers.RequestToUserMapper;
import repository.UserRepository;
import repository.UserRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

@WebServlet("/signUpVerification")
public class SignUpVerificationServlet extends HttpServlet {
    private UserRepository userRepository = new UserRepositoryImpl();
    private RequestToUserMapper mapper = RequestToUserMapper.getMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User newUser = mapper.map(req);
        String view = "/WEB-INF/JSPs/makeAccount.jsp";

        try {
            userRepository.save(newUser);
            req.getSession().setAttribute("user", newUser);
        } catch (ConstraintViolationException e) {
            ConstraintViolation<?> firstConstraintViolation = e.getConstraintViolations().iterator().next();
            req.setAttribute("message", firstConstraintViolation.getMessage());
            req.setAttribute("user", newUser);
            view = "/WEB-INF/JSPs/signUp.jsp";
        }
        getServletContext().getRequestDispatcher(view).forward(req, resp);
    }
}