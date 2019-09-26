package com.moonshine.repository;

import com.moonshine.domain.Top;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Top entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TopRepository extends JpaRepository<Top, Long> {
    Top findAllByProducts_Id(
        @Param("id") Long id);
}
