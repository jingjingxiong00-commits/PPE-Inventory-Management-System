/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  user
 * Created: 2025年4月5日
 */
CREATE DATABASE IF NOT EXISTS ppe_inventory;
USE ppe_inventory;

CREATE TABLE IF NOT EXISTS Users (
    userID VARCHAR(20) PRIMARY KEY,
    name VARCHAR(50),
    password VARCHAR(50),
    userType VARCHAR(10), 
    email VARCHAR(100)
);


CREATE TABLE IF NOT EXISTS Suppliers (
    supplierCode VARCHAR(20) PRIMARY KEY,
    supplierName VARCHAR(100),
    contactInfo VARCHAR(100)
);


CREATE TABLE IF NOT EXISTS Hospitals (
    hospitalCode VARCHAR(20) PRIMARY KEY,
    hospitalName VARCHAR(100),
    location VARCHAR(100)
);


CREATE TABLE IF NOT EXISTS Items (
    itemCode VARCHAR(20) PRIMARY KEY,
    itemName VARCHAR(100),
    supplierCode VARCHAR(20),
    quantityInBoxes INT,
    FOREIGN KEY (supplierCode) REFERENCES Suppliers(supplierCode)
);
INSERT INTO Users (userID, name, password, userType, email)
VALUES 
('admin1', 'Alice Admin', 'admin123', 'admin', 'alice@admin.com'),
('staff1', 'Bob Staff', 'staff123', 'staff', 'bob@hospital.com');


INSERT INTO Suppliers (supplierCode, supplierName, contactInfo)
VALUES 
('SUP01', 'HealthMed Co.', 'contact@healthmed.com'),
('SUP02', 'Protective Gear Ltd.', 'info@pgear.com'),
('SUP03', 'SafeSupply Inc.', 'support@safesupply.com');


INSERT INTO Hospitals (hospitalCode, hospitalName, location)
VALUES 
('HOSP01', 'City General Hospital', 'Downtown'),
('HOSP02', 'North Valley Hospital', 'North District'),
('HOSP03', 'Sunrise Medical Center', 'Eastside');


INSERT INTO Items (itemCode, itemName, supplierCode, quantityInBoxes)
VALUES 
('PPE01', 'N95 Masks', 'SUP01', 100),
('PPE02', 'Face Shields', 'SUP01', 80),
('PPE03', 'Protective Gowns', 'SUP02', 120),
('PPE04', 'Gloves (Box of 100)', 'SUP03', 200);



CREATE TABLE IF NOT EXISTS Transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    item_code VARCHAR(20) NOT NULL,
    item_name VARCHAR(100) NOT NULL,
    supplier_hospital_code VARCHAR(20),
    quantity INT NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    start_date DATETIME,
    end_date DATETIME
);