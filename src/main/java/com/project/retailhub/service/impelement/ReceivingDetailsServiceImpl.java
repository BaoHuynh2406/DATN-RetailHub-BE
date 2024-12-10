package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.request.Receiving.ReceivingDetailsRequest;
import com.project.retailhub.data.dto.response.Receiving.ReceivingDetailsResponse;
import com.project.retailhub.data.entity.ReceivingDetail;
import com.project.retailhub.data.mapper.ReceivingDetailMapper;
import com.project.retailhub.data.repository.ProductRepository;
import com.project.retailhub.data.repository.ReceivingDetailRepository;
import com.project.retailhub.data.repository.ReceivingRepository;
import com.project.retailhub.service.ReceivingDetailsService;
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
public class ReceivingDetailsServiceImpl implements ReceivingDetailsService {

    static final Logger log = LoggerFactory.getLogger(ReceivingDetailsServiceImpl.class);

    ReceivingDetailRepository receivingDetailRepository;
    ProductRepository productRepository;
    ReceivingRepository receivingRepository;
    ReceivingDetailMapper receivingDetailMapper;

    @Override
    public void addReceivingDetails(ReceivingDetailsRequest request) {
        if (receivingDetailRepository.existsById(request.getReceivingDetailId())) {
            throw new RuntimeException("ReceivingDetails ID already exists");
        }

        ReceivingDetail receivingDetail = receivingDetailMapper.toReceivingDetail(request);
        receivingDetailRepository.save(receivingDetail);
        log.info("Added ReceivingDetail with ID {}", request.getReceivingDetailId());
    }

    @Override
    public void updateReceivingDetails(ReceivingDetailsRequest request) {
        long receivingDetailId = request.getReceivingDetailId();
        if (receivingDetailId <= 0) {
            throw new RuntimeException("ReceivingDetail ID is invalid");
        }

        ReceivingDetail receivingDetail = receivingDetailRepository.findById(receivingDetailId)
                .orElseThrow(() -> new RuntimeException("ReceivingDetails ID not found"));

        receivingDetail.setQuantity(request.getQuantity());
        receivingDetail.setReceivingCost(request.getReceivingCost());
        receivingDetailRepository.save(receivingDetail);
        log.info("Updated ReceivingDetail with ID {}", receivingDetailId);
    }

    @Override
    public void deleteReceivingDetails(Long receivingDetailId) {
        ReceivingDetail receivingDetail = receivingDetailRepository.findById(receivingDetailId)
                .orElseThrow(() -> new RuntimeException("ReceivingDetails ID not found"));

        receivingDetailRepository.delete(receivingDetail);
        log.info("Deleted ReceivingDetail with ID {}", receivingDetailId);
    }

    @Override
    public List<ReceivingDetailsResponse> findAllReceivingDetails() {
        List<ReceivingDetail> receivingDetails = receivingDetailRepository.findAll();
        return receivingDetailMapper.toReceivingDetailsResponseList(receivingDetails, receivingRepository, productRepository);
    }

    @Override
    public List<ReceivingDetailsResponse> getAllReceivingDetailsId(long receivingDetailId) {
        List<ReceivingDetail> receivingDetails = receivingDetailRepository.findByReceivingDetailId(receivingDetailId);
        if (receivingDetails.isEmpty()) {
            throw new RuntimeException("ReceivingDetails not found with ID " + receivingDetailId);
        }
        return receivingDetailMapper.toReceivingDetailsResponseList(receivingDetails, receivingRepository, productRepository);
    }
}
