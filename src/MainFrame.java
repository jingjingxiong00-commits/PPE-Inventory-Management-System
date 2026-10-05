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
 
public class MainFrame extends JFrame {
    private JButton updateInventoryButton;
    private JButton trackInventoryButton;
    private JButton manageUsersButton;
    private JLabel statusLabel;
    
    public MainFrame(String userType) {
        setTitle("PPE Inventory Management System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JToolBar toolBar = new JToolBar();
        updateInventoryButton = new JButton("Update Inventory");
        trackInventoryButton = new JButton("Tracking Inventory");
        manageUsersButton = new JButton("Manage Users");
        statusLabel = new JLabel("Logged in as: " + userType);
        
        toolBar.add(updateInventoryButton); 
        toolBar.add(trackInventoryButton); 
        toolBar.add(manageUsersButton); 
        toolBar.add(Box.createHorizontalGlue()); 
        toolBar.add(statusLabel); 
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Dashboard",  new DashboardPanel());
        
        setupButtonListeners(userType);
        setupPermissions(userType);
        
        mainPanel.add(toolBar,  BorderLayout.NORTH);
        mainPanel.add(tabbedPane,  BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void setupButtonListeners(String userType) {
        updateInventoryButton.addActionListener(e  -> new UpdateInventoryFrame().setVisible(true));
        trackInventoryButton.addActionListener(e  -> new TrackInventoryFrame().setVisible(true));
        manageUsersButton.addActionListener(e  -> new ManageUsersFrame(userType).setVisible(true));
    }
    
    private void setupPermissions(String userType) {
        boolean isAdmin = "admin".equals(userType);
        manageUsersButton.setEnabled(isAdmin); 
    }
    
    // 仪表板面板 
    private class DashboardPanel extends JPanel {
        public DashboardPanel() {
            setLayout(new BorderLayout());
            add(new JLabel("<html><h1>PPE Inventory Dashboard</h1></html>"), BorderLayout.NORTH);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()  -> new MainFrame("admin").setVisible(true));
    }
}