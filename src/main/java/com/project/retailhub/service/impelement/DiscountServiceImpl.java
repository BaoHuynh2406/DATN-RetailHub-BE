package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.response.DiscountResponse;
import com.project.retailhub.data.dto.response.Pagination.PageResponse;
import com.project.retailhub.data.entity.Discounts;
import com.project.retailhub.data.entity.Product;
import com.project.retailhub.data.mapper.DiscountMapper;
import com.project.retailhub.data.repository.DiscountsRepository;
import com.project.retailhub.data.repository.ProductRepository;
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
    private final DiscountMapper discountMapper;
    private final ProductRepository productRepository;

    @Override
    public PageResponse<DiscountResponse> getAllDiscounts(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size,  Sort.by(Sort.Direction.DESC, "id"));

        Page<Discounts> data = discountsRepository.findAll(pageable);
        List<DiscountResponse> result = data.getContent().stream().map(discountMapper::toDiscountResponse).toList();
        for (DiscountResponse response : result) {
            Product p = productRepository.findById(response.getProductId()).orElseThrow(
                    () -> new RuntimeException("Product not found with id " + response.getProductId())
            );

            response.setProductName(p.getProductName());
            response.setPrice(p.getPrice());
            response.setImage(p.getImage());
        }


        return PageResponse.<DiscountResponse>builder()
                .totalPages(data.getTotalPages())
                .pageSize(data.getSize())
                .currentPage(1)
                .totalElements(data.getTotalElements())
                .data(result)
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
        // Lấy danh sách các Discounts theo productId
        List<Discounts> discountsList = discountsRepository.findFirstActiveDiscountByProductId(currentDate, productId);
        // Trả về giá trị đầu tiên nếu danh sách không rỗng, ngược lại trả về null
        return discountsList.isEmpty() ? null : discountsList.getFirst();
    }

    @Override
    public void addDiscount(Discounts discount) {
        String validationError = validateDiscount(discount);
        if (validationError != null) {
            throw new IllegalArgumentException(validationError);
        }

        discountsRepository.save(discount);
    }

    @Override
    public void deleteDiscount(Long id) {
        discountsRepository.deleteById(id);
    }

    @Override
    public void updateDiscount(Discounts discount) {
        String validationError = validateDiscount(discount);
        if (validationError != null) {
            throw new IllegalArgumentException(validationError);
        }

        discountsRepository.save(discount);
    }

    public String validateDiscount(Discounts discount) {
        // Kiểm tra ngày bắt đầu và ngày kết thúc
        if (discount.getStartDate().after(discount.getEndDate())) {
            return "Ngày bắt đầu không được lớn hơn ngày kết thúc.";
        }

        // Kiểm tra tỷ lệ giảm giá
        if (discount.getDiscountRate() < 0 || discount.getDiscountRate() > 100) {
            return "Tỷ lệ giảm giá phải nằm trong khoảng từ 0 đến 100.";
        }


        return null; // Nếu tất cả đều hợp lệ
    }
}
