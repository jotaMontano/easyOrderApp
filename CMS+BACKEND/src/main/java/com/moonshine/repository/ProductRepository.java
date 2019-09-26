package com.moonshine.repository;

import com.moonshine.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select distinct product from Product product left join fetch product.extras left join fetch product.combos left join fetch product.discounts",
        countQuery = "select count(distinct product) from Product product")
    Page<Product> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct product from Product product left join fetch product.extras left join fetch product.combos left join fetch product.discounts")
    List<Product> findAllWithEagerRelationships();

    @Query("select product from Product product left join fetch product.extras left join fetch product.combos left join fetch product.discounts where product.id =:id")
    Optional<Product> findOneWithEagerRelationships(@Param("id") Long id);

    Page<Product> findAllByClient_idAndStatusIsTrue(@Param("id") Long id, Pageable pageable);

    Page<Product> findAllByClient_idAndStatusIsTrueAndTypeEquals(@Param("id") Long id, @Param("type") String type, Pageable pageable);

    Page<Product> findAllByCategoriesIdAndStatusIsTrue(@Param("id") Long id, Pageable pageable);

    Page<Product> findAllByClient_IdAndNameContainingAndStatusIsTrue(
        @Param("id") Long id, @Param("name") String description, Pageable pageable);


    Page<Product> findAllByClient_idAndStatusIsFalse(
        @Param("id") Long id, Pageable pageable);

    @Query(value = "select distinct product from Product product " +
        "inner join product.tops where product.client.id = :id and product.status = true")
    Page<Product> findAllProducts(@Param("id") Long id, Pageable pageable);
}
