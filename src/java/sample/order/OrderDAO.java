/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.order;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.utils.DBUtils;

/**
 *
 * @author phuon
 */
public class OrderDAO {

    private static final String CREATE = "INSERT INTO tblOrder (orderDate, userID, status) VALUES (?, ?, 1)";
    private static final String GET_ORDER = "SELECT orderID FROM tblOrder WHERE userID=? AND status=1 AND checkOut=0";
    private static final String CHECK_OUT = "UPDATE tblOrder SET checkOut=1, total=? WHERE orderID=?";
    private static final String GET = "SELECT orderDate, userID, total FROM tblOrder WHERE orderID=? AND status=1";

    private final Logger logger = LogManager.getLogger(OrderDAO.class);

    public OrderDTO get(int orderID) throws SQLException {
        OrderDTO order = null;

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                pst = conn.prepareStatement(GET);
                pst.setInt(1, orderID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    Date orderDate = rs.getDate("orderDate");
                    String userID = rs.getString("userID");
                    float total = rs.getFloat("total");
                    order = new OrderDTO(orderID, orderDate, userID, total);
                }
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
        return order;
    }

    public boolean create(String userID, Date orderDate) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                pst = conn.prepareStatement(CREATE);
                pst.setDate(1, orderDate);
                pst.setString(2, userID);
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

    public boolean checkOut(int orderID, float total) throws SQLException {
        boolean check = false;

        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                pst = conn.prepareStatement(CHECK_OUT);
                pst.setFloat(1, total);
                pst.setInt(2, orderID);
                check = pst.executeUpdate() > 1;
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

    public int getOrder(String userID) throws SQLException {
        int orderID = -1;

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                pst = conn.prepareStatement(GET_ORDER);
                pst.setString(1, userID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    orderID = rs.getInt("orderID");
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
            if (conn != null) {
                conn.close();
            }
        }

        return orderID;
    }
}
