package com.moonshine.service;

import com.moonshine.domain.ListOfValue;
import com.moonshine.repository.ListOfValueRepository;
import com.moonshine.service.dto.ListOfValueDTO;
import com.moonshine.service.mapper.ListOfValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing ListOfValue.
 */
@Service
@Transactional
public class ListOfValueService {

    private final Logger log = LoggerFactory.getLogger(ListOfValueService.class);

    private final ListOfValueRepository listOfValueRepository;

    private final ListOfValueMapper listOfValueMapper;

    public ListOfValueService(ListOfValueRepository listOfValueRepository, ListOfValueMapper listOfValueMapper) {
        this.listOfValueRepository = listOfValueRepository;
        this.listOfValueMapper = listOfValueMapper;
    }

    /**
     * Save a listOfValue.
     *
     * @param listOfValueDTO the entity to save
     * @return the persisted entity
     */
    public ListOfValueDTO save(ListOfValueDTO listOfValueDTO) {
        log.debug("Request to save ListOfValue : {}", listOfValueDTO);
        ListOfValue listOfValue = listOfValueMapper.toEntity(listOfValueDTO);
        listOfValue = listOfValueRepository.save(listOfValue);
        return listOfValueMapper.toDto(listOfValue);
    }

    /**
     * Get all the listOfValues.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ListOfValueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ListOfValues");
        return listOfValueRepository.findAll(pageable)
            .map(listOfValueMapper::toDto);
    }


    /**
     * Get one listOfValue by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ListOfValueDTO> findOne(Long id) {
        log.debug("Request to get ListOfValue : {}", id);
        return listOfValueRepository.findById(id)
            .map(listOfValueMapper::toDto);
    }

    /**
     * Delete the listOfValue by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ListOfValue : {}", id);        listOfValueRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<ListOfValueDTO> getListProduct(String lstname) {
        return listOfValueMapper.toDto(listOfValueRepository.findListOfValueByType(lstname));
    }
}
