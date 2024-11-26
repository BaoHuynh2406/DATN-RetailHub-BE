package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.request.Payment.HandlePaymentRequest;
import com.project.retailhub.data.entity.Invoice;
import com.project.retailhub.data.entity.Payment;
import com.project.retailhub.data.mapper.PaymentMapper;
import com.project.retailhub.data.repository.InvoiceRepository;
import com.project.retailhub.data.repository.PaymentRepository;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import com.project.retailhub.service.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentServiceImpl implements PaymentService {
    InvoiceRepository invoiceRepository;
    PaymentMapper paymentMapper;
    PaymentRepository paymentRepository;

    @Override
    public void handlePaymentCash(HandlePaymentRequest request) {
        //Kiem tra hoa don da thanh toan chua, neu roi bao loi hoa don da thanh toan.
        Invoice invoice = invoiceRepository.findById(request.getInvoiceId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        if(!invoice.getStatus().equals("PENDING")){
            throw new AppException(ErrorCode.PAYMENT_REJECT);
        }
        if(request.getAmount().compareTo(BigDecimal.valueOf(0))<0){
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
        if(invoice.getTotalPayment().compareTo(invoice.getFinalTotal())<0){
            invoice.setStatus("PENDING");
        }else{
            invoice.setStatus("PAID");
            //tich diem cho khach hang
            // bo sung ham tich diem
        }
        //Cap nhat lai hoa don
        invoiceRepository.save(invoice);
    }

}
