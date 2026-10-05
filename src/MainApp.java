/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupassignment.pkgnew;

/**
 *
 * @author user
 */
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainApp extends JFrame{
    public static void main(String[] args) {

        Locale.setDefault(Locale.ENGLISH);

        SwingUtilities.invokeLater(() -> {
            ManageUsersFrame frame = new ManageUsersFrame("admin");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}