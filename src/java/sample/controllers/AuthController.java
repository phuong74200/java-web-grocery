/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import sample.env.env;
import sample.google.GoogleDTO;
import sample.user.UserDAO;
import sample.user.UserDTO;
import sample.utils.GoogleUtiils;

/**
 *
 * @author phuon
 */
@WebServlet(name = "AuthController", urlPatterns = {"/AuthController"})
public class AuthController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String ERROR = "login.jsp";
    private static final String AD_PAGE = "productAdd.jsp";
    private static final String US_PAGE = "landing.jsp";
    private static final String US = "US";
    private static final String AD = "AD";
    
    private final org.apache.logging.log4j.Logger logger = LogManager.getLogger(AuthController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");

        String url = ERROR;

        try {
            UserDAO dao = new UserDAO();

            String urlParameters = "code="
                    + request.getParameter("code")
                    + "&client_id=" + env.OAUTH_CLIENT_ID
                    + "&client_secret=" + env.OAUTH_SECRET_KEY
                    + "&redirect_uri=" + env.REDIRECT_URI
                    + "&grant_type=authorization_code";

            GoogleDTO user = GoogleUtiils.getInfo(urlParameters);

            logger.info(user.toString());
            
            UserDTO checkUser = dao.checkGoogle(user);

            HttpSession session = request.getSession();

            if (checkUser == null) {
                dao.createGoogle(user);
                checkUser = dao.checkGoogle(user);
            }

            session.setAttribute("LOGIN_USER", checkUser);

            if(AD.equals(checkUser.getRoleID())) {
                url = AD_PAGE;
            } else {
                url = US_PAGE;
            }
        } catch (IOException | SQLException e) {
            logger.error(e);
        } finally {
            response.sendRedirect(url);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
