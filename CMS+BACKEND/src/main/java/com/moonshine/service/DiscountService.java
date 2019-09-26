package com.moonshine.service;

import com.moonshine.domain.Discount;
import com.moonshine.domain.Product;
import com.moonshine.repository.DiscountRepository;
import com.moonshine.service.dto.DiscountDTO;
import com.moonshine.service.mapper.DiscountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Discount.
 */
@Service
@Transactional
public class DiscountService {

    private final Logger log = LoggerFactory.getLogger(DiscountService.class);

    private final DiscountRepository discountRepository;

    private final DiscountMapper discountMapper;

    public DiscountService(DiscountRepository discountRepository, DiscountMapper discountMapper) {
        this.discountRepository = discountRepository;
        this.discountMapper = discountMapper;
    }

    /**
     * Save a discount.
     *
     * @param discountDTO the entity to save
     * @return the persisted entity
     */
    public DiscountDTO save(DiscountDTO discountDTO) {
        log.debug("Request to save Discount : {}", discountDTO);
        Discount discount = discountMapper.toEntity(discountDTO);
        discount = discountRepository.save(discount);
        return discountMapper.toDto(discount);
    }

    /**
     * Get all the discounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DiscountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Discounts");
        return discountRepository.findAll(pageable)
            .map(discountMapper::toDto);
    }

    /**
     * Get all the Discount with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<DiscountDTO> findAllWithEagerRelationships(Pageable pageable) {
        return discountRepository.findAllWithEagerRelationships(pageable).map(discountMapper::toDto);
    }
    

    /**
     * Get one discount by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DiscountDTO> findOne(Long id) {
        log.debug("Request to get Discount : {}", id);
        return discountRepository.findOneWithEagerRelationships(id)
            .map(discountMapper::toDto);
    }

    /**
     * Delete the discount by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Discount : {}", id);        discountRepository.deleteById(id);
    }

    public Page<DiscountDTO> getDiscountByUser(Long id, Pageable pageable) {
        return discountRepository.findAllByClient_idAndStatusIsTrue
            (id, pageable)
            .map(discountMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<Discount> findAll() {
        return discountRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<DiscountDTO> findAllByClient(Long id,Pageable pageable) {
        log.debug("Request to get all Extras");
        return discountRepository.findAllByClientIdAndStatusIsTrue(id,pageable)
            .map(discountMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<DiscountDTO> findAllByProduct(Product product) {
        log.debug("Request to get all Extras");
        return discountMapper.toDto(discountRepository.findAllByProductsAndStatus(product, true));
    }

    @Transactional(readOnly = true)
    public Page<DiscountDTO> getDiscountsBystatus(Long id, boolean status, Pageable pageable) {
        return status ? discountRepository.findAllByClient_idAndStatusIsTrue
            (id,pageable).map(discountMapper::toDto)
            : discountRepository.findAllByClient_idAndStatusIsFalse
            (id,pageable).map(discountMapper::toDto);
    }
}
