CREATE DATABASE db_retailhub;
GO
use db_retailhub;

-- USE master
-- GO
-- DROP DATABASE db_retailhub;

CREATE TABLE roles
(
    role_id VARCHAR(10) PRIMARY KEY,
    role_description NVARCHAR(100) NOT NULL
);

CREATE TABLE users
(
    user_id BIGINT IDENTITY(100000, 1) PRIMARY KEY,
    role_id VARCHAR(10),
    full_name NVARCHAR(100) NOT NULL,
    password VARCHAR(500) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    address NVARCHAR(200),
    image_name NVARCHAR(200),
    birthday DATE,
    start_date DATE NOT NULL,
    end_date DATE,
    is_active BIT DEFAULT 1,
    is_delete BIT DEFAULT 0,
    CONSTRAINT FK_users_roles FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

CREATE TABLE customers
(
    customer_id BIGINT PRIMARY KEY IDENTITY(1000, 1),
    full_name NVARCHAR(100) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    points INT NOT NULL,
    is_active BIT DEFAULT 1,
    is_delete BIT DEFAULT 0
);


CREATE TABLE point_history
(
    history_id BIGINT PRIMARY KEY IDENTITY(1, 1),
    user_id BIGINT,
    customer_id BIGINT,
    transaction_date DATE NOT NULL,
    points INT NOT NULL,
    transaction_type NVARCHAR(20) NOT NULL,
    description NVARCHAR(100) NOT NULL,
    CONSTRAINT FK_point_history_users FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT FK_point_history_customers FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);


CREATE TABLE categories
(
    category_id INT IDENTITY(1, 1) PRIMARY KEY,
    category_name NVARCHAR(50),
    is_delete BIT DEFAULT 0
);


CREATE TABLE taxes
(
    tax_id VARCHAR(10) PRIMARY KEY,
    tax_name NVARCHAR(50),
    tax_rate DECIMAL(5, 2)
);


CREATE TABLE suppliers
(
    supplier_id BIGINT IDENTITY(1, 1) PRIMARY KEY,
    supplier_name NVARCHAR(50) NOT NULL,
    supplier_description NVARCHAR(255),
    supplier_phone_number VARCHAR(15) NOT NULL,
    supplier_email VARCHAR(50) NOT NULL,
    supplier_address NVARCHAR(50) NOT NULL,
    is_delete BIT DEFAULT 0 NOT NULL
);


CREATE TABLE products
(
    product_id BIGINT IDENTITY(100000, 1) PRIMARY KEY,
    barcode VARCHAR(25) NOT NULL,
    product_name NVARCHAR(50) NOT NULL,
    product_description NVARCHAR(500) NOT NULL,
    image NVARCHAR(500) NOT NULL,
    cost DECIMAL(18, 2) NOT NULL,
    price DECIMAL(18, 2) NOT NULL,
    inventory_count DECIMAL(10, 2) NOT NULL,
    unit NVARCHAR(10) NOT NULL,
    location NVARCHAR(255),
    expiry_date DATE NOT NULL,
    is_delete BIT DEFAULT 0 NOT NULL,
    category_id INT,
    tax_id VARCHAR(10),
    CONSTRAINT FK_products_categories FOREIGN KEY (category_id) REFERENCES categories(category_id),
    CONSTRAINT FK_products_taxes FOREIGN KEY (tax_id) REFERENCES taxes(tax_id)
);


CREATE TABLE receiving
(
    receiving_id BIGINT IDENTITY(1, 1) PRIMARY KEY,
    supplier_id BIGINT,
    receiving_date DATE NOT NULL,
    user_id BIGINT,
    CONSTRAINT FK_receiving_suppliers FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id),
    CONSTRAINT FK_receiving_users FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE receiving_details
(
    receiving_detail_id BIGINT IDENTITY(1, 1) PRIMARY KEY,
    receiving_id BIGINT,
    product_id BIGINT,
    quantity DECIMAL(10, 2) NOT NULL,
    receiving_cost DECIMAL(10, 2) NOT NULL,
    note NVARCHAR(50),
    CONSTRAINT FK_receiving_details_receiving FOREIGN KEY (receiving_id) REFERENCES receiving(receiving_id),
    CONSTRAINT FK_receiving_details_products FOREIGN KEY (product_id) REFERENCES products(product_id)
);


CREATE TABLE stock_check
(
    stock_check_id BIGINT IDENTITY(1, 1) PRIMARY KEY,
    user_id BIGINT NOT NULL,
    stock_check_date DATETIME NOT NULL,
    CONSTRAINT FK_stock_check_users FOREIGN KEY (user_id) REFERENCES users(user_id)
);


CREATE TABLE stock_check_item
(
    stock_check_item_id BIGINT IDENTITY(1, 1) PRIMARY KEY,
    stock_check_id BIGINT,
    product_id BIGINT,
    actual_quantity DECIMAL(10, 2) NOT NULL,
    recorded_quantity DECIMAL(10, 2) NOT NULL,
    note NVARCHAR(50),
    CONSTRAINT FK_stock_check_item_stock_check FOREIGN KEY (stock_check_id) REFERENCES stock_check(stock_check_id),
    CONSTRAINT FK_stock_check_item_products FOREIGN KEY (product_id) REFERENCES products(product_id)
);




CREATE TABLE invoices
(
    invoice_id BIGINT PRIMARY KEY IDENTITY(1, 1),
    user_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    invoice_date DATETIME NOT NULL,
    total_tax DECIMAL(18, 2) NOT NULL,
    total_amount DECIMAL(18, 2) NOT NULL,
    total_payment DECIMAL(18, 2) NOT NULL,
    CONSTRAINT FK_invoices_users FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT FK_invoices_customers FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);


CREATE TABLE invoice_items
(
    invoice_item_id BIGINT IDENTITY(1, 1) PRIMARY KEY,
    invoice_id BIGINT,
    product_id BIGINT,
    quantity INT NOT NULL,
    unit_price DECIMAL(18, 2) NOT NULL,
    tax_amount DECIMAL(10, 2) NOT NULL,
    CONSTRAINT FK_invoice_items_invoices FOREIGN KEY (invoice_id) REFERENCES invoices(invoice_id),
    CONSTRAINT FK_invoice_items_products FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE payment_methods
(
    payment_method_id INT PRIMARY KEY IDENTITY(1, 1),
    payment_name NVARCHAR(50) NOT NULL,
    image VARCHAR(100)
);

CREATE TABLE payments
(
    payment_id BIGINT IDENTITY(1, 1) PRIMARY KEY,
    invoice_id BIGINT,
    payment_method_id INT,
    amount DECIMAL(18, 2) NOT NULL,
    payment_date DATETIME NOT NULL,
    CONSTRAINT FK_payments_invoices FOREIGN KEY (invoice_id) REFERENCES invoices(invoice_id),
    CONSTRAINT FK_payments_payment_methods FOREIGN KEY (payment_method_id) REFERENCES payment_methods(payment_method_id)
);

GO

CREATE TRIGGER trg_DeleteExpiredTokens
ON token
AFTER INSERT
AS
BEGIN
    DECLARE @RowCount INT;
    SET @RowCount = (SELECT COUNT(*) FROM token);

    IF @RowCount > 100
    BEGIN
        DELETE FROM token WHERE expiry_time < GETDATE();
    END
END;

GO

CREATE TRIGGER trg_DeleteExpiredInvalidateToken
ON invalidate_token
AFTER INSERT
AS
BEGIN
    DECLARE @RowCount INT;
    SET @RowCount = (SELECT COUNT(*) FROM invalidate_token);

    IF @RowCount > 100
    BEGIN
        DELETE FROM invalidate_token WHERE expiry_time < GETDATE();
    END
END;

GO


--------------------------------------------------------------------------------------
