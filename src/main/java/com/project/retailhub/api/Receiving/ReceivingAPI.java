package com.project.retailhub.api.Receiving;

import com.project.retailhub.data.dto.request.Receiving.ReceivingRequest;
import com.project.retailhub.data.dto.response.Receiving.ReceivingResponse;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.service.ReceivingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/receiving")
public class ReceivingAPI {
    private final ReceivingService receivingService;

    // Tạo mới Receiving
    @PostMapping("/create")
    public ResponseObject<?> addReceiving(@RequestBody ReceivingRequest request) {
        var resultApi = new ResponseObject<Void>();
        receivingService.addReceiving(request);
        resultApi.setMessage("Receiving added successfully");
        log.info("Add receiving successfully");
        return resultApi;
    }

    // Cập nhật Receiving
    @PutMapping("/update")
    public ResponseObject<?> updateReceiving(@RequestBody ReceivingRequest request) {
        var resultApi = new ResponseObject<>();
        receivingService.updateReceiving(request);
        resultApi.setMessage("Receiving updated successfully");
        log.info("Update receiving with ID " + request.getReceivingId() + " successfully");
        return resultApi;
    }

    // Xóa Receiving
    @DeleteMapping("/delete/{receivingId}")
    public ResponseObject<?> deleteReceiving(@PathVariable("receivingId") long receivingId) {
        var resultApi = new ResponseObject<>();
        receivingService.deleteReceiving(receivingId);
        resultApi.setMessage("Receiving deleted successfully");
        log.info("Deleted receiving with ID " + receivingId + " successfully");
        return resultApi;
    }

    // Lấy danh sách tất cả Receiving
    @GetMapping("/findAllReceiving")
    public ResponseObject<?> findAllReceiving() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(receivingService.findAllReceiving());
        log.info("Fetched all receiving");
        return resultApi;
    }

    // Lấy Receiving theo ID
    @GetMapping("/findById/{receivingId}")
    public ResponseObject<?> findReceivingById(@PathVariable("receivingId") long receivingId) {
        var resultApi = new ResponseObject<List<ReceivingResponse>>();
        try {
            resultApi.setData(receivingService.getAllReceivingId(receivingId));
            log.info("Fetched receiving with ID " + receivingId);
        } catch (AppException e) {
            resultApi.setCode(e.getErrorCode().getCode());
            resultApi.setMessage(e.getMessage());
        }
        log.info("Fetched receiving with ID " + receivingId);
        return resultApi;
    }
}
