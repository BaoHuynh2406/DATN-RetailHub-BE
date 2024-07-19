CREATE DATABASE db_retailhub;
GO

CREATE TABLE employees (
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

CREATE TABLE suppliers (
                           supplier_id INT IDENTITY(1,1) PRIMARY KEY,
                           supplier_name NVARCHAR(50),
                           supplier_description NVARCHAR(255), -- mô tả
                           supplier_phone_number VARCHAR(15),
                           supplier_email VARCHAR(50),
                           supplier_address NVARCHAR(50),
                           active BIT -- trạng thái
);
GO

CREATE TABLE products (
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

CREATE TABLE roles (
                       role_id VARCHAR(10) PRIMARY KEY,
                       role_description NVARCHAR(100) -- mô tả
);
GO

CREATE TABLE categories (
                            category_id INT IDENTITY(1,1) PRIMARY KEY,
                            category_name NVARCHAR(50)
);
GO

CREATE TABLE taxes (
                       tax_id VARCHAR(10) PRIMARY KEY,
                       tax_name NVARCHAR(20),
                       tax_rate DECIMAL(5,2) -- tỉ lệ thuế
);
GO

CREATE TABLE receiving (
                           receiving_id INT IDENTITY(1,1) PRIMARY KEY,
                           supplier_id INT, -- mã nhà cung cấp
                           receiving_date DATE
);
GO

CREATE TABLE receiving_details (
                                   receiving_detail_id INT IDENTITY(1,1) PRIMARY KEY,
                                   receiving_id INT,
                                   product_id INT,
                                   quantity DECIMAL(10,2),
                                   receiving_cost DECIMAL(10,2), -- giá nhập
                                   note NVARCHAR(50)
);
GO
