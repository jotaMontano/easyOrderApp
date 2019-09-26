package com.moonshine.repository;

import com.moonshine.domain.OrderOk;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the OrderOk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderOkRepository extends JpaRepository<OrderOk, Long> {

    List<OrderOk> findAllByClientId(Long id);
    List<OrderOk> findAllByClientIdAndStatusIsFalse(Long id);
    List<OrderOk> findAllByClientIdAndStatusIsTrue(Long id);
}
