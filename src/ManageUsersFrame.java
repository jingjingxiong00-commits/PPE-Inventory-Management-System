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
import java.awt.*;  
import java.awt.event.*;  
import java.sql.*;  
import java.util.ArrayList;  
import java.util.List;  
import javax.swing.table.AbstractTableModel;  
 

 
public class ManageUsersFrame extends JFrame { 
    private JTextField userIdField, nameField; 
    private JPasswordField passwordField; 
    private JComboBox<String> userTypeComboBox; 
    private JTable userTable; 
    private UserTableModel tableModel; 
 
    public ManageUsersFrame(String currentUserType) { 
        setTitle("Manage Users"); 
        setSize(800, 800); 
        setLayout(new BorderLayout()); 
 
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5)); 
        inputPanel.setBorder(BorderFactory.createTitledBorder("User  Details")); 
 
        userIdField = new JTextField(); 
        nameField = new JTextField(); 
        passwordField = new JPasswordField(); 
        userTypeComboBox = new JComboBox<>(new String[]{"admin", "staff"}); 
 
        inputPanel.add(new  JLabel("User ID:")); 
        inputPanel.add(userIdField);  
        inputPanel.add(new  JLabel("Name:")); 
        inputPanel.add(nameField);  
        inputPanel.add(new  JLabel("Password:")); 
        inputPanel.add(passwordField);  
        inputPanel.add(new  JLabel("User Type:")); 
        inputPanel.add(userTypeComboBox);  
 
        JPanel buttonPanel = new JPanel(); 
        JButton addButton = new JButton("Add"); 
        JButton updateButton = new JButton("Update"); 
        JButton deleteButton = new JButton("Delete"); 
 
        buttonPanel.add(addButton);  
        buttonPanel.add(updateButton);  
        buttonPanel.add(deleteButton);  
 
        tableModel = new UserTableModel(); 
        userTable = new JTable(tableModel); 
 
        addButton.addActionListener(e  -> addUser()); 
        updateButton.addActionListener(e  -> updateUser()); 
        deleteButton.addActionListener(e  -> deleteUser()); 
 
  
        add(inputPanel, BorderLayout.NORTH); 
        add(buttonPanel, BorderLayout.CENTER); 
        add(new JScrollPane(userTable), BorderLayout.SOUTH); 
 
        loadUsers(); 
    } 
 
    private void addUser() { 
        String userId = userIdField.getText().trim();  
        String name = nameField.getText().trim();  
        String password = new String(passwordField.getPassword()).trim();  
        String userType = (String) userTypeComboBox.getSelectedItem();  
 
        if (userId.isEmpty()  || name.isEmpty()  || password.isEmpty())  { 
            JOptionPane.showMessageDialog(this,  "Please fill all fields"); 
            return; 
        } 
 
        try (Connection conn = DatabaseConnector.getConnection())  { 
            String sql = "INSERT INTO users (userID, name, password, userType) VALUES (?, ?, ?, ?)"; 
            PreparedStatement stmt = conn.prepareStatement(sql);  
            stmt.setString(1,  userId); 
            stmt.setString(2,  name); 
            stmt.setString(3,  password); 
            stmt.setString(4,  userType); 
 
            int rows = stmt.executeUpdate();  
            if (rows > 0) { 
                JOptionPane.showMessageDialog(this,  "User added successfully"); 
                loadUsers(); 
                clearFields(); 
            } 
        } catch (SQLException ex) { 
            JOptionPane.showMessageDialog(this,  "Error: " + ex.getMessage());  
        } 
    } 
 
    private void loadUsers() { 
        List<User> users = new ArrayList<>(); 
        try (Connection conn = DatabaseConnector.getConnection())  { 
            Statement stmt = conn.createStatement();  
            ResultSet rs = stmt.executeQuery("SELECT  * FROM users"); 
 
            while (rs.next())  { 
                users.add(new  User( 
                        rs.getString("userID"),  
                        rs.getString("name"),  
                        rs.getString("password"),  
                        rs.getString("userType")  
                )); 
            } 
            tableModel.setUsers(users);  
        } catch (SQLException ex) { 
            ex.printStackTrace();  
            JOptionPane.showMessageDialog(this,  "Error loading users: " + ex.getMessage());  
        } 
    } 
 
    private void clearFields() { 
        userIdField.setText("");  
        nameField.setText("");  
        passwordField.setText("");  
        userTypeComboBox.setSelectedIndex(0);  
    } 
 
    private void updateUser() { 
        int selectedRow = userTable.getSelectedRow();  
        if (selectedRow == -1) { 
            JOptionPane.showMessageDialog(this,  "Please select a user to update"); 
            return; 
        } 
 
        String userId = userIdField.getText().trim();  
        String name = nameField.getText().trim();  
        String password = new String(passwordField.getPassword()).trim();  
        String userType = (String) userTypeComboBox.getSelectedItem();  
 
        if (userId.isEmpty()  || name.isEmpty()  || password.isEmpty())  { 
            JOptionPane.showMessageDialog(this,  "Please fill all fields"); 
            return; 
        } 
 
        try (Connection conn = DatabaseConnector.getConnection())  { 
            String sql = "UPDATE users SET name = ?, password = ?, userType = ? WHERE userID = ?"; 
            PreparedStatement stmt = conn.prepareStatement(sql);  
            stmt.setString(1,  name); 
            stmt.setString(2,  password); 
            stmt.setString(3,  userType); 
            stmt.setString(4,  userId); 
 
            int rows = stmt.executeUpdate();  
            if (rows > 0) { 
                JOptionPane.showMessageDialog(this,  "User updated successfully"); 
                loadUsers(); 
                clearFields(); 
            } 
        } catch (SQLException ex) { 
            JOptionPane.showMessageDialog(this,  "Error: " + ex.getMessage());  
        } 
    } 
 
    private void deleteUser() { 
        int selectedRow = userTable.getSelectedRow();  
        if (selectedRow == -1) { 
            JOptionPane.showMessageDialog(this,  "Please select a user to delete"); 
            return; 
        } 
 
        String userId = (String) tableModel.getValueAt(selectedRow,  0); 
 
        int confirm = JOptionPane.showConfirmDialog(this,  "Are you sure you want to delete this user?", "Confirm Deletion", JOptionPane.YES_NO_OPTION); 
        if (confirm == JOptionPane.YES_OPTION) { 
            try (Connection conn = DatabaseConnector.getConnection())  { 
                String sql = "DELETE FROM users WHERE userID = ?"; 
                PreparedStatement stmt = conn.prepareStatement(sql);  
                stmt.setString(1,  userId); 
 
                int rows = stmt.executeUpdate();  
                if (rows > 0) { 
                    JOptionPane.showMessageDialog(this,  "User deleted successfully"); 
                    loadUsers(); 
                    clearFields(); 
                } 
            } catch (SQLException ex) { 
                JOptionPane.showMessageDialog(this,  "Error: " + ex.getMessage());  
            } 
        } 
    } 
 
    private static class UserTableModel extends AbstractTableModel { 
        private List<User> users = new ArrayList<>(); 
        private String[] columns = {"User ID", "Name", "Password", "Type"}; 
 
        public void setUsers(List<User> users) { 
            this.users  = users; 
            fireTableDataChanged(); 
        } 
 
        public int getRowCount() { 
            return users.size();  
        } 
 
        public int getColumnCount() { 
            return columns.length;  
        } 
 
        public String getColumnName(int col) { 
            return columns[col]; 
        } 
 
        public Object getValueAt(int row, int col) { 
            User user = users.get(row);  
            return switch (col) { 
                case 0 -> user.userId();  
                case 1 -> user.name();  
                case 2 -> user.password();  
                case 3 -> user.userType();  
                default -> null; 
            }; 
        } 
    } 
 
    private record User(String userId, String name, String password, String userType) {} 
 
    public static void main(String[] args) { 
        SwingUtilities.invokeLater(()  -> { 
            ManageUsersFrame frame = new ManageUsersFrame("admin"); 
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
            frame.setVisible(true);  
        }); 
    } 
} 