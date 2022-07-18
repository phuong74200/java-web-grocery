/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.user.UserDAO;
import sample.user.UserDTO;
import sample.user.UserError;
import sample.utils.DateUtils;
import sample.utils.ValidUtils;

/**
 *
 * @author phuon
 */
@WebServlet(name = "SignUpController", urlPatterns = {"/SignUpController"})
public class SignUpController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String ERROR = "signUp.jsp";
    private static final String SUCCESS = "login.jsp";

    private final Logger logger = LogManager.getLogger(SignUpController.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;

        UserError userError = new UserError();
        UserDTO validDTO = new UserDTO();

        try {
            String userID = request.getParameter("userID");
            String fullName = request.getParameter("fullname");
            String password = request.getParameter("password");
            String rePassword = request.getParameter("rePassword");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String birthday = request.getParameter("birthday");
            String email = request.getParameter("email");

            UserDAO dao = new UserDAO();

            boolean checkValidation = true;
            boolean checkDuplicate = dao.checkDuplicate(userID);

            if (checkDuplicate) {
                userError.setUserIDError("have exist");
                validDTO.setUserID("");
            } else {
                validDTO.setUserID(userID);
            }

            if (!userID.matches("[\\w\\d\\_\\.]{5,20}")) {
                userError.setUserIDError("is not valid");
                validDTO.setUserID("");
                checkValidation = false;
            } else {
                validDTO.setUserID(userID);
            }
            
            if (userID.contains(" ")) {
                userError.setUserIDError("is containing spaces");
                validDTO.setUserID("");
                checkValidation = false;
            } else {
                validDTO.setUserID(userID);
            }

            if (userID.length() < 5 || userID.length() > 20) {
                userError.setUserIDError("length must be between [5, 20]");
                validDTO.setUserID("");
                checkValidation = false;
            } else {
                validDTO.setUserID(userID);
            }

            if (!fullName.matches("[\\w\\s]{5,40}")) {
                userError.setFullNameError("is not valid");
                validDTO.setFullName("");
                checkValidation = false;
            } else {
                validDTO.setFullName(fullName);
            }

            if (!password.equals(rePassword)) {
                userError.setRePasswordError("is not matches");
                checkValidation = false;
            }

            if (password.trim().length() < 5) {
                userError.setRePasswordError("length must be at least 5 characters");
                checkValidation = false;
            }

            if (email.trim().length() > 0 && !ValidUtils.isValidEmail(email)) {
                userError.setEmailError("is not valid");
                checkValidation = false;
            } else {
                validDTO.setEmail(email);
            }

            if (!phone.matches("[\\d]{10}")) {
                userError.setPhoneError("must 10 digits length");
                checkValidation = false;
            } else {
                validDTO.setPhone(phone);
            }

            if (ValidUtils.isValidDate(birthday) == null) {
                userError.setBirthdayError("is not valid");
                checkValidation = false;
            } else if (ValidUtils.isValidDate(birthday).getTime() > DateUtils.now()) {
                userError.setBirthdayError("is not valid");
                checkValidation = false;
            } else {
                java.sql.Date validDate = ValidUtils.isValidDate(birthday);
                validDTO.setBirthday(DateUtils.toHTMLDate(validDate));
            }

            if (checkValidation) {
                UserDTO user = new UserDTO(userID, fullName, password, "US", address, birthday, phone, email);
                boolean checkCreate = dao.create(user);
                if (checkCreate) {
                    url = SUCCESS;
                }
            } else {
                request.setAttribute("USER_ERROR", userError);
                request.setAttribute("VALID", validDTO);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
