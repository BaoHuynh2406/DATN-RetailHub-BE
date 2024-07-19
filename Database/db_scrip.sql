-- Bảng Invoice Items
CREATE TABLE invoice_items (
    invoice_item_id INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính tự động tăng
    invoice_id INT, -- Khóa ngoại đến bảng hóa đơn
    product_id INT, -- Khóa ngoại đến bảng sản phẩm
    quantity INT, -- Số lượng
    unit_price DECIMAL(18,2), -- Giá mỗi sản phẩm
    tax_amount DECIMAL(10,2), -- Tổng tiền thuế
);

-- Bảng Stock Check
CREATE TABLE stock_check (
    stock_check_id INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính tự động tăng
    employee_id INT, -- Khóa ngoại đến bảng nhân viên
    stock_check_date DATETIME -- Ngày kiểm
);

-- Bảng Stock Check Item
CREATE TABLE stock_check_item (
    stock_check_item_id INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính tự động tăng
    stock_check_id INT, -- Khóa ngoại đến bảng Stock Check
    product_id INT, -- Khóa ngoại đến bảng sản phẩm
    actual_quantity DECIMAL(10,2), -- Số lượng thực tế
    recorded_quantity DECIMAL(10,2), -- Số lượng trong hệ thống
    note NVARCHAR(50), -- Ghi chú

);

-- Bảng Payments
CREATE TABLE payments (
    payment_id INT IDENTITY(1,1) PRIMARY KEY, -- Khóa chính tự động tăng
    invoice_id INT, -- Khóa ngoại đến bảng hóa đơn
    payment_method_id INT, -- Khóa ngoại đến bảng phương thức thanh toán
    amount DECIMAL(18,2), -- Số tiền thanh toán
    payment_date DATETIME, -- Ngày thanh toán

);
