/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupassignment.pkgnew;

/**
 *
 * @author user
 */

import java.util.Scanner;

public class MainMenu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();
        SetupManager setupManager = new SetupManager();

        while (true) {
            System.out.println("\n==== PPE Management System ====");
            System.out.println("1. Add User");
            System.out.println("2. Search User");
            System.out.println("3. Delete User");
            System.out.println("4. Add Supplier");
            System.out.println("5. Add Hospital");
            System.out.println("6. Add Item");
            System.out.println("0. Exit");
            System.out.print("Please enter option: ");
            
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("User ID: ");
                    String userID = scanner.nextLine();
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    System.out.print("Type of User (admin/staff): ");
                    String userType = scanner.nextLine();
                    System.out.print("E-mail: ");
                    String email = scanner.nextLine();
                    userManager.addUser(userID, name, password, userType, email);
                    break;

                case "2":
                    System.out.print("Enter the user ID you want to search for: ");
                    String searchID = scanner.nextLine();
                    userManager.searchUser(searchID);
                    break;

                case "3":
                    System.out.print("Enter the user ID you want to delete: ");
                    String deleteID = scanner.nextLine();
                    userManager.deleteUser(deleteID);
                    break;

                case "4":
                    System.out.print("Supplier Code: ");
                    String supplierCode = scanner.nextLine();
                    System.out.print("Supplier Name: ");
                    String supplierName = scanner.nextLine();
                    System.out.print("Contact Details: ");
                    String contact = scanner.nextLine();
                    setupManager.addSupplier(supplierCode, supplierName, contact);
                    break;

                case "5":
                    System.out.print("Hospital Code: ");
                    String hospitalCode = scanner.nextLine();
                    System.out.print("Hospital Name: ");
                    String hospitalName = scanner.nextLine();
                    System.out.print("Hospital Address: ");
                    String location = scanner.nextLine();
                    setupManager.addHospital(hospitalCode, hospitalName, location);
                    break;

                case "6":
                    System.out.print("Item Code: ");
                    String itemCode = scanner.nextLine();
                    System.out.print("Item Name: ");
                    String itemName = scanner.nextLine();
                    System.out.print("Supplier Code: ");
                    String itemSupplierCode = scanner.nextLine();
                    System.out.print("Initial quantity: ");
                    int quantity = Integer.parseInt(scanner.nextLine());
                    setupManager.addItem(itemCode, itemName, itemSupplierCode, quantity);
                    break;

                case "0":
                    System.out.println("System has exited.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option, please re-enter!");
            }
        }
    }
}
