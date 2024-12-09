package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.request.Receiving.ReceivingRequest;
import com.project.retailhub.data.dto.response.Receiving.ReceivingResponse;
import com.project.retailhub.data.dto.response.Supplier.SupplierResponse;
import com.project.retailhub.data.dto.response.UserResponse;
import com.project.retailhub.data.entity.Receiving;
import com.project.retailhub.data.entity.Supplier;
import com.project.retailhub.data.entity.User;
import com.project.retailhub.data.repository.SupplierRepository;
import com.project.retailhub.data.repository.TaxRepository;
import com.project.retailhub.data.repository.UserRepository;
import jdk.jfr.Name;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReceivingMapper {
    Receiving toReceiving(ReceivingRequest request);

    @Mapping(source = "supplierId", target = "supplier", qualifiedByName = "supplierToSupplierResponse")
    @Mapping(source = "userId", target = "user", qualifiedByName = "userToUserResponse")

    ReceivingResponse toReceivingResponse(Receiving receiving, @Context SupplierRepository supplierRepository, @Context UserRepository userRepository);

    @Named("supplierToSupplierResponse")
    default SupplierResponse mapSupplierToSupplierResponse(int supplierId, @Context SupplierRepository supplierRepository) {
        Supplier e = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        return SupplierResponse.builder()
                .supplierId(e.getSupplierId())
                .supplierName(e.getSupplierName())
                .supplierDescription(e.getSupplierDescription())
                .supplierPhoneNumber(e.getSupplierPhoneNumber())
                .supplierEmail(e.getSupplierEmail())
                .supplierAddress(e.getSupplierAddress())
                .isDelete(e.isDelete())
                .build();
    }

    @Named("userToUserResponse")
    default UserResponse mapUserToUserResponse(long userId, @Context UserRepository userRepository) {
        User e = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserResponse.builder()
                .userId(e.getUserId())
                .fullName(e.getFullName())
                .email(e.getEmail())
                .address(e.getAddress())
                .phoneNumber(e.getPhoneNumber())
                .image(e.getImageName())
                .startDate(e.getStartDate())
                .endDate(e.getEndDate())
                .birthday(e.getBirthday())
                .isActive(e.getIsActive())
                .isDelete(e.getIsDelete())
                .build();
    }
    List<ReceivingResponse> toReceivingResponseList(List<Receiving> receivings, @Context SupplierRepository supplierRepository, @Context UserRepository userRepository);
}
