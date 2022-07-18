/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.user;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.google.GoogleDTO;
import sample.utils.DBUtils;
import sample.utils.DateUtils;

/**
 *
 * @author phuon
 */
public class UserDAO {

    private static final String LOGIN = "SELECT fullName, roleID, address, birthday, phone FROM tblUsers WHERE userID=? AND password=? AND status=1";
    private static final String CHECK_DUPLICATE = "SELECT status FROM tblUsers WHERE userID=?";
    private static final String CREATE = "INSERT INTO tblUsers (userID, fullName, password, roleID, address, birthday, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String CREATE_GOOGLE = "INSERT INTO tblUsers (userID, fullName, password, roleID, address, birthday, phone, email, registerEmail) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String CHECK_GOOGLE = "SELECT userID, fullName, roleID, address, birthday, phone, email FROM tblUsers WHERE registerEmail=?";
    private static final String GET_MAIL = "SELECT email FROM tblUsers WHERE userID=?";

    private final Logger logger = LogManager.getLogger(UserDAO.class);

    public String getEmail(String userID) throws SQLException {
        String email = null;

        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                pst = connection.prepareStatement(GET_MAIL);
                pst.setString(1, userID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    email = rs.getString("email");
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
        return email;
    }

    public UserDTO checkGoogle(GoogleDTO user) throws SQLException {
        UserDTO userDTO = null;

        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                pst = connection.prepareStatement(CHECK_GOOGLE);
                pst.setString(1, user.getEmail());
                rs = pst.executeQuery();
                if (rs.next()) {
                    String userID = rs.getString("userID");
                    String fullName = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    String address = rs.getString("address");
                    String birthday = rs.getString("birthday");
                    String phone = rs.getString("phone");
                    String email = rs.getString("email");
                    userDTO = new UserDTO(userID, fullName, "", roleID, address, birthday, phone, email);
                }
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

        return userDTO;
    }

    public boolean createGoogle(GoogleDTO user) throws SQLException {
        boolean check = false;

        Connection connection = null;
        PreparedStatement pst = null;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                pst = connection.prepareStatement(CREATE_GOOGLE);
                pst.setString(1, user.getId());
                pst.setString(2, user.getName());
                pst.setString(3, UUID.randomUUID().toString());
                pst.setString(4, "US");
                pst.setString(5, "");
                pst.setDate(6, new Date(DateUtils.now()));
                pst.setString(7, "");
                pst.setString(8, user.getEmail());
                pst.setString(9, user.getEmail());
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

    public boolean create(UserDTO user) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                pst = conn.prepareStatement(CREATE);
                pst.setString(1, user.getUserID());
                pst.setString(2, user.getFullName());
                pst.setString(3, user.getPassword());
                pst.setString(4, user.getRoleID());
                pst.setString(5, user.getAddress());
                pst.setString(6, user.getBirthday());
                pst.setString(7, user.getPhone());
                pst.setString(8, user.getEmail());
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

    public boolean checkDuplicate(String userID) throws SQLException {
        boolean check = false;
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        UserDTO user = null;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                pst = connection.prepareStatement(CHECK_DUPLICATE);
                pst.setString(1, userID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    check = true;
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

    public UserDTO login(String userID, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        UserDTO user = null;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                pst = connection.prepareStatement(LOGIN);
                pst.setString(1, userID);
                pst.setString(2, password);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String fullName = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    String address = rs.getString("address");
                    String birthday = rs.getString("birthday");
                    String phone = rs.getString("phone");
                    user = new UserDTO(userID, fullName, "", roleID, address, birthday, phone, "");
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
        return user;
    }
}
