/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupassignment.pkgnew;

/**
 *
 * @author user
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SetupManager {

    public void addSupplier(String code, String name, String contact) {
        String sql = "INSERT INTO Suppliers (supplierCode, supplierName, contactInfo) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, code);
            stmt.setString(2, name);
            stmt.setString(3, contact);
            stmt.executeUpdate();
            System.out.println("供应商添加成功！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addHospital(String code, String name, String location) {
        String sql = "INSERT INTO Hospitals (hospitalCode, hospitalName, location) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, code);
            stmt.setString(2, name);
            stmt.setString(3, location);
            stmt.executeUpdate();
            System.out.println("医院添加成功！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addItem(String itemCode, String itemName, String supplierCode, int quantityInBoxes) {
        String sql = "INSERT INTO Items (itemCode, itemName, supplierCode, quantityInBoxes) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, itemCode);
            stmt.setString(2, itemName);
            stmt.setString(3, supplierCode);
            stmt.setInt(4, quantityInBoxes);
            stmt.executeUpdate();
            System.out.println("物品添加成功！");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
