CREATE DATABASE db_minimart

use db_minimart

CREATE TABLE db_roles -- Bảng vai trò
( 
    role_id VARCHAR(5) PRIMARY KEY, -- mã vai trò
    role_name NVARCHAR(50) NOT NULL, -- Tên vai trò
    role_description NVARCHAR(200) -- Mô tả vai trò
);

CREATE TABLE db_employees -- Bảng người dùng hệ thống
(
    employees_id INT IDENTITY(1000,1) PRIMARY KEY, -- Mã nhân viên
    employees_full_name NVARCHAR(50) NOT NULL, -- Tên nhân viên
    employees_password VARCHAR(1000) NOT NULL, -- mật khẩu mã hóa
    employees_email VARCHAR(100) NOT NULL, -- email nhân viên
    role_id VARCHAR(5), -- Mã vai trò
    employees_phone_number VARCHAR(15), -- SĐT nhân viên
    employees_address NVARCHAR(500), -- địa chỉ nhân viên
    employees_birtday DATE, -- Ngày sinh nhân viên
    employees_start_date DATE, -- Ngày gia nhập
    employees_end_date DATE, -- Ngày nghỉ
    employees_status bit, -- Trạng thái hoạt động (1 - True, 0 - False)
    FOREIGN KEY (role_id) REFERENCES db_roles(role_id) -- Khóa ngoại đến bảng role
);



CREATE TABLE db_customers -- Bảng khách hàng
(
    customer_id int IDENTITY(1000,1) PRIMARY KEY, -- Mã khách hàng
    customer_fullName NVARCHAR(50) NOT NULL, -- Tên khách hàng
    customer_phone_number VARCHAR(15) NOT NULL UNIQUE, -- Số điện thoại khách hàng, không được trùng nhau
    customer_points INT DEFAULT 0 -- Điểm của khách hàng

);

CREATE TABLE db_points_history -- Bảng lịch sử đổi điểm
(
    history_id INT IDENTITY(1,1) PRIMARY KEY, -- Mã lịch sử điểm
    customer_id INT NOT NULL, -- Mã khách hàng
    employess_id INT NOT NULL, -- Mã nhân viên
    transaction_date DATE NOT NULL, -- Ngày giao dịch
    points INT NOT NULL, -- Số điểm (có thể là âm hoặc dương)
    transaction_type NVARCHAR(50) NOT NULL, -- Loại giao dịch ('Cộng điểm' hoặc 'Trừ điểm')
    description NVARCHAR(255), -- Chi tiết về giao dịch (ví dụ: lý do cộng/trừ điểm)
    FOREIGN KEY (customer_id) REFERENCES db_customers(customer_id), -- Khóa ngoại liên kết với bảng Khách Hàng
    FOREIGN KEY (employess_id) REFERENCES db_employees(employees_id) -- Khóa ngoại liên kết với bảng Nhân viên
);


CREATE TABLE db_products (
    product_id INT PRIMARY KEY IDENTITY(1,1), 
    product_barcode VARCHAR(100) UNIQUE NOT NULL,
    product_name NVARCHAR(255) NOT NULL,
    product_description NVARCHAR(MAX),
    product_price DECIMAL(10, 2) NOT NULL,
    ExpiryDate DATE,
    product_status bit DEFAULT 1,
);
