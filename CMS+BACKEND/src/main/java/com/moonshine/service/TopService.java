package com.moonshine.service;

import com.moonshine.domain.Top;
import com.moonshine.repository.TopRepository;
import com.moonshine.service.dto.TopDTO;
import com.moonshine.service.mapper.TopMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Top.
 */
@Service
@Transactional
public class TopService {

    private final Logger log = LoggerFactory.getLogger(TopService.class);

    private final TopRepository topRepository;

    private final TopMapper topMapper;

    public TopService(TopRepository topRepository, TopMapper topMapper) {
        this.topRepository = topRepository;
        this.topMapper = topMapper;
    }

    /**
     * Save a top.
     *
     * @param topDTO the entity to save
     * @return the persisted entity
     */
    public TopDTO save(TopDTO topDTO) {
        log.debug("Request to save Top : {}", topDTO);
        Top top = topMapper.toEntity(topDTO);
        top = topRepository.save(top);
        return topMapper.toDto(top);
    }

    /**
     * Get all the tops.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TopDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tops");
        return topRepository.findAll(pageable)
            .map(topMapper::toDto);
    }


    /**
     * Get one top by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TopDTO> findOne(Long id) {
        log.debug("Request to get Top : {}", id);
        return topRepository.findById(id)
            .map(topMapper::toDto);
    }

    /**
     * Delete the top by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Top : {}", id);        topRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public TopDTO getTopByIdProduct(Long id) {
        return topMapper.toDto( topRepository.findAllByProducts_Id(id));
    }

}
