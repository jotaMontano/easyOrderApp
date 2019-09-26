package com.moonshine.service;

import com.moonshine.domain.ExtraInLine;
import com.moonshine.repository.ExtraInLineRepository;
import com.moonshine.service.dto.ExtraInLineDTO;
import com.moonshine.service.mapper.ExtraInLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing ExtraInLine.
 */
@Service
@Transactional
public class ExtraInLineService {

    private final Logger log = LoggerFactory.getLogger(ExtraInLineService.class);

    private final ExtraInLineRepository extraInLineRepository;

    private final ExtraInLineMapper extraInLineMapper;

    public ExtraInLineService(ExtraInLineRepository extraInLineRepository, ExtraInLineMapper extraInLineMapper) {
        this.extraInLineRepository = extraInLineRepository;
        this.extraInLineMapper = extraInLineMapper;
    }

    /**
     * Save a extraInLine.
     *
     * @param extraInLineDTO the entity to save
     * @return the persisted entity
     */
    public ExtraInLineDTO save(ExtraInLineDTO extraInLineDTO) {
        log.debug("Request to save ExtraInLine : {}", extraInLineDTO);
        ExtraInLine extraInLine = extraInLineMapper.toEntity(extraInLineDTO);
        extraInLine = extraInLineRepository.save(extraInLine);
        return extraInLineMapper.toDto(extraInLine);
    }

    /**
     * Get all the extraInLines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExtraInLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExtraInLines");
        return extraInLineRepository.findAll(pageable)
            .map(extraInLineMapper::toDto);
    }


    /**
     * Get one extraInLine by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ExtraInLineDTO> findOne(Long id) {
        log.debug("Request to get ExtraInLine : {}", id);
        return extraInLineRepository.findById(id)
            .map(extraInLineMapper::toDto);
    }

    /**
     * Delete the extraInLine by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ExtraInLine : {}", id);
        extraInLineRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<ExtraInLineDTO> findExtrasInLine(Long id) {
        log.debug("Request to get ProductByOrder : {}", id);
        return extraInLineMapper.toDto(extraInLineRepository.findAllByProductByOrderId(id));
    }
}
