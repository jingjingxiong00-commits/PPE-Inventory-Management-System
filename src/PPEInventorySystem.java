/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package groupassignment.pkgnew;

/**
 *
 * @author user
 */
import javax.swing.*; 
import java.sql.*; 
 
public class PPEInventorySystem {
    
   private static final String DB_URL = "jdbc:mysql://localhost:3306/ppe_inventory?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "your_password";
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()  -> {
            try {
                new LoginFrame();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,  "Error: " + e.getMessage()); 
            }
        });
    }
 
    public static Connection connectToDatabase() {
        try {
            return DriverManager.getConnection(DB_URL,  DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,  "Database connection failed: " + e.getMessage()); 
            return null;
        }
    }
}