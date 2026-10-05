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
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.Timestamp;  
 
public class TrackInventoryFrame extends JFrame { 
    private JTextField itemCodeField; 
    private JButton trackButton; 
    private JTextArea resultArea; 
 
    public TrackInventoryFrame() { 
        setTitle("Track Inventory Transactions by Item Code"); 
        setSize(500, 400); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLayout(new BorderLayout(10, 10)); 
 
        // Input panel 
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
        JLabel itemCodeLabel = new JLabel("Item Code:"); 
        itemCodeField = new JTextField(20); 
        trackButton = new JButton("Track Transaction"); 
 
        inputPanel.add(itemCodeLabel);  
        inputPanel.add(itemCodeField);  
        inputPanel.add(trackButton);  
 
        // Result area 
        resultArea = new JTextArea(15, 40); 
        resultArea.setEditable(false);  
        JScrollPane scrollPane = new JScrollPane(resultArea); 
 
        // Add components to frame 
        add(inputPanel, BorderLayout.NORTH); 
        add(scrollPane, BorderLayout.CENTER); 
 
        trackButton.addActionListener(new  ActionListener() { 
            @Override 
            public void actionPerformed(ActionEvent e) { 
                String itemCode = itemCodeField.getText().trim();  
                if (itemCode.isEmpty())  { 
                    JOptionPane.showMessageDialog(null,   "Please enter an Item Code"); 
                    return; 
                } 
                trackTransaction(itemCode); 
            } 
        }); 
 
        setVisible(true); 
    } 
 
    private void trackTransaction(String itemCode) { 
        Connection conn = PPEInventorySystem.connectToDatabase();  
        if (conn == null) { 
            JOptionPane.showMessageDialog(null,   "Failed to connect to database"); 
            return; 
        } 
 
        String query = "SELECT t.transaction_id, t.item_name, t.supplier_hospital_code, " +
               "t.quantity, t.transaction_type, t.start_date, t.end_date " +
               "FROM Transactions t " +
               "WHERE t.item_code = ?";
 
        try (PreparedStatement stmt = conn.prepareStatement(query))  { 
            stmt.setString(1,  itemCode); 
            ResultSet rs = stmt.executeQuery();  
 
            if (rs.next())  { 
                // Format the results for display 
                StringBuilder sb = new StringBuilder(); 
                sb.append("Transaction  Details:\n"); 
                sb.append("-------------------\n");  
                sb.append(String.format("%-20s:  %s\n", "Transaction ID", rs.getString("transaction_id")));  
                sb.append(String.format("%-20s:  %s\n", "Item Name", rs.getString("item_name")));  
                sb.append(String.format("%-20s:  %s\n", "Partner Code", rs.getString("supplier_hospital_code")));  
                sb.append(String.format("%-20s:  %d\n", "Quantity", rs.getInt("quantity")));  
                sb.append(String.format("%-20s:  %s\n", "Transaction Type", rs.getString("transaction_type")));  
                
               Timestamp startDate = rs.getTimestamp("start_date");
Timestamp endDate = rs.getTimestamp("end_date");

sb.append(String.format("%-20s: %s\n",
        "Start Date",
        startDate != null ? startDate.toString() : "N/A"));

sb.append(String.format("%-20s: %s\n",
        "End Date",
        endDate != null ? endDate.toString() : "N/A"));
                
                resultArea.setText(sb.toString());  
            } else { 
                resultArea.setText("No  transaction found with Item Code: " + itemCode); 
            } 
        } catch (Exception ex) { 
            JOptionPane.showMessageDialog(null,   "Error: " + ex.getMessage());  
            ex.printStackTrace();  
        } finally { 
            try { 
                if (conn != null) conn.close();  
            } catch (Exception ex) { 
                ex.printStackTrace();  
            } 
        } 
    } 
} 