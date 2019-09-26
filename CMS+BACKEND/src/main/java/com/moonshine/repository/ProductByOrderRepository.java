package com.moonshine.repository;

import com.moonshine.domain.ProductByOrder;
import com.moonshine.service.dto.ProductByOrderDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ProductByOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductByOrderRepository extends JpaRepository<ProductByOrder, Long> {

    List<ProductByOrder> findAllByOrderOkId(Long id);
}
