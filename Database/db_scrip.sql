CREATE DATABASE db_retailhub;

use db_retailhub;

-- Bảng Invoice ItemsF
CREATE TABLE invoice_items 
(
    invoice_item_id INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính tự động tăng
    invoice_id INT, -- Khóa ngoại đến bảng hóa đơn
    product_id INT, -- Khóa ngoại đến bảng sản phẩm
    quantity INT, -- Số lượng
    unit_price DECIMAL(18,2), -- Giá mỗi sản phẩm
    tax_amount DECIMAL(10,2), -- Tổng tiền thuế
);

-- Bảng Stock Check
CREATE TABLE stock_check 
(
    stock_check_id INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính tự động tăng
    employee_id INT, -- Khóa ngoại đến bảng nhân viên
    stock_check_date DATETIME -- Ngày kiểm
);

-- Bảng Stock Check Item
CREATE TABLE stock_check_item 
(
    stock_check_item_id INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính tự động tăng
    stock_check_id INT, -- Khóa ngoại đến bảng Stock Check
    product_id INT, -- Khóa ngoại đến bảng sản phẩm
    actual_quantity DECIMAL(10,2), -- Số lượng thực tế
    recorded_quantity DECIMAL(10,2), -- Số lượng trong hệ thống
    note NVARCHAR(50), -- Ghi chú
);

-- Bảng Payments
CREATE TABLE payments 
(
    payment_id INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính tự động tăng
    invoice_id INT, -- Khóa ngoại đến bảng hóa đơn
    payment_method_id INT, -- Khóa ngoại đến bảng phương thức thanh toán
    amount DECIMAL(18,2), -- Số tiền thanh toán
    payment_date DATETIME, -- Ngày thanh toán
﻿create database db_retailhub

 CREATE TABLE point_history
(
    history_id INT PRIMARY KEY IDENTITY(1,1), -- Khóa chính tự động tăng
    employee_id INT NOT NULL, -- Mã nhân viên thực hiện giao dịch
    transaction_date DATE NOT NULL, -- Ngày giao dịch
    points INT NOT NULL, -- Số điểm giao dịch
    transaction_type NVARCHAR(20) NOT NULL, -- Loại giao dịch (cộng điểm hoặc trừ điểm)
    description NVARCHAR(100) NOT NULL -- Mô tả giao dịch
);


CREATE TABLE customers
(
    customer_id INT PRIMARY KEY IDENTITY(1000,1), -- Khóa chính tự động tăng, bắt đầu từ 1000
    full_name NVARCHAR(50) NOT NULL, -- Tên đầy đủ của khách hàng
    phone_number VARCHAR(15) NOT NULL, -- Số điện thoại của khách hàng
    points INT NOT NULL -- Số điểm hiện có của khách hàng
);

CREATE TABLE invoices
(
    invoice_id INT PRIMARY KEY IDENTITY(1,1), -- Khóa chính tự động tăng
    employee_id INT NOT NULL, -- Mã nhân viên thực hiện giao dịch
    customer_id INT NOT NULL, -- Mã khách hàng (liên kết tới bảng customers)
    invoice_date DATETIME NOT NULL, -- Ngày lập hóa đơn
    total_tax DECIMAL(18,2) NOT NULL, -- Tổng số thuế
    total_amount DECIMAL(18,2) NOT NULL, -- Tổng số tiền hóa đơn
    total_payment DECIMAL(18,2) NOT NULL -- Tổng số tiền đã thanh toán
);

CREATE TABLE payment_methods
(
    payment_method_id INT PRIMARY KEY IDENTITY(1,1), -- Khóa chính tự động tăng
    payment_name NVARCHAR(50) NOT NULL, -- Tên phương thức thanh toán
    image VARCHAR(100) -- Hình ảnh đại diện cho phương thức thanh toán
);
GO

CREATE TABLE employees 
(
                           employee_id INT IDENTITY(1,1) PRIMARY KEY,
                           full_name NVARCHAR(50),
                           password VARCHAR(500),
                           email VARCHAR(50),
                           phone_number VARCHAR(15),
                           address NVARCHAR(50),
                           image NVARCHAR(100),
                           birthday DATE,
                           start_date DATE, -- ngày vào làm
                           end_date DATE, -- ngày ra
                           status BIT -- trạng thái
);
GO

CREATE TABLE suppliers 
(
                           supplier_id INT IDENTITY(1,1) PRIMARY KEY,
                           supplier_name NVARCHAR(50),
                           supplier_description NVARCHAR(255), -- mô tả
                           supplier_phone_number VARCHAR(15),
                           supplier_email VARCHAR(50),
                           supplier_address NVARCHAR(50),
                           active BIT -- trạng thái
);
GO

CREATE TABLE products 
(
                          product_id INT IDENTITY(1,1) PRIMARY KEY,
                          barcode VARCHAR(25), -- mã vạch
                          product_name NVARCHAR(50),
                          product_description NVARCHAR(500), -- mô tả SP
                          image NVARCHAR(500),
                          cost DECIMAL(18,2), -- giá nhập
                          quantity DECIMAL(10,2), -- tồn kho
                          unit NVARCHAR(10), -- đơn vị tính
                          location NVARCHAR(255), -- vị trí
                          expiry_date DATE, -- ngày hết hạn gần nhất
                          status BIT -- trạng thái
);
GO

CREATE TABLE roles 
(
                       role_id VARCHAR(10) PRIMARY KEY,
                       role_description NVARCHAR(100) -- mô tả
);
GO

CREATE TABLE categories 
(
                            category_id INT IDENTITY(1,1) PRIMARY KEY,
                            category_name NVARCHAR(50)
);
GO

CREATE TABLE taxes 
(
                       tax_id VARCHAR(10) PRIMARY KEY,
                       tax_name NVARCHAR(20),
                       tax_rate DECIMAL(5,2) -- tỉ lệ thuế
);
GO

CREATE TABLE receiving 
(
                           receiving_id INT IDENTITY(1,1) PRIMARY KEY,
                           supplier_id INT, -- mã nhà cung cấp
                           receiving_date DATE
);
GO

CREATE TABLE receiving_details 
(
                                   receiving_detail_id INT IDENTITY(1,1) PRIMARY KEY,
                                   receiving_id INT,
                                   product_id INT,
                                   quantity DECIMAL(10,2),
                                   receiving_cost DECIMAL(10,2), -- giá nhập
                                   note NVARCHAR(50)
);
GO
