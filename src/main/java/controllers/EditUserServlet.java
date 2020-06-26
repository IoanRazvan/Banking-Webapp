package controllers;

import business.User;
import data.UserDB;
import util.Util;
import util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.AbstractMap;

@WebServlet("/editUser")
public class EditUserServlet extends HttpServlet {
    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String selectedField = req.getParameter("selectedField");
        System.out.println(selectedField);
        HttpSession session = req.getSession();
        User activeUser = (User) session.getAttribute("user");
        String newValue = req.getParameter("newValue");
        ArrayList<AbstractMap.SimpleEntry<String, String>> fieldValuePairs = (ArrayList<AbstractMap.SimpleEntry<String, String>>) session.getAttribute("fieldValuePairs");
        String message = "";
        boolean modifiedUser = false;
        int index;

        switch (selectedField) {
            case "Username":
                if (UserDB.getUserByUsername(newValue) != null){
                    message = "Username already claimed!";
                    req.setAttribute("selectedField", selectedField);
                } else {
                    index = Util.indexOf(fieldValuePairs, selectedField);
                    fieldValuePairs.set(index, new AbstractMap.SimpleEntry<>(selectedField, newValue));
                    activeUser.setUsername(newValue);
                    modifiedUser = true;
                }
                break;
            case "First Name":
                index = Util.indexOf(fieldValuePairs, selectedField);
                fieldValuePairs.set(index, new  AbstractMap.SimpleEntry<>(selectedField, newValue));
                activeUser.setFirstName(newValue);
                modifiedUser = true;
                break;
            case "Last Name":
                index = Util.indexOf(fieldValuePairs, selectedField);
                fieldValuePairs.set(index, new  AbstractMap.SimpleEntry<>(selectedField, newValue));
                activeUser.setLastName(newValue);
                modifiedUser = true;
                break;
            case "Phone Number":
                if (!ValidationUtil.validPhoneNumber(newValue)){
                    req.setAttribute("selectedField", selectedField);
                    message = "Invalid phone number!";
                } else if (UserDB.getUserByPhoneNumber(newValue) != null) {
                    req.setAttribute("selectedField", selectedField);
                    message = "Phone number already claimed!";
                }
                else {
                    index = Util.indexOf(fieldValuePairs, selectedField);
                    fieldValuePairs.set(index, new  AbstractMap.SimpleEntry<>(selectedField, newValue));
                    activeUser.setPhoneNumber(newValue);
                    modifiedUser = true;
                }
                break;
            case "Password":
                activeUser.setPassword(newValue);
                modifiedUser = true;
                break;
        }
        req.setAttribute("message", message);
        System.out.println("Aici");
        if (modifiedUser)
            UserDB.update(activeUser);
        getServletContext().getRequestDispatcher("/WEB-INF/JSPs/editUser.jsp").forward(req, resp);
    }
}
