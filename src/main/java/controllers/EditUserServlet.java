package controllers;

import business.User;
import business.UserInformation;
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

@WebServlet("/editUser")
@MultipartConfig
public class EditUserServlet extends HttpServlet {
    private final RequestToObjectMapper<UserInformation> mapper =  RequestToObjectMapper.from(UserInformation.class);
    private final UserRepository userRepo = new UserRepositoryImpl();
    private final static String SUCCESS_MESSAGE = "Profile information updated successfully";
    private final static String ERROR_MESSAGE = "Your profile was not updated due to an unknown error";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null)
            resp.sendRedirect("logIn");
        else
            getServletContext().getRequestDispatcher("/WEB-INF/Views/editUser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserInformation newUserInformation = mapper.map(req);
        User sessionHolder = (User)req.getSession().getAttribute("user");
        UserInformation oldUserInformation = sessionHolder.updateUserInfo(newUserInformation);
        sessionHolder.setPassword(oldUserInformation.getPassword());
        try {
            userRepo.update(sessionHolder);
            HttpUtils.sendResponse(resp, SUCCESS_MESSAGE, HttpServletResponse.SC_OK);
        } catch (ConstraintViolationException ex) {
            sessionHolder.updateUserInfo(oldUserInformation);
            ConstraintViolation<?> firstConstraintViolation = ex.getConstraintViolations().iterator().next();
            HttpUtils.sendResponse(resp, firstConstraintViolation.getMessage(), HttpServletResponse.SC_CONFLICT);
        } catch (Exception ex) {
            sessionHolder.updateUserInfo(oldUserInformation);
            HttpUtils.sendResponse(resp, ERROR_MESSAGE, HttpServletResponse.SC_CONFLICT);
        }
    }
}