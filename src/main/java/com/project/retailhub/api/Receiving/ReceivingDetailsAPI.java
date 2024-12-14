package com.project.retailhub.api.Receiving;

import com.project.retailhub.data.dto.request.Receiving.ReceivingDetailsRequest;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.ReceivingDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/receiving-details")
public class ReceivingDetailsAPI {

    private final ReceivingDetailsService receivingDetailsService;
    // tao moi receiving-details
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SC')")
    @PostMapping("/create")
    public ResponseObject<?> addReceivingDetails(@RequestBody ReceivingDetailsRequest request) {
        var resultApi = new ResponseObject<>();
        receivingDetailsService.addReceivingDetails(request);
        resultApi.setMessage("Receiving details added successfully");
        log.info("Added receiving details successfully");
        return resultApi;
    }

    // update receiving details
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SC')")
    @PostMapping("/update")
    public ResponseObject<?> updateReceivingDetails(@RequestBody ReceivingDetailsRequest request) {
        var resultApi = new ResponseObject<>();
        receivingDetailsService.updateReceivingDetails(request);
        resultApi.setMessage("Receiving details updated successfully");
        log.info("Updated receiving details with ID " + request.getReceivingDetailId() + " successfully");
        return resultApi;
    }

    // xoa receiving details
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SC')")
    @DeleteMapping("/delete/{receivingDetailId}")
    public ResponseObject<?> deleteReceivingDetails(@PathVariable("receivingDetailId") long receivingDetailId) {
        var resultApi = new ResponseObject<>();
        receivingDetailsService.deleteReceivingDetails(receivingDetailId);
        resultApi.setMessage("Receiving details deleted successfully");
        log.info("Deleted receiving details with ID " + receivingDetailId + " successfully");
        return resultApi;
    }

    // fill all receiving-details
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SC')")
    @GetMapping("/fillAllReceiving")
    public ResponseObject<?> getAllReceivingDetails() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(receivingDetailsService.findAllReceivingDetails());
        log.info("Fetched all receiving details successfully");
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SC')")
    @GetMapping("/findById/{receivingDetailId}")
    public ResponseObject<?> getReceivingDetailsById(@PathVariable("receivingDetailId") long receivingDetailId) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(receivingDetailsService.getAllReceivingDetailsId(receivingDetailId));
            resultApi.setMessage("Fetched receiving details successfully");
        } catch (RuntimeException e) {
            resultApi.setMessage(e.getMessage());
        }
        log.info("Fetched receiving details with ID " + receivingDetailId + " successfully");
        return resultApi;
    }

}
