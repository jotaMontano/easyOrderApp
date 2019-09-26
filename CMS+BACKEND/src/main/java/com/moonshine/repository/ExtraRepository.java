package com.moonshine.repository;

import com.moonshine.domain.Extra;
import com.moonshine.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Extra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtraRepository extends JpaRepository<Extra, Long> {

    Page<Extra> findAllByClientIdAndStatusIsTrue(Long id, Pageable pageable);
    List<Extra> findAllByClientId(Long id);
    List<Extra> findAllByProducts(Product product);
    Page<Extra> findAllByClient_idAndStatusIsTrue(@Param("id") Long id, Pageable pageable);
    Page<Extra> findAllByClient_idAndStatusIsFalse(@Param("id") Long id, Pageable pageable);
}
