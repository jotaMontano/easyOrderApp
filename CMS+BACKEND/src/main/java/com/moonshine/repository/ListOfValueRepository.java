package com.moonshine.repository;

import com.moonshine.domain.Discount;
import com.moonshine.domain.ListOfValue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


/**
 * Spring Data  repository for the ListOfValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ListOfValueRepository extends JpaRepository<ListOfValue, Long> {
    List<ListOfValue> findListOfValueByType(String lstName);
}
