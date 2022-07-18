/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.order.OrderDAO;
import sample.orderDetail.OrderDetailDAO;
import sample.orderDetail.OrderDetailDTO;
import sample.product.ProductDAO;
import sample.product.ProductDTO;
import sample.user.UserDTO;
import sample.utils.DateUtils;
import sample.utils.ValidUtils;

/**
 *
 * @author phuon
 */
@WebServlet(name = "AddItemController", urlPatterns = {"/AddItemController"})
public class AddItemController extends HttpServlet {

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
    private static final String SUCCESS = "landing.jsp";

    private final Logger logger = LogManager.getLogger(AddItemController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;

        ProductDAO productDAO = new ProductDAO();
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        OrderDAO orderDAO = new OrderDAO();

        try {
            HttpSession session = request.getSession();

            String productID = request.getParameter("productID");

            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");

            if (loginUser != null) {
                String userID = loginUser.getUserID();
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                if (ValidUtils.isValidInt(productID)) {
                    ProductDTO product = productDAO.getByID(Integer.parseInt(productID));

                    int orderID = orderDAO.getOrder(userID);

                    if (orderID < 0) {
                        orderDAO.create(userID, new Date(DateUtils.now()));
                        orderID = orderDAO.getOrder(userID);
                    }

                    if (quantity > 0) {
                        OrderDetailDTO detail = new OrderDetailDTO();
                        detail.setOrderID(orderID);
                        detail.setPrice(product.getPrice());
                        detail.setProductID(Integer.parseInt(product.getProductID()));
                        detail.setQuantity(quantity);

                        OrderDetailDTO checkDetail = orderDetailDAO.get(detail);

                        if (checkDetail == null) {
                            orderDetailDAO.create(detail);
                        } else {
                            if (!orderDetailDAO.checkStatus(detail.getDetailID())) {
                                orderDetailDAO.enable(detail.getDetailID());
                            }

                            int newQuantity = checkDetail.getQuantity() + quantity;
                            checkDetail.setQuantity(newQuantity);
                            orderDetailDAO.update(checkDetail);
                        }
                    }
                }

                url = SUCCESS;
            }
        } catch (NumberFormatException | SQLException e) {
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
