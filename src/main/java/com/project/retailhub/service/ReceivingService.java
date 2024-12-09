package com.project.retailhub.service;

import com.project.retailhub.data.dto.request.Receiving.ReceivingRequest;
import com.project.retailhub.data.dto.response.Receiving.ReceivingResponse;

import java.util.List;

public interface ReceivingService {
    void addReceiving(ReceivingRequest request);
    void updateReceiving(ReceivingRequest request);
    void deleteReceiving(long receivingId);
    List<ReceivingResponse> findAllReceiving();
    List<ReceivingResponse> getAllReceivingId(long receivingId);
    ReceivingResponse findReceivingById(long receivingId);
}
