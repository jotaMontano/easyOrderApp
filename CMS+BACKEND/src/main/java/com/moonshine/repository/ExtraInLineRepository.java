package com.moonshine.repository;

import com.moonshine.domain.ExtraInLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ExtraInLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtraInLineRepository extends JpaRepository<ExtraInLine, Long> {

    List<ExtraInLine> findAllByProductByOrderId(Long id);
}
