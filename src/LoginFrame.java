/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupassignment.pkgnew;

/**
 *
 * @author user
 */
import javax.swing.*; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException;
 
public class LoginFrame extends JFrame {
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JButton loginButton;
   
    public class LoginSystem {
    private static String loggedInUserType;
    
    public static void setLoggedInUserType(String userType) {
        loggedInUserType = userType;
    }
    
    public static String getCurrentUserType() {
        return loggedInUserType;
    }
}
   
    public LoginFrame() {
        setTitle("PPE Inventory System - Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
 
        JLabel userIdLabel = new JLabel("User ID:");
        userIdField = new JTextField(20);
 
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
 
        loginButton = new JButton("Login");
 
        add(userIdLabel);
        add(userIdField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
 
        loginButton.addActionListener(new  ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText(); 
                String password = new String(passwordField.getPassword()); 
 
                String userType = validateLogin(userId, password);

if (userType != null) {
    JOptionPane.showMessageDialog(null, "Login Successful!");
    dispose();
    new MainFrame(userType).setVisible(true);
} else {
    JOptionPane.showMessageDialog(null, "Invalid User ID or Password.");
}
            }
        });
 
        setVisible(true);
    }
 
    private String validateLogin(String userId, String password) {
    Connection conn = PPEInventorySystem.connectToDatabase();
    if (conn == null) return null;

    String query = "SELECT userType FROM users WHERE userID = ? AND password = ?";

    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, userId);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getString("userType");
        }

        return null;

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(
            null,
            "Database Error: " + ex.getMessage()
        );
        return null;
    }
}
}