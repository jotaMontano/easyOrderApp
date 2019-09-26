package com.moonshine.repository;

import com.moonshine.domain.Discount;
import com.moonshine.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Discount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Query(value = "select distinct discount from Discount discount left join fetch discount.days",
        countQuery = "select count(distinct discount) from Discount discount")
    Page<Discount> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct discount from Discount discount left join fetch discount.days")
    List<Discount> findAllWithEagerRelationships();

    @Query("select discount from Discount discount left join fetch discount.days where discount.id =:id")
    Optional<Discount> findOneWithEagerRelationships(@Param("id") Long id);

    Page<Discount> findAllByClient_idAndStatusIsTrue(@Param("id") Long id, Pageable pageable);
    Page<Discount> findAllByClient_idAndStatusIsFalse(@Param("id") Long id, Pageable pageable);

    List<Discount> findAll();

    Page<Discount> findAllByClientIdAndStatusIsTrue(Long id, Pageable pageable);

    List<Discount> findAllByProductsAndStatus(Product product, @NotNull Boolean status);
}
