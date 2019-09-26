package com.moonshine.repository;

import com.moonshine.domain.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;



/**
 * Spring Data  repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findAllByClient_idAndStatusIsTrue(@Param("id") Long id, Pageable pageable);
    Page<Category> findAllByClient_idAndStatusIsFalse(@Param("id") Long id, Pageable pageable);
    Page<Category> findAllByClientIdAndStatusIsTrue(Long id, Pageable pageable);
}
