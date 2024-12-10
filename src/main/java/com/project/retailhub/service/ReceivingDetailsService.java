package com.project.retailhub.service;

import com.project.retailhub.data.dto.request.Receiving.ReceivingDetailsRequest;
import com.project.retailhub.data.dto.response.Receiving.ReceivingDetailsResponse;

import java.util.List;

public interface ReceivingDetailsService {
    void addReceivingDetails(ReceivingDetailsRequest request);

    void updateReceivingDetails(ReceivingDetailsRequest request);

    void deleteReceivingDetails(Long receivingDetailId);

    List<ReceivingDetailsResponse> findAllReceivingDetails();

    List<ReceivingDetailsResponse> getAllReceivingDetailsId(long receivingDetailId);
}
