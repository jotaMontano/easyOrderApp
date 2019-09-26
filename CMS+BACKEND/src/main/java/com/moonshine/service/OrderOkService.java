package com.moonshine.service;

import com.moonshine.domain.OrderOk;
import com.moonshine.repository.OrderOkRepository;
import com.moonshine.service.dto.OrderOkDTO;
import com.moonshine.service.mapper.OrderOkMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing OrderOk.
 */
@Service
@Transactional
public class OrderOkService {

    private final Logger log = LoggerFactory.getLogger(OrderOkService.class);

    private final OrderOkRepository orderOkRepository;

    private final OrderOkMapper orderOkMapper;

    public OrderOkService(OrderOkRepository orderOkRepository, OrderOkMapper orderOkMapper) {
        this.orderOkRepository = orderOkRepository;
        this.orderOkMapper = orderOkMapper;
    }

    /**
     * Save a orderOk.
     *
     * @param orderOkDTO the entity to save
     * @return the persisted entity
     */
    public OrderOkDTO save(OrderOkDTO orderOkDTO) {
        log.debug("Request to save OrderOk : {}", orderOkDTO);
        OrderOk orderOk = orderOkMapper.toEntity(orderOkDTO);
        orderOk = orderOkRepository.save(orderOk);
        return orderOkMapper.toDto(orderOk);
    }

    /**
     * Get all the orderOks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OrderOkDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrderOks");
        return orderOkRepository.findAll(pageable)
            .map(orderOkMapper::toDto);
    }


    /**
     * Get one orderOk by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<OrderOkDTO> findOne(Long id) {
        log.debug("Request to get OrderOk : {}", id);
        return orderOkRepository.findById(id)
            .map(orderOkMapper::toDto);
    }

    /**
     * Delete the orderOk by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderOk : {}", id);        orderOkRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<OrderOkDTO> findOrdersByClient(Long id, String filter) {
        log.debug("Request to get all OrderOks");
        if (filter.equals("Pendiente")){
            return  orderOkMapper.toDto(orderOkRepository.findAllByClientIdAndStatusIsFalse(id));
        }else if (filter.equals("Entregado")){
            return orderOkMapper.toDto(orderOkRepository.findAllByClientIdAndStatusIsTrue(id));
        }
        return orderOkMapper.toDto(orderOkRepository.findAllByClientId(id));
    }
}
