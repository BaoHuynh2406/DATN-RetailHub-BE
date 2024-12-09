package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.request.Receiving.ReceivingRequest;
import com.project.retailhub.data.dto.response.Receiving.ReceivingResponse;
import com.project.retailhub.data.entity.Receiving;
import com.project.retailhub.data.mapper.ReceivingMapper;
import com.project.retailhub.data.repository.ReceivingRepository;
import com.project.retailhub.data.repository.SupplierRepository;
import com.project.retailhub.data.repository.UserRepository;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.service.ReceivingService;
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
public class ReceivingServiceImpl implements ReceivingService {

    private static final Logger log = LoggerFactory.getLogger(ReceivingServiceImpl.class);
    ReceivingRepository receivingRepository;
    SupplierRepository supplierRepository;
    UserRepository userRepository;
    ReceivingMapper receivingMapper;

    @Override
    public void addReceiving(ReceivingRequest request) {
        if (receivingRepository.existsById(request.getReceivingId()))
            throw new RuntimeException("Receiving ID already exists");
        // Chuyển đổi request thành entity
        Receiving receiving = receivingMapper.toReceiving(request);
        receivingRepository.save(receiving);
    }

    @Override
    public void updateReceiving(ReceivingRequest request) {
        // Kiem tra id null hay ko
        long receivingId = request.getReceivingId();
        if(receivingId <= 0) {
            throw new RuntimeException("ID NULL.");
        }
        // Tim kiem receiving theo id
        Receiving receiving = receivingRepository.findById(receivingId)
                .orElseThrow(() -> new RuntimeException("Receiving not found"));
        // Cap nhat thong tin
        receiving.setSupplierId(request.getSupplierId());
        receiving.setUserId(request.getUserId());
        receiving.setReceivingDate(request.getReceivingDate());
        receivingRepository.save(receiving);
    }

    @Override
    public void deleteReceiving(long receivingId) {
        Receiving receiving = receivingRepository.findById(receivingId)
                .orElseThrow(() -> new RuntimeException("Receiving not found"));
        receivingRepository.delete(receiving);
    }

    @Override
    public List<ReceivingResponse> findAllReceiving() {
        return receivingMapper.toReceivingResponseList(receivingRepository.findAll(), supplierRepository, userRepository);
    }

    @Override
    public List<ReceivingResponse> getAllReceivingId(long receivingId) {
        List<Receiving> receivings = receivingRepository.findAllByReceivingId(receivingId);
        if (receivings.isEmpty()) {
            throw new RuntimeException("Rong");
        }
        return receivingMapper.toReceivingResponseList(receivings, supplierRepository, userRepository);
    }

    @Override
    public ReceivingResponse findReceivingById(long receivingId) {
        Receiving receiving = receivingRepository.findById(receivingId)
                .orElseThrow(() -> new RuntimeException("Receiving not found"));
        return receivingMapper.toReceivingResponse(receiving, supplierRepository, userRepository);
    }
}
