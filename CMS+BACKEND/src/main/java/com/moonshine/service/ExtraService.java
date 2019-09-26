package com.moonshine.service;

import com.moonshine.domain.Extra;
import com.moonshine.domain.Product;
import com.moonshine.repository.ExtraRepository;
import com.moonshine.service.dto.ExtraDTO;
import com.moonshine.service.mapper.ExtraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Extra.
 */
@Service
@Transactional
public class ExtraService {

    private final Logger log = LoggerFactory.getLogger(ExtraService.class);

    private final ExtraRepository extraRepository;

    private final ExtraMapper extraMapper;

    public ExtraService(ExtraRepository extraRepository, ExtraMapper extraMapper) {
        this.extraRepository = extraRepository;
        this.extraMapper = extraMapper;
    }

    /**
     * Save a extra.
     *
     * @param extraDTO the entity to save
     * @return the persisted entity
     */
    public ExtraDTO save(ExtraDTO extraDTO) {
        log.debug("Request to save Extra : {}", extraDTO);
        Extra extra = extraMapper.toEntity(extraDTO);
        extra = extraRepository.save(extra);
        return extraMapper.toDto(extra);
    }

    /**
     * Get all the extras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExtraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Extras");
        return extraRepository.findAll(pageable)
            .map(extraMapper::toDto);
    }


    /**
     * Get one extra by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ExtraDTO> findOne(Long id) {
        log.debug("Request to get Extra : {}", id);
        return extraRepository.findById(id)
            .map(extraMapper::toDto);
    }

    /**
     * Delete the extra by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Extra : {}", id);
        extraRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public Page<ExtraDTO> findAllByClient(Long id,Pageable pageable) {
        log.debug("Request to get all Extras");
        return extraRepository.findAllByClientIdAndStatusIsTrue(id, pageable)
            .map(extraMapper::toDto);
    }
    @Transactional(readOnly = true)
    public List<ExtraDTO> findAllByProduct(Product product) {
        log.debug("Request to get all Extras");
        return extraMapper.toDto(extraRepository.findAllByProducts(product));
    }

    @Transactional(readOnly = true)
    public Page<ExtraDTO> getExtrasByStatus(Long id, boolean status, Pageable pageable) {
        return status ? extraRepository.findAllByClient_idAndStatusIsTrue
            (id,pageable).map(extraMapper::toDto)
            : extraRepository.findAllByClient_idAndStatusIsFalse
            (id,pageable).map(extraMapper::toDto);
    }
}
