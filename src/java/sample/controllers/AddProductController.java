/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.category.CategoryDAO;
import sample.product.ProductDAO;
import sample.product.ProductDTO;
import sample.product.ProductError;
import sample.utils.ValidUtils;

/**
 *
 * @author phuon
 */
@MultipartConfig
@WebServlet(name = "AddProductController", urlPatterns = {"/AddProductController"})
public class AddProductController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String ERROR = "productAdd.jsp";
    private static final String SUCCESS = "productAdd.jsp";

    private final Logger logger = LogManager.getLogger(AddProductController.class);

    
    /* 
    
        if server.xml in tomcat %basePath% have been configured set SAVE_MODE = 1
        otherwise set SAVE_MODE = 0
    
        SAVE_MODE = 0 will save image into project path so u can send it to other
        SAVE_MODE = 1 will save image into D:\GroceryImages\* so you need to configure
    
        if SAVE_MODE = 1. Add these line into tomcat %basePath% inside <Host/>
            <Context docBase="D:\GroceryImages\" path="Grocery/images" />
    
    */
    private static final int SAVE_MODE = 0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = ERROR;

        ProductError productError = new ProductError();
        CategoryDAO categoryDAO = new CategoryDAO();
        ProductDAO productDAO = new ProductDAO();
        ProductDTO validDTO = new ProductDTO();

        try {
            String productName = request.getParameter("productName");
            String category = request.getParameter("category");
            String importDate = request.getParameter("importDate");
            String usingDate = request.getParameter("usingDate");
            String price = request.getParameter("price");
            String quantity = request.getParameter("quantity");

            boolean isValid = true;

            log(categoryDAO.getIDByName(category) + "");

            Part filePart = request.getPart("file");
            try (InputStream fileContent = filePart.getInputStream()) {
                String hashedFileName = Integer.toString(Paths.get(filePart.getSubmittedFileName()).hashCode());

                if (productName.length() == 0) {
                    productError.setProductNameError("[name not valid]");
                    isValid = false;
                } else {
                    validDTO.setProductName(productName);
                }

                if (categoryDAO.getIDByName(category) < 0) {
                    productError.setCategoryIDError("[category not valid]");
                    isValid = false;
                } else {
                    validDTO.setCategoryID(categoryDAO.getIDByName(category));
                }

                if (ValidUtils.isValidDate(importDate) == null) {
                    productError.setImportDateError("[import date not valid]");
                    isValid = false;
                } else {
                    validDTO.setImportDate(ValidUtils.isValidDate(importDate));
                }

                if (!ValidUtils.isValidInt(usingDate)) {
                    productError.setUsingDateError("[using date not valid]");
                    isValid = false;
                } else {
                    validDTO.setUsingDate(Integer.parseInt(usingDate));
                }

                if (!ValidUtils.isValidFloat(price)) {
                    productError.setPriceError("[price not valid]");
                    isValid = false;
                } else {
                    validDTO.setPrice(Float.parseFloat(price));
                }

                if (!ValidUtils.isValidInt(quantity)) {
                    productError.setQuantityError("[quantity not valid]");
                    isValid = false;
                } else {
                    validDTO.setQuantity(Integer.parseInt(quantity));
                }

                if (isValid) {
                    String savePath;
                    String relativePath;
                    
                    if (SAVE_MODE == 0) {
                        ServletContext context = request.getServletContext();
                        savePath = context.getRealPath("/") + "\\assets\\images\\";
                        relativePath = "./assets/images/";
                    } else {
                        savePath = "D:/GroceryImages/";
                        relativePath = "./images/";
                    }

                    File targetFile = new File(savePath + hashedFileName + ".png");
                    FileUtils.copyInputStreamToFile(fileContent, targetFile);
                    ProductDTO product = new ProductDTO("", productName, relativePath + hashedFileName + ".png",
                            Float.parseFloat(price), Integer.parseInt(quantity), categoryDAO.getIDByName(category),
                            ValidUtils.isValidDate(importDate), Integer.parseInt(usingDate));
                    boolean checkCreate = productDAO.create(product);

                    if (checkCreate) {
                        url = SUCCESS;
                    }
                } else {
                    request.setAttribute("ERROR", productError);
                    request.setAttribute("VALID", validDTO);
                }
            }
        } catch (IOException | NumberFormatException | SQLException | ServletException e) {
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
