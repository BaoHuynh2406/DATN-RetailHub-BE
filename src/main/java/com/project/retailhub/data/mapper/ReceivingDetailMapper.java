package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.request.Receiving.ReceivingDetailsRequest;
import com.project.retailhub.data.dto.response.Receiving.ReceivingDetailsResponse;
import com.project.retailhub.data.dto.response.Receiving.ReceivingResponse;
import com.project.retailhub.data.dto.response.product.ProductResponse;
import com.project.retailhub.data.entity.Product;
import com.project.retailhub.data.entity.Receiving;
import com.project.retailhub.data.entity.ReceivingDetail;
import com.project.retailhub.data.repository.ProductRepository;
import com.project.retailhub.data.repository.ReceivingRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReceivingDetailMapper {
    ReceivingDetail toReceivingDetail(ReceivingDetailsRequest request);

    @Mapping(source = "receivingId", target = "receiving", qualifiedByName = "receivingToReceivingResponse")
    @Mapping(source = "productId", target = "product", qualifiedByName = "productToProductResponse")
    ReceivingDetailsResponse toReceivingDetailsResponse(ReceivingDetail receivingDetail,
                                                        @Context ReceivingRepository receivingRepository,
                                                        @Context ProductRepository productRepository);

    @Named("receivingToReceivingResponse")
    default ReceivingResponse mapReceivingToReceivingResponse(Long receivingId, @Context ReceivingRepository receivingRepository) {
        return receivingRepository.findById(receivingId)
                .map(receiving -> ReceivingResponse.builder()
                        .receivingId(receiving.getReceivingId())
                        .receivingDate(receiving.getReceivingDate())
                        .build())
                .orElseThrow(() -> new RuntimeException("Receiving not found"));
    }

    @Named("productToProductResponse")
    default ProductResponse mapProductToProductResponse(Long productId, @Context ProductRepository productRepository) {
        return productRepository.findById(productId)
                .map(product -> ProductResponse.builder()
                        .productId(product.getProductId())
                        .productName(product.getProductName())
                        .build())
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    List<ReceivingDetailsResponse> toReceivingDetailsResponseList(List<ReceivingDetail> receivingDetails,
                                                                  @Context ReceivingRepository receivingRepository,
                                                                  @Context ProductRepository productRepository);
}
