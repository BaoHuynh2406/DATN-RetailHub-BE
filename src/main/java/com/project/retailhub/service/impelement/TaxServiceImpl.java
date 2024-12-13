package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.request.Tax.TaxRequest;
import com.project.retailhub.data.dto.response.Tax.TaxResponse;
import com.project.retailhub.data.entity.Tax;
import com.project.retailhub.data.mapper.TaxMapper;
import com.project.retailhub.data.repository.TaxRepository;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import com.project.retailhub.service.TaxService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaxServiceImpl implements TaxService {

    TaxRepository taxRepository;
    TaxMapper taxMapper;

    @Override
    public void addNewTax(TaxRequest request) {
        if (taxRepository.existsByTaxName(request.getTaxName()))
            throw new AppException(ErrorCode.TAXNAME_ALREADY_EXIST);

        // Thực hiện chuyển đổi request thành entity
        Tax tax = taxMapper.toTax(request);
        tax.setTaxRate(request.getTaxRate()/100);
        taxRepository.save(tax);
    }

    @Override
    public void updateTax(TaxRequest request) {
        String taxId = request.getTaxId();  // Chỉnh sửa để lấy taxId dạng String
        if (taxId == null || taxId.isEmpty()) {
            throw new AppException(ErrorCode.TAX_ID_NULL);
        }
        // Tìm kiếm tax theo ID
        Tax tax = taxRepository.findById(taxId)
                .orElseThrow(() -> new AppException(ErrorCode.TAX_NOT_FOUND));

        // Cập nhật thông tin tax
        tax.setTaxName(request.getTaxName());
        tax.setTaxRate(request.getTaxRate()/100);

        // Lưu thông tin tax đã cập nhật
        taxRepository.save(tax);
    }

    @Override
    public void deleteTax(String taxId) {
       taxRepository.deleteById(taxId);
    }

    @Override
    public void restoreTax(String taxId) {
        Tax tax = taxRepository.findById(taxId)
                .orElseThrow(() -> new AppException(ErrorCode.TAX_NOT_FOUND));
        tax.setIsDelete(false);
        taxRepository.save(tax);
    }

    @Override
    public List<TaxResponse> findAllActiveTaxes()   {
        return taxMapper.toTaxResponseList(taxRepository.findAllByIsDelete(false));
    }

    @Override
    public List<TaxResponse> findAllTaxes() {
        return taxMapper.toTaxResponseList(taxRepository.findAll());
    }

    @Override
    public TaxResponse findTaxByTaxId(String taxId) {
        Tax tax = taxRepository.findById(taxId)
                .orElseThrow(() -> new AppException(ErrorCode.TAX_NOT_FOUND));
        return taxMapper.toTaxResponse(tax);
    }
}
