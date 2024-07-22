CREATE DATABASE db_retailhub;

use db_retailhub;

CREATE TABLE roles (
    role_id VARCHAR(10) PRIMARY KEY,
    role_description NVARCHAR(100) NOT NULL -- mô tả
);

GO
    CREATE TABLE employees (
        employee_id INT IDENTITY(1, 1) PRIMARY KEY,
        full_name NVARCHAR(50) NOT NULL,
        password VARCHAR(500) NOT NULL,
        email VARCHAR(50) NOT NULL,
        phone_number VARCHAR(15) NOT NULL,
        address NVARCHAR(50),
        image NVARCHAR(100),
        birthday DATE,
        start_date DATE NOT NULL, -- ngày vào làm
        end_date DATE, -- ngày ra
        status BIT -- trạng thái
    );

GO
    CREATE TABLE customers (
        customer_id INT PRIMARY KEY IDENTITY(1000, 1),
        -- Khóa chính tự động tăng, bắt đầu từ 1000
        full_name NVARCHAR(50) NOT NULL,
        -- Tên đầy đủ của khách hàng
        phone_number VARCHAR(15) NOT NULL,
        -- Số điện thoại của khách hàng
        points INT NOT NULL -- Số điểm hiện có của khách hàng
    );

CREATE TABLE point_history (
    history_id INT PRIMARY KEY IDENTITY(1, 1),
    -- Khóa chính tự động tăng
    transaction_date DATE NOT NULL,
    -- Ngày giao dịch
    points INT NOT NULL,
    -- Số điểm giao dịch
    transaction_type NVARCHAR(20) NOT NULL,
    -- Loại giao dịch (cộng điểm hoặc trừ điểm)
    description NVARCHAR(100) NOT NULL -- Mô tả giao dịch
);

GO
    CREATE TABLE categories (
        category_id INT IDENTITY(1, 1) PRIMARY KEY,
        category_name NVARCHAR(50)
    );

GO
    CREATE TABLE taxes (
        tax_id VARCHAR(10) PRIMARY KEY,
        tax_name NVARCHAR(20),
        tax_rate DECIMAL(5, 2) -- tỉ lệ thuế
    );

GO
    CREATE TABLE suppliers (
        supplier_id INT IDENTITY(1, 1) PRIMARY KEY,
        supplier_name NVARCHAR(50) NOT NULL,
        supplier_description NVARCHAR(255),
        -- mô tả
        supplier_phone_number VARCHAR(15) NOT NULL,
        supplier_email VARCHAR(50) NOT NULL,
        supplier_address NVARCHAR(50) NOT NULL,
        active BIT NOT NULL -- trạng thái
    );

GO
    CREATE TABLE products (
        product_id INT IDENTITY(1, 1) PRIMARY KEY,
        barcode VARCHAR(25) NOT NULL,
        -- mã vạch
        product_name NVARCHAR(50) NOT NULL,
        product_description NVARCHAR(500) NOT NULL,
        -- mô tả SP
        image NVARCHAR(500) NOT NULL,
        cost DECIMAL(18, 2) NOT NULL,
        -- giá nhập
        quantity DECIMAL(10, 2) NOT NULL,
        -- tồn kho
        unit NVARCHAR(10) NOT NULL,
        -- đơn vị tính
        location NVARCHAR(255),
        -- vị trí
        expiry_date DATE NOT NULL,
        -- ngày hết hạn gần nhất
        status BIT NOT NULL -- trạng thái
    );

GO
    CREATE TABLE receiving (
        receiving_id INT IDENTITY(1, 1) PRIMARY KEY,
        receiving_date DATE NOT NULL
    );

GO
    CREATE TABLE receiving_details (
        receiving_detail_id INT IDENTITY(1, 1) PRIMARY KEY,
        quantity DECIMAL(10, 2) NOT NULL,
        receiving_cost DECIMAL(10, 2) NOT NULL,
        -- giá nhập
        note NVARCHAR(50)
    );

GO
    -- Bảng Stock Check
    CREATE TABLE stock_check (
        stock_check_id INT IDENTITY(1, 1) PRIMARY KEY,
        -- Khóa chính tự động tăng
        stock_check_date DATETIME NOT NULL -- Ngày kiểm
    );

GO
    -- Bảng Stock Check Item
    CREATE TABLE stock_check_item (
        stock_check_item_id INT IDENTITY(1, 1) PRIMARY KEY,
        -- Khóa chính tự động tăng
        actual_quantity DECIMAL(10, 2) NOT NULL,
        -- Số lượng thực tế
        recorded_quantity DECIMAL(10, 2) NOT NULL,
        -- Số lượng trong hệ thống
        note NVARCHAR(50),
        -- Ghi chú
    );

GO
    -- Bảng Payments
    CREATE TABLE payments (
        payment_id INT IDENTITY(1, 1) PRIMARY KEY,
        -- Khóa chính tự động tăng
        amount DECIMAL(18, 2) NOT NULL,
        -- Số tiền thanh toán
        payment_date DATETIME NOT NULL,
        -- Ngày thanh toán
    );

GO
    CREATE TABLE payment_methods (
        payment_method_id INT PRIMARY KEY IDENTITY(1, 1),
        -- Khóa chính tự động tăng
        payment_name NVARCHAR(50) NOT NULL,
        -- Tên phương thức thanh toán
        image VARCHAR(100) -- Hình ảnh đại diện cho phương thức thanh toán
    );

GO
    CREATE TABLE invoices (
        invoice_id INT PRIMARY KEY IDENTITY(1, 1),
        -- Khóa chính tự động tăng
        invoice_date DATETIME NOT NULL,
        -- Ngày lập hóa đơn
        total_tax DECIMAL(18, 2) NOT NULL,
        -- Tổng số thuế
        total_amount DECIMAL(18, 2) NOT NULL,
        -- Tổng số tiền hóa đơn
        total_payment DECIMAL(18, 2) NOT NULL -- Tổng số tiền đã thanh toán
    );

GO
    -- Bảng Invoice ItemsF
    CREATE TABLE invoice_items (
        invoice_item_id INT IDENTITY(1, 1) PRIMARY KEY,
        -- Khóa chính tự động tăng
        quantity INT NOT NULL,
        -- Số lượng
        unit_price DECIMAL(18, 2) NOT NULL,
        -- Giá mỗi sản phẩm
        tax_amount DECIMAL(10, 2) NOT NULL,
        -- Tổng tiền thuế
    );

GO
    -- Thêm dữ liệu vào bảng employee
INSERT INTO
    employees (
        full_name,
        password,
        email,
        phone_number,
        address,
        image,
        birthday,
        start_date,
        end_date,
        status
    )
VALUES
    (
        'Lý Nguyên Hòa',
        'password123',
        'nguyenhoa102@gmail.com',
        '0909123456',
        '123 Đường ABC, Quận 1',
        'image1.jpg',
        '1990-01-01',
        '2023-07-01',
        NULL,
        1
    ),
    (
        'Huỳnh Thiên Bảo',
        'password456',
        'baohuynh2406@gmail.com',
        '0919123456',
        '456 Đường DEF, Quận 2',
        'image2.jpg',
        '1992-02-02',
        '2023-07-01',
        NULL,
        1
    ),
    (
        'Võ Hoàng Vinh',
        'password789',
        'vinhhoang04@gmail.com',
        '0929123456',
        '789 Đường GHI, Quận 3',
        'image3.jpg',
        '1988-03-03',
        '2023-07-01',
        '2024-01-01',
        0
    );

GO
