package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.response.Invoice.InvoiceResponse;
import com.project.retailhub.data.dto.response.Pagination.PageResponse;
import com.project.retailhub.data.entity.Discounts;
import com.project.retailhub.data.repository.DiscountsRepository;
import com.project.retailhub.service.DiscountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountsService {
    private final DiscountsRepository discountsRepository;

    @Override
    public PageResponse<Discounts> getAllDiscounts() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Discounts> data = discountsRepository.findAll(pageable);
        return PageResponse.<Discounts>builder()
                .totalPages(data.getTotalPages())
                .pageSize(data.getSize())
                .currentPage(1)
                .totalElements(data.getTotalElements())
                .data(data.getContent())
                .build();
    }

    @Override
    public List<Discounts> getAllDiscountsAvailable() {
        Date currentDate = new Date();
        return discountsRepository.findActiveDiscounts(currentDate);
    }

    @Override
    public Discounts getDiscountByProductIdAvailable(long productId) {
        Date currentDate = new Date();
        return discountsRepository.findActiveDiscountsByProductId(currentDate, productId).orElse(null);
    }

    @Override
    public void addDiscount(Discounts discount) {
        discountsRepository.save(discount);
    }

    @Override
    public void deleteDiscount(Long id) {
        discountsRepository.deleteById(id);
    }

    @Override
    public Discounts updateDiscount(Discounts updatedDiscount) {
        return discountsRepository.save(updatedDiscount);
    }
}
