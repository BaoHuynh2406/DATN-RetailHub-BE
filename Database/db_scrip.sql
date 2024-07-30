CREATE DATABASE db_retailhub;
use db_retailhub;
-- USE master
-- DROP DATABASE db_retailhub;



CREATE TABLE roles (
    role_id VARCHAR(10) PRIMARY KEY,
    role_description NVARCHAR(100) NOT NULL -- mô tả
);

GO
    CREATE TABLE employees (
        employee_id INT IDENTITY(1, 1) PRIMARY KEY,
        role_id VARCHAR(10),
        full_name NVARCHAR(100) NOT NULL,
        password VARCHAR(500) NOT NULL,
        email VARCHAR(50) NOT NULL,
        phone_number VARCHAR(15) NOT NULL,
        address NVARCHAR(200),
        image NVARCHAR(200),
        birthday DATE,
        start_date DATE NOT NULL, -- ngày vào làm
        end_date DATE, -- ngày ra
        status BIT -- trạng thái
    );

GO
    CREATE TABLE customers (
        customer_id INT PRIMARY KEY IDENTITY(1000, 1),
        -- Khóa chính tự động tăng, bắt đầu từ 1000
        full_name NVARCHAR(100) NOT NULL,
        -- Tên đầy đủ của khách hàng
        phone_number VARCHAR(15) NOT NULL,
        -- Số điện thoại của khách hàng
        points INT NOT NULL -- Số điểm hiện có của khách hàng
    );

CREATE TABLE point_history (
    history_id INT PRIMARY KEY IDENTITY(1, 1),
    -- Khóa chính tự động tăng
    employee_id INT,
    -- Mã nhân viên thực hiện giao dịch
    customer_id INT,
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
        tax_name NVARCHAR(50),
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
        status BIT NOT NULL, -- trạng thái
        category_id INT,
        tax_id VARCHAR(10)
    );

GO
    CREATE TABLE receiving (
        receiving_id INT IDENTITY(1, 1) PRIMARY KEY,
        supplier_id INT,
        -- mã nhà cung cấp
        receiving_date DATE NOT NULL
    );

GO
    CREATE TABLE receiving_details (
        receiving_detail_id INT IDENTITY(1, 1) PRIMARY KEY,
        receiving_id INT,
        product_id INT,
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
        employee_id INT NOT NULL,
        -- Khóa ngoại đến bảng nhân viên
        stock_check_date DATETIME NOT NULL -- Ngày kiểm
    );

GO
    -- Bảng Stock Check Item
    CREATE TABLE stock_check_item (
        stock_check_item_id INT IDENTITY(1, 1) PRIMARY KEY,
        -- Khóa chính tự động tăng
        stock_check_id INT,
        -- Khóa ngoại đến bảng Stock Check
        product_id INT,
        -- Khóa ngoại đến bảng sản phẩm
        actual_quantity DECIMAL(10, 2) NOT NULL,
        -- Số lượng thực tế
        recorded_quantity DECIMAL(10, 2) NOT NULL,
        -- Số lượng trong hệ thống
        note NVARCHAR(50)
    );

GO
    -- Bảng Payments
    CREATE TABLE payments (
        payment_id INT IDENTITY(1, 1) PRIMARY KEY,
        -- Khóa chính tự động tăng
        invoice_id INT,
        -- Khóa ngoại đến bảng hóa đơn
        payment_method_id INT,
        -- Khóa ngoại đến bảng phương thức thanh toán
        amount DECIMAL(18, 2) NOT NULL,
        -- Số tiền thanh toán
        payment_date DATETIME NOT NULL -- Ngày thanh toán
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
        employee_id INT NOT NULL,
        -- Mã nhân viên thực hiện giao dịch
        customer_id INT NOT NULL,
        -- Mã khách hàng (liên kết tới bảng customers)
        invoice_date DATETIME NOT NULL,
        -- Ngày lập hóa đơn
        total_tax DECIMAL(18, 2) NOT NULL,
        -- Tổng số thuế
        total_amount DECIMAL(18, 2) NOT NULL,
        -- Tổng số tiền hóa đơn
        total_payment DECIMAL(18, 2) NOT NULL -- Tổng số tiền đã thanh toán
    );

GO
    -- Bảng Invoice Items
    CREATE TABLE invoice_items (
        invoice_item_id INT IDENTITY(1, 1) PRIMARY KEY,
        -- Khóa chính tự động tăng
        invoice_id INT,
        -- Khóa ngoại đến bảng hóa đơn
        product_id INT,
        -- Khóa ngoại đến bảng sản phẩm
        quantity INT NOT NULL,
        -- Số lượng
        unit_price DECIMAL(18, 2) NOT NULL,
        -- Giá mỗi sản phẩm
        tax_amount DECIMAL(10, 2) NOT NULL -- Tổng tiền thuế
    );

