package com.moonshine.service;

import com.moonshine.domain.ProductByOrder;
import com.moonshine.repository.ProductByOrderRepository;
import com.moonshine.service.dto.ProductByOrderDTO;
import com.moonshine.service.mapper.ProductByOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing ProductByOrder.
 */
@Service
@Transactional
public class ProductByOrderService {

    private final Logger log = LoggerFactory.getLogger(ProductByOrderService.class);

    private final ProductByOrderRepository productByOrderRepository;

    private final ProductByOrderMapper productByOrderMapper;

    public ProductByOrderService(ProductByOrderRepository productByOrderRepository, ProductByOrderMapper productByOrderMapper) {
        this.productByOrderRepository = productByOrderRepository;
        this.productByOrderMapper = productByOrderMapper;
    }

    /**
     * Save a productByOrder.
     *
     * @param productByOrderDTO the entity to save
     * @return the persisted entity
     */
    public ProductByOrderDTO save(ProductByOrderDTO productByOrderDTO) {
        log.debug("Request to save ProductByOrder : {}", productByOrderDTO);
        ProductByOrder productByOrder = productByOrderMapper.toEntity(productByOrderDTO);
        productByOrder = productByOrderRepository.save(productByOrder);
        return productByOrderMapper.toDto(productByOrder);
    }

    /**
     * Get all the productByOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProductByOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductByOrders");
        return productByOrderRepository.findAll(pageable)
            .map(productByOrderMapper::toDto);
    }


    /**
     * Get one productByOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ProductByOrderDTO> findOne(Long id) {
        log.debug("Request to get ProductByOrder : {}", id);
        return productByOrderRepository.findById(id)
            .map(productByOrderMapper::toDto);
    }

    /**
     * Delete the productByOrder by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductByOrder : {}", id);        productByOrderRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ProductByOrderDTO> findProductsByOrder(Long id) {
        log.debug("Request to get ProductByOrder : {}", id);
        return productByOrderMapper.toDto(productByOrderRepository.findAllByOrderOkId(id));
    }
}
