/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.order.OrderDAO;
import sample.orderDetail.OrderDetailDAO;
import sample.orderDetail.OrderDetailDTO;
import sample.product.ProductDAO;
import sample.product.ProductDTO;
import sample.user.UserDAO;
import sample.utils.CaptchaUtils;
import sample.utils.MailUtils;



/**
 *
 * @author phuon
 */
@WebServlet(name = "CheckOutController", urlPatterns = {"/CheckOutController"})
public class CheckOutController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String ERROR = "GetCartController";
    private static final String SUCCESS = "GetCartController";

    private final Logger logger = LogManager.getLogger(CheckOutController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = ERROR;

        OrderDAO orderDAO = new OrderDAO();
        OrderDetailDAO detailDAO = new OrderDetailDAO();
        ProductDAO productDAO = new ProductDAO();
        UserDAO userDAO = new UserDAO();

        try {
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

            boolean isVerify = CaptchaUtils.veriry(gRecaptchaResponse);

            if (isVerify) {
                int orderID = Integer.parseInt(request.getParameter("orderID"));

                List<OrderDetailDTO> list = detailDAO.getByOrderID(orderID);

                float total = 0;

                List<OrderDetailDTO> removedItem = new ArrayList<>();

                String orderTable = "";

                for (OrderDetailDTO detail : list) {
                    int productID = detail.getProductID();
                    ProductDTO product = productDAO.getByID(productID);
                    if (product.getQuantity() < detail.getQuantity()) {
                        removedItem.add(detail);
                    } else {
                        orderTable += "<tr><td>" + product.getProductName() + "</td><td>$" + detail.getPrice() + "</td><td>" + detail.getQuantity() + "</td><td>$" + detail.getQuantity() * detail.getPrice() + "</td></tr>";
                        total += detail.getQuantity() * detail.getPrice();
                        productDAO.checkOut(detail);
                    }
                    detailDAO.delete(detail.getDetailID());
                }

                orderDAO.checkOut(orderID, total);

                request.setAttribute("OUT_STOCK", removedItem);

                String mailHTML = "<html><h1>Thank you for visiting our Grocery</h1><table border='1px'><tr><th>Name</th><th>Price</th><th>Quantity</th><th>Total</th></tr>"
                        + orderTable
                        + "</table>"
                        + "<p>Here is your order</p>"
                        + "</html>";

                url = SUCCESS;

                String email = userDAO.getEmail(orderDAO.get(orderID).getUserID());

                if (email != null && !email.equals("")) {
                    new MailUtils(mailHTML, email).start();
                    logger.info("Mail send to: " + email);
                }
            } else {
                request.setAttribute("INVALID_CAPTCHA", "Captcha is invalid. Please try again");
                url = ERROR;
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