GO

-- Thêm khóa ngoại cho bảng employees
ALTER TABLE employees
ADD CONSTRAINT FK_employees_role_id FOREIGN KEY (role_id) REFERENCES roles(role_id);

GO
-- Thêm khóa ngoại cho bảng point_history
ALTER TABLE point_history
ADD CONSTRAINT FK_point_history_employee_id FOREIGN KEY (employee_id) REFERENCES employees(employee_id);
ALTER TABLE point_history
ADD CONSTRAINT FK_point_history_customer_id FOREIGN KEY (customer_id) REFERENCES customers(customer_id);

GO

-- Thêm khóa ngoại cho bảng products
ALTER TABLE products
ADD CONSTRAINT FK_products_category_id FOREIGN KEY (category_id) REFERENCES categories(category_id);
ALTER TABLE products
ADD CONSTRAINT FK_products_tax_id FOREIGN KEY (tax_id) REFERENCES taxes(tax_id);

GO
-- Thêm khóa ngoại cho bảng receiving
ALTER TABLE receiving
ADD CONSTRAINT FK_receiving_supplier_id FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id);

GO
-- Thêm khóa ngoại cho bảng receiving_details
ALTER TABLE receiving_details
ADD CONSTRAINT FK_receiving_details_receiving_id FOREIGN KEY (receiving_id) REFERENCES receiving(receiving_id);
ALTER TABLE receiving_details
ADD CONSTRAINT FK_receiving_details_product_id FOREIGN KEY (product_id) REFERENCES products(product_id);

GO
-- Thêm khóa ngoại cho bảng stock_check
ALTER TABLE stock_check
ADD CONSTRAINT FK_stock_check_employee_id FOREIGN KEY (employee_id) REFERENCES employees(employee_id);

GO
-- Thêm khóa ngoại cho bảng stock_check_item
ALTER TABLE stock_check_item
ADD CONSTRAINT FK_stock_check_item_stock_check_id FOREIGN KEY (stock_check_id) REFERENCES stock_check(stock_check_id);
ALTER TABLE stock_check_item
ADD CONSTRAINT FK_stock_check_item_product_id FOREIGN KEY (product_id) REFERENCES products(product_id);

GO
-- Thêm khóa ngoại cho bảng invoices
ALTER TABLE invoices
ADD CONSTRAINT FK_invoices_employee_id FOREIGN KEY (employee_id) REFERENCES employees(employee_id);
ALTER TABLE invoices
ADD CONSTRAINT FK_invoices_customer_id FOREIGN KEY (customer_id) REFERENCES customers(customer_id);

GO
-- Thêm khóa ngoại cho bảng invoice_items
ALTER TABLE invoice_items
ADD CONSTRAINT FK_invoice_items_invoice_id FOREIGN KEY (invoice_id) REFERENCES invoices(invoice_id);
ALTER TABLE invoice_items
ADD CONSTRAINT FK_invoice_items_product_id FOREIGN KEY (product_id) REFERENCES products(product_id);

GO
-- Thêm khóa ngoại cho bảng payments
ALTER TABLE payments
ADD CONSTRAINT FK_payments_invoice_id FOREIGN KEY (invoice_id) REFERENCES invoices(invoice_id);
ALTER TABLE payments
ADD CONSTRAINT FK_payments_payment_method_id FOREIGN KEY (payment_method_id) REFERENCES payment_methods(payment_method_id);

 --------------------------------------------------------------------------------------

 INSERT INTO Roles Values ('USER', N'Người dùng'), ('ADMIN', N'Quản lý')

 SELECT * FROM employees

INSERT INTO employees (
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
    N'Lý Nguyên Hòa',
    'password123',
    'nguyenhoa102@gmail.com',
    '0909123456',
    N'123 Đường ABC, Quận 1',
    'image1.jpg',
    '1990-01-01',
    '2023-07-01',
    NULL,
    1
),
(
    N'Huỳnh Thiên Bảo',
    'password456',
    'baohuynh2406@gmail.com',
    '0919123456',
    N'456 Đường DEF, Quận 2',
    'image2.jpg',
    '1992-02-02',
    '2023-07-01',
    NULL,
    1
),
(
    N'Võ Hoàng Vinh',
    'password789',
    'vinhhoang04@gmail.com',
    '0929123456',
    N'789 Đường GHI, Quận 3',
    'image3.jpg',
    '1988-03-03',
    '2023-07-01',
    '2024-01-01',
    0
);
