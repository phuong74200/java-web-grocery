/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.product;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.controllers.SearchProductController;
import sample.orderDetail.OrderDetailDTO;
import sample.utils.DBUtils;
import sample.utils.ValidUtils;

/**
 *
 * @author phuon
 */
public class ProductDAO {

    private static final String ADD = "INSERT INTO tblProduct(productName, image, price, quantity, categoryID, importDate, usingDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SEARCH = "SELECT productID, productName, image, price, quantity, categoryID, importDate, usingDate FROM tblProduct WHERE productName LIKE ? AND status=1";
    private static final String GET_ALL = "SELECT productID, productName, image, price, quantity, categoryID, importDate, usingDate FROM tblProduct WHERE status=1";
    private static final String UPDATE = "UPDATE tblProduct SET  productName=?, image=?, price=?, quantity=?, categoryID=?, importDate=?, usingDate=? WHERE productID=?";
    private static final String GET_ID = "SELECT productID, productName, image, price, quantity, categoryID, importDate, usingDate FROM tblProduct WHERE productID=?";
    private static final String DELETE = "UPDATE tblProduct SET status=0 WHERE productID=?";
    private static final String CHECK_OUT = "UPDATE tblProduct SET quantity -= ? WHERE productID=?";

    private final Logger logger = LogManager.getLogger(ProductDAO.class);
    
    public boolean checkOut(OrderDetailDTO detail) throws SQLException {
        boolean check = false;

        Connection connection = null;
        PreparedStatement pst = null;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                pst = connection.prepareStatement(CHECK_OUT);
                pst.setInt(1, detail.getQuantity());
                pst.setInt(2, detail.getProductID());
                check = pst.executeUpdate() > 0;
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return check;
    }

    public boolean delete(int ID) throws SQLException {
        boolean check = false;

        Connection connection = null;
        PreparedStatement pst = null;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                pst = connection.prepareStatement(DELETE);
                pst.setInt(1, ID);
                check = pst.executeUpdate() > 0;
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return check;
    }

    public boolean update(ProductDTO product) throws SQLException {
        boolean check = false;

        Connection connection = null;
        PreparedStatement pst = null;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                pst = connection.prepareStatement(UPDATE);
                pst.setString(1, product.getProductName());
                pst.setString(2, product.getImage());
                pst.setFloat(3, product.getPrice());
                pst.setInt(4, product.getQuantity());
                pst.setInt(5, product.getCategoryID());
                pst.setDate(6, product.getImportDate());
                pst.setInt(7, product.getUsingDate());
                pst.setInt(8, Integer.parseInt(product.getProductID()));
                check = pst.executeUpdate() > 0;
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return check;
    }

    public ProductDTO getByID(int ID) throws SQLException {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        ProductDTO product = null;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                pst = connection.prepareStatement(GET_ID);
                pst.setInt(1, ID);
                rs = pst.executeQuery();

                if (rs.next()) {
                    String productID = rs.getString("productID");
                    String productName = rs.getString("productName");
                    String image = rs.getString("image");
                    float price = Float.parseFloat(rs.getString("price"));
                    int quantity = Integer.parseInt(rs.getString("quantity"));
                    int categoryID = Integer.parseInt(rs.getString("categoryID"));
                    Date importDate = ValidUtils.isValidDate(rs.getString("importDate"));
                    int usingDate = Integer.parseInt(rs.getString("usingDate"));
                    product = new ProductDTO(productID, productName, image, price, quantity, categoryID, importDate,
                            usingDate);
                }
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return product;
    }

    public List<ProductDTO> search(String keyword, boolean getOutDated) throws SQLException {
        List<ProductDTO> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                pst = connection.prepareStatement(SEARCH + (!getOutDated ? " AND DATEADD(DAY, usingDate, importDate) >= GETDATE() AND quantity > 0" : ""));
                pst.setString(1, "%" + keyword + "%");
                rs = pst.executeQuery();

                while (rs.next()) {
                    String productID = rs.getString("productID");
                    String productName = rs.getString("productName");
                    String image = rs.getString("image");
                    float price = Float.parseFloat(rs.getString("price"));
                    int quantity = Integer.parseInt(rs.getString("quantity"));
                    int categoryID = Integer.parseInt(rs.getString("categoryID"));
                    Date importDate = ValidUtils.isValidDate(rs.getString("importDate"));
                    int usingDate = Integer.parseInt(rs.getString("usingDate"));
                    ProductDTO product = new ProductDTO(productID, productName, image, price, quantity, categoryID, importDate,
                            usingDate);
                    list.add(product);
                }
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return list;
    }

    public List<ProductDTO> getAll(boolean getOutDated) throws SQLException {
        List<ProductDTO> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                pst = connection.prepareStatement(GET_ALL + (!getOutDated ? " AND DATEADD(DAY, usingDate, importDate) >= GETDATE() AND quantity > 0" : ""));
                rs = pst.executeQuery();

                while (rs.next()) {
                    String productID = rs.getString("productID");
                    String productName = rs.getString("productName");
                    String image = rs.getString("image");
                    float price = Float.parseFloat(rs.getString("price"));
                    int quantity = Integer.parseInt(rs.getString("quantity"));
                    int categoryID = Integer.parseInt(rs.getString("categoryID"));
                    Date importDate = ValidUtils.isValidDate(rs.getString("importDate"));
                    int usingDate = Integer.parseInt(rs.getString("usingDate"));
                    ProductDTO product = new ProductDTO(productID, productName, image, price, quantity, categoryID, importDate,
                            usingDate);
                    list.add(product);
                }
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return list;
    }

    public boolean create(ProductDTO product) throws SQLException {
        Connection connection = null;
        PreparedStatement pst = null;

        boolean success = false;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                pst = connection.prepareStatement(ADD);
                pst.setString(1, product.getProductName());
                pst.setString(2, product.getImage());
                pst.setFloat(3, product.getPrice());
                pst.setInt(4, product.getQuantity());
                pst.setInt(5, product.getCategoryID());
                pst.setDate(6, product.getImportDate());
                pst.setInt(7, product.getUsingDate());
                success = pst.executeUpdate() > 0;
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return success;
    }
}
