/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package groupassignment.pkgnew;

/**
 *
 * @author user
 */

import java.sql.*;

public class UserManager {

    public void addUser(String userID, String name, String password, String userType, String email) {
        String sql = "INSERT INTO Users (userID, name, password, userType, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userID);
            stmt.setString(2, name);
            stmt.setString(3, password);
            stmt.setString(4, userType);
            stmt.setString(5, email);
            stmt.executeUpdate();
            System.out.println("✅ 用户添加成功！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchUser(String userID) {
        String sql = "SELECT * FROM Users WHERE userID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("🔍 用户信息：");
                System.out.println("ID: " + rs.getString("userID"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Type: " + rs.getString("userType"));
                System.out.println("Email: " + rs.getString("email"));
            } else {
                System.out.println("❌ 未找到该用户。");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String userID) {
        String sql = "DELETE FROM Users WHERE userID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userID);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("🗑️ 用户已删除！");
            } else {
                System.out.println("❌ 用户不存在。");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}