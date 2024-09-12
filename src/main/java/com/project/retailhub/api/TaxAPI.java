package com.project.retailhub.api;

import com.project.retailhub.data.dto.request.Tax.TaxRequest;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.TaxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/tax")
public class TaxAPI {

    final TaxService taxService;

    @GetMapping("/getAllActive")
    public ResponseObject<?> doGetFindAllActiveTaxes() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(taxService.findAllActiveTaxes());
        log.info("Get ALL Active Taxes");
        return resultApi;
    }

    @GetMapping("/getAll")
    public ResponseObject<?> doGetFindAllTaxes() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(taxService.findAllTaxes());
        log.info("Get ALL Taxes");
        return resultApi;
    }

    @GetMapping("/{taxId}")
    public ResponseObject<?> doGetTaxById(@PathVariable("taxId") String taxId) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(taxService.findTaxByTaxId(taxId));
        log.info("Get tax with ID " + taxId);
        return resultApi;
    }

    @PostMapping("/create")
    public ResponseObject<?> doPostCreateTax(@RequestBody TaxRequest request) {
        var resultApi = new ResponseObject<>();
        taxService.addNewTax(request);
        resultApi.setMessage("Tax added successfully");
        log.info("Added tax successfully");
        return resultApi;
    }

    @PutMapping("/update")
    public ResponseObject<?> doPostUpdateTax(@RequestBody TaxRequest request) {
        var resultApi = new ResponseObject<>();
        taxService.updateTax(request);
        resultApi.setMessage("Tax updated successfully");
        log.info("Updated tax with ID " + request.getTaxId() + " successfully");
        return resultApi;
    }

    @DeleteMapping("/delete/{taxId}")
    public ResponseObject<?> doDeleteTax(@PathVariable("taxId") String taxId) {
        var resultApi = new ResponseObject<>();
        taxService.deleteTax(taxId);
        resultApi.setMessage("Tax deleted successfully");
        log.info("Deleted tax with ID " + taxId);
        return resultApi;
    }

    @PutMapping("/restore/{taxId}")
    public ResponseObject<?> doRestoreTax(@PathVariable("taxId") String taxId) {
        var resultApi = new ResponseObject<>();
        taxService.restoreTax(taxId);
        resultApi.setMessage("Tax restored successfully");
        log.info("Restored tax with ID " + taxId);
        return resultApi;
    }
}
