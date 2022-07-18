/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.orderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.order.OrderDAO;
import sample.product.ProductDAO;
import sample.product.ProductDTO;
import sample.utils.DBUtils;

/**
 *
 * @author phuon
 */
public class OrderDetailDAO {

    private static final String CREATE = "INSERT INTO tblOrderDetail (price, quantity, orderID, productID, status) VALUES (?, ?, ?, ?, 1)";
    private static final String GET = "SELECT detailID, quantity, price, productID, orderID FROM tblOrderDetail WHERE productID=? AND orderID=? AND status=1";
    private static final String UPDATE = "UPDATE tblOrderDetail SET quantity=? WHERE detailID=?";
    private static final String GET_BY_ORDER = "SELECT detailID, quantity, price, productID, orderID FROM tblOrderDetail WHERE orderID=? AND status=1";
    private static final String DELETE = "UPDATE tblOrderDetail SET status=0 WHERE detailID=?";
    private static final String CHECK_STATUS = "SELECT status FROM tblOrderDetail WHERE detailID=?";
    private static final String ENABLE = "UPDATE tblOrderDetail SET status=1 WHERE detailID=?";

    private final Logger logger = LogManager.getLogger(OrderDetailDAO.class);
    
    public boolean delete(int detailID) throws SQLException {
        boolean check = false;

        Connection connection = null;
        PreparedStatement pst = null;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                pst = connection.prepareStatement(DELETE);
                pst.setInt(1, detailID);
                check = pst.executeUpdate() > 1;
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

    public boolean enable(int detailID) throws SQLException {
        Connection connection = null;
        PreparedStatement pst = null;

        boolean check = false;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                pst = connection.prepareStatement(ENABLE);
                pst.setInt(1, detailID);
                check = pst.executeUpdate() > 1;
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

    public boolean checkStatus(int detailID) throws SQLException {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        boolean check = false;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                pst = connection.prepareStatement(CHECK_STATUS);
                pst.setInt(1, detailID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    check = rs.getBoolean("status");
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

        return check;
    }

    public List<OrderDetailDTO> getByOrderID(int orderID) throws SQLException {
        List<OrderDetailDTO> list = new ArrayList<>();

        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                pst = connection.prepareStatement(GET_BY_ORDER);
                pst.setInt(1, orderID);
                rs = pst.executeQuery();
                while (rs.next()) {
                    int detailID = rs.getInt("detailID");
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");
                    int productID = rs.getInt("productID");
                    OrderDetailDTO detail = new OrderDetailDTO(detailID, price, quantity, orderID, productID);
                    list.add(detail);
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

    public OrderDetailDTO get(OrderDetailDTO detailDTO) throws SQLException {
        OrderDetailDTO detail = null;

        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                pst = connection.prepareStatement(GET);
                pst.setInt(1, detailDTO.getProductID());
                pst.setInt(2, detailDTO.getOrderID());
                rs = pst.executeQuery();

                if (rs.next()) {
                    int detailID = rs.getInt("detailID");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    int orderID = rs.getInt("orderID");
                    int productID = rs.getInt("productID");

                    detail = new OrderDetailDTO(detailID, price, quantity, orderID, productID);
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
        return detail;
    }

    public boolean create(OrderDetailDTO detail) throws SQLException {
        boolean check = false;

        ProductDAO productDAO = new ProductDAO();

        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                pst = conn.prepareStatement(CREATE);
                pst.setFloat(1, detail.getPrice());
                pst.setInt(2, detail.getQuantity());
                pst.setInt(3, detail.getOrderID());
                pst.setInt(4, detail.getProductID());
                check = pst.executeUpdate() > 0;
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return check;
    }

    public boolean update(OrderDetailDTO detail) throws SQLException {
        boolean check = false;

        ProductDAO productDAO = new ProductDAO();

        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                pst = conn.prepareStatement(UPDATE);
                pst.setFloat(1, detail.getQuantity());
                pst.setInt(2, detail.getDetailID());
                check = pst.executeUpdate() > 0;
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return check;
    }
}
