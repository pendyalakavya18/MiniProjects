# Database Created 

CREATE DATABASE RetailInsights;
USE RetailInsights;

# Categories table

CREATE TABLE Categories (
    CategoryID INT PRIMARY KEY AUTO_INCREMENT,
    CategoryName VARCHAR(50)
);

# Products Table

CREATE TABLE Products (
    ProductID INT PRIMARY KEY AUTO_INCREMENT,
    ProductName VARCHAR(100),
    CategoryID INT,
    ExpiryDate DATE,
    StockCount INT,
    Price DECIMAL(10,2),
    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)
);

# SalesTransactions Table

CREATE TABLE SalesTransactions (
    TransactionID INT PRIMARY KEY AUTO_INCREMENT,
    ProductID INT,
    QuantitySold INT,
    TransactionDate DATE,
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);
# Inserting data into Categories table

INSERT INTO Categories (CategoryName) VALUES
('Fruits'),
('Dairy'),
('Beverages'),
('Snacks');

# Inserting data into Products table

INSERT INTO Products (ProductName, CategoryID, ExpiryDate, StockCount, Price) VALUES
('Milk', 2, CURDATE() + INTERVAL 5 DAY, 60, 50),
('Cheese', 2, CURDATE() + INTERVAL 20 DAY, 30, 120),
('Apple', 1, CURDATE() + INTERVAL 3 DAY, 80, 30),
('Chips', 4, CURDATE() + INTERVAL 60 DAY, 100, 20),
('Juice', 3, CURDATE() + INTERVAL 10 DAY, 40, 60),
('Butter', 2, CURDATE() + INTERVAL 2 DAY, 70, 90);

# Inserting data into Sales Transactions table

INSERT INTO SalesTransactions (ProductID, QuantitySold, TransactionDate) VALUES
(1, 10, CURDATE() - INTERVAL 5 DAY),
(1, 5, CURDATE() - INTERVAL 10 DAY),
(3, 20, CURDATE() - INTERVAL 2 DAY),
(4, 15, CURDATE() - INTERVAL 40 DAY),
(5, 10, CURDATE() - INTERVAL 15 DAY);

# Expiring Soon Products

SELECT ProductName, ExpiryDate, StockCount
FROM Products
WHERE ExpiryDate BETWEEN CURDATE() AND CURDATE() + INTERVAL 7 DAY
AND StockCount > 50;

 #Dead Stock Analysis
 
 SELECT P.ProductID, P.ProductName
FROM Products P
LEFT JOIN SalesTransactions S 
ON P.ProductID = S.ProductID 
AND S.TransactionDate >= CURDATE() - INTERVAL 60 DAY
WHERE S.ProductID IS NULL;

SELECT C.CategoryName, 
SUM(S.QuantitySold * P.Price) AS TotalRevenue
FROM SalesTransactions S
JOIN Products P ON S.ProductID = P.ProductID
JOIN Categories C ON P.CategoryID = C.CategoryID
WHERE MONTH(S.TransactionDate) = MONTH(CURDATE() - INTERVAL 1 MONTH)
AND YEAR(S.TransactionDate) = YEAR(CURDATE() - INTERVAL 1 MONTH)
GROUP BY C.CategoryName
ORDER BY TotalRevenue DESC;


