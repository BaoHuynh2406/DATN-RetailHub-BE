create database db_retailhub
-- Tạo bảng point_history
CREATE TABLE point_history
(
    history_id INT PRIMARY KEY IDENTITY(1,1), -- Khóa chính tự động tăng
    employee_id INT NOT NULL, -- Mã nhân viên thực hiện giao dịch
    transaction_date DATE NOT NULL, -- Ngày giao dịch
    points INT NOT NULL, -- Số điểm giao dịch
    transaction_type NVARCHAR(20) NOT NULL, -- Loại giao dịch (cộng điểm hoặc trừ điểm)
    description NVARCHAR(100) NOT NULL -- Mô tả giao dịch
);

-- Tạo bảng customers
CREATE TABLE customers
(
    customer_id INT PRIMARY KEY IDENTITY(1000,1), -- Khóa chính tự động tăng, bắt đầu từ 1000
    full_name NVARCHAR(50) NOT NULL, -- Tên đầy đủ của khách hàng
    phone_number VARCHAR(15) NOT NULL, -- Số điện thoại của khách hàng
    points INT NOT NULL -- Số điểm hiện có của khách hàng
);

-- Tạo bảng invoices
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

-- Tạo bảng payment_methods
CREATE TABLE payment_methods
(
    payment_method_id INT PRIMARY KEY IDENTITY(1,1), -- Khóa chính tự động tăng
    payment_name NVARCHAR(50) NOT NULL, -- Tên phương thức thanh toán
    image VARCHAR(100) -- Hình ảnh đại diện cho phương thức thanh toán
);
