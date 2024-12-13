package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.Receiving;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReceivingRepository extends JpaRepository<Receiving, Long> {

    Optional<Receiving> findById(Long receivingId);

    boolean existsById(Long receivingId);

    List<Receiving> findAllByReceivingId(long receivingId);

    Page<Receiving> findAll(Pageable pageable);
}
