package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.request.Payment.HandlePaymentRequest;
import com.project.retailhub.data.dto.request.TransactionRequest;
import com.project.retailhub.data.entity.Invoice;
import com.project.retailhub.data.entity.InvoiceItem;
import com.project.retailhub.data.entity.Payment;
import com.project.retailhub.data.mapper.PaymentMapper;
import com.project.retailhub.data.repository.InvoiceItemRepository;
import com.project.retailhub.data.repository.InvoiceRepository;
import com.project.retailhub.data.repository.PaymentRepository;
import com.project.retailhub.data.repository.ProductRepository;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import com.project.retailhub.service.PaymentService;
import com.project.retailhub.service.PointHistoryService;
import com.project.retailhub.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentServiceImpl implements PaymentService {
    InvoiceRepository invoiceRepository;
    InvoiceItemRepository invoiceItemRepository;
    ProductService productService;
    PaymentMapper paymentMapper;
    PaymentRepository paymentRepository;
    PointHistoryService pointHistoryService;

    @Override
    public void handlePaymentCash(HandlePaymentRequest request) {
        //Kiem tra hoa don da thanh toan chua, neu roi bao loi hoa don da thanh toan.
        Invoice invoice = invoiceRepository.findById(request.getInvoiceId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        if (!invoice.getStatus().equals("PENDING")) {
            throw new AppException(ErrorCode.PAYMENT_REJECT);
        }
        if (request.getAmount().compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new AppException(ErrorCode.PAYMENT_REJECT);
        }
        //luu thong tin payment lại
        Payment payment = paymentMapper.toPayment(request);
        // khoi tao thoi gian hien tai cho hoa don
        payment.setPaymentDate(new Date());
        paymentRepository.save(payment);
        //cập nhật số tiền payment trong hoa don
        invoice.setTotalPayment(invoice.getTotalPayment().add(request.getAmount()));
        //Kiễm tra số tiền đã thanh toán nếu >= số tiền hóa đơn thì  cập nhật trạng thái hóa đơn PAID
        if (invoice.getTotalPayment().compareTo(invoice.getFinalTotal()) < 0) {
            invoice.setStatus("PENDING");
        } else {
            //trừ tồn kho
            handleChekAndSubtractStock(invoice.getInvoiceId());
            //Tính điểm và lưu vào bảng điểm
            invoice.setStatus("PAID");
            invoice.setInvoiceDate(new Date());
            TransactionRequest tichDiem = new TransactionRequest();
            tichDiem.setUserId(invoice.getUserId());
            tichDiem.setInvoiceId(invoice.getInvoiceId());
            tichDiem.setCustomerId(invoice.getCustomerId());
            tichDiem.setDescription("Tich Diem Cho Hoa Don " + invoice.getInvoiceId());
            BigDecimal points = invoice.getFinalTotal().multiply(BigDecimal.valueOf(0.01));
            tichDiem.setPoints(points.intValue());
            pointHistoryService.createTransaction(tichDiem);
        }
        //Cap nhat lai hoa don
        invoiceRepository.save(invoice);
    }


    //Kiễm tra & trừ tồn kho
    private void handleChekAndSubtractStock(Long invoiceId) {
        //Lấy ra List Item trong hóa đơn
        List<InvoiceItem> itemsOfInvoice = invoiceItemRepository.findByInvoiceId(invoiceId);
        if (itemsOfInvoice.isEmpty()) {
            throw new RuntimeException("San pham trong hoa don rong!");
        }

        //Kiểm tra & trừ tồn kho
        for (InvoiceItem item : itemsOfInvoice) {
            Long productId = item.getProductId();
            int quantity = item.getQuantity();
            //Lấy số lượng của sản phẩm
            BigDecimal currentQuantity = productService.getCurrentQuantity(productId);
            if (currentQuantity.intValue() < quantity) {
                throw new RuntimeException("Không đủ tồn kho!");
            }
            //Trừ số lượng sản phẩm trong kho
            productService.subtractQuantity(productId, quantity);
        }
    }
}
