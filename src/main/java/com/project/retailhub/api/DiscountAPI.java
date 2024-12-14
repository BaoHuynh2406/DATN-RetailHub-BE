package com.project.retailhub.api;


import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.data.entity.Discounts;
import com.project.retailhub.service.DiscountsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/discount")
public class DiscountAPI {
    private final DiscountsService discountService;

    @GetMapping("/all")
    public ResponseObject<?> doGetFindAllActiveCategories(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                          @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(discountService.getAllDiscounts(page, size));
        log.info("Get ALL Discounts");
        return resultApi;
    }

    @GetMapping("/{id}")
    public ResponseObject<?> getById(
            @PathVariable("id") Long id
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(discountService.getById(id));
        log.info("Get discount by Id");
        return resultApi;
    }

    @PostMapping("/create")
    public ResponseObject<?> addReceiving(@RequestBody Discounts request) {
        var resultApi = new ResponseObject<Void>();
        discountService.addDiscount(request);
        resultApi.setMessage("Successfully added");
        log.info("Add discount successfully");
        return resultApi;
    }

    // Cập nhật Receiving
    @PutMapping("/update")
    public ResponseObject<?> updateReceiving(@RequestBody Discounts request) {
        var resultApi = new ResponseObject<>();
        discountService.updateDiscount(request);
        resultApi.setMessage("Discounts updated successfully");
        log.info("Update Discounts with ID " + request.getId() + " successfully");
        return resultApi;
    }

    // Xóa Receiving
    @DeleteMapping("/delete/{id}")
    public ResponseObject<?> deleteReceiving(@PathVariable("id") long receivingId) {
        var resultApi = new ResponseObject<>();
        discountService.deleteDiscount(receivingId);
        resultApi.setMessage("Discounts deleted successfully");
        log.info("Deleted Discounts with ID " + receivingId + " successfully");
        return resultApi;
    }
}
