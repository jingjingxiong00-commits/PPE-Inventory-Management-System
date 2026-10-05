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
 
public class UpdateInventoryFrame extends JFrame {
    private JTextField itemCodeField;
    private JTextField quantityField;
    private JComboBox<String> actionComboBox;
    private JButton updateButton;
 
    public UpdateInventoryFrame() {
        setTitle("Update Inventory");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
 
        JLabel itemCodeLabel = new JLabel("Item Code:");
        itemCodeField = new JTextField(20);
 
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField(20);
 
        JLabel actionLabel = new JLabel("Action:");
        actionComboBox = new JComboBox<>(new String[]{"Received", "Distributed"});
 
        updateButton = new JButton("Update");
 
        add(itemCodeLabel);
        add(itemCodeField);
        add(quantityLabel);
        add(quantityField);
        add(actionLabel);
        add(actionComboBox);
        add(updateButton);
 
        updateButton.addActionListener(new  ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemCode = itemCodeField.getText(); 
                int quantity = Integer.parseInt(quantityField.getText()); 
                String action = (String) actionComboBox.getSelectedItem(); 
 
                if (updateInventory(itemCode, quantity, action)) {
                    JOptionPane.showMessageDialog(null,  "Inventory Updated Successfully!");
                } else {
                    JOptionPane.showMessageDialog(null,  "Failed to Update Inventory.");
                }
            }
        });
 
        setVisible(true);
    }
    
   private boolean updateInventory(String itemCode, int quantity, String action) {
    Connection conn = PPEInventorySystem.connectToDatabase();
    if (conn == null) return false;

    try {
        // Find the item and its current stock
        String selectQuery =
                "SELECT itemName, supplierCode, quantityInBoxes " +
                "FROM Items WHERE itemCode = ?";

        PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
        selectStmt.setString(1, itemCode);
        ResultSet rs = selectStmt.executeQuery();

        if (!rs.next()) {
            JOptionPane.showMessageDialog(
                    null,
                    "The item code was not found: " + itemCode
            );
            return false;
        }

        String itemName = rs.getString("itemName");
        String supplierCode = rs.getString("supplierCode");
        int currentStock = rs.getInt("quantityInBoxes");

        // Validate distributed quantity
        if (action.equals("Distributed")) {
            if (currentStock < 25 || quantity > currentStock) {
                JOptionPane.showMessageDialog(
                        null,
                        "Out of stock! Current stock is " + currentStock +
                        ". Please reduce the number of distributions and try again."
                );
                return false;
            }
        }

        // Update inventory
        String updateQuery =
                "UPDATE Items SET quantityInBoxes = quantityInBoxes " +
                (action.equals("Received") ? "+ ?" : "- ?") +
                " WHERE itemCode = ?";

        PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
        updateStmt.setInt(1, quantity);
        updateStmt.setString(2, itemCode);

        int rows = updateStmt.executeUpdate();

        if (rows > 0) {
            // Record the inventory transaction
            String transactionQuery =
                    "INSERT INTO Transactions " +
                    "(item_code, item_name, supplier_hospital_code, quantity, " +
                    "transaction_type, start_date, end_date) " +
                    "VALUES (?, ?, ?, ?, ?, NOW(), NOW())";

            PreparedStatement transactionStmt =
                    conn.prepareStatement(transactionQuery);

            transactionStmt.setString(1, itemCode);
            transactionStmt.setString(2, itemName);
            transactionStmt.setString(3, supplierCode);
            transactionStmt.setInt(4, quantity);
            transactionStmt.setString(5, action);

            transactionStmt.executeUpdate();

            return true;
        }

        return false;

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(
                null,
                "Error: " + ex.getMessage()
        );
        return false;

    } finally {
        try {
            if (conn != null) conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
}