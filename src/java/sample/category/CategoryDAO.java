/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sample.utils.DBUtils;

/**
 *
 * @author phuon
 */
public class CategoryDAO {

    private static final String GET_ID = "SELECT categoryID FROM tblCategory WHERE categoryName=? AND status=1";
    private static final String GET_NAME = "SELECT categoryName FROM tblCategory WHERE categoryID=? AND status=1";
    private static final String GET_LIST = "SELECT categoryName FROM tblCategory WHERE status=1";

    public int getIDByName(String categoryName) throws SQLException {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String categoryID = "-1";

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                pst = connection.prepareStatement(GET_ID);
                pst.setString(1, categoryName);
                rs = pst.executeQuery();
                if (rs.next()) {
                    categoryID = rs.getString("categoryID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        return Integer.parseInt(categoryID);
    }
    
    public String getNameByID(int categoryID) throws SQLException {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String categoryName = null;

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                pst = connection.prepareStatement(GET_NAME);
                pst.setInt(1, categoryID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    categoryName = rs.getString("categoryName");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        return categoryName;
    }

    public List<CategoryDTO> getAll() throws SQLException {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        List<CategoryDTO> list = new ArrayList<CategoryDTO>();

        try {
            connection = DBUtils.getConnection();
            if (connection != null) {
                pst = connection.prepareStatement(GET_LIST);
                rs = pst.executeQuery();
                while (rs.next()) {
                    String categoryName = rs.getString("categoryName");
                    list.add(new CategoryDTO("", categoryName));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
}
