package com.moonshine.service;

import com.moonshine.domain.Category;
import com.moonshine.repository.CategoryRepository;
import com.moonshine.service.dto.CategoryDTO;
import com.moonshine.service.mapper.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Category.
 */
@Service
@Transactional
public class CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Save a category.
     *
     * @param categoryDTO the entity to save
     * @return the persisted entity
     */
    public CategoryDTO save(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    /**
     * Get all the categories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        return categoryRepository.findAll(pageable)
            .map(categoryMapper::toDto);
    }


    /**
     * Get one category by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CategoryDTO> findOne(Long id) {
        log.debug("Request to get Category : {}", id);
        return categoryRepository.findById(id)
            .map(categoryMapper::toDto);
    }

    /**
     * Delete the category by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);        categoryRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public Page<CategoryDTO> getCategoriesByClient(Long id, Pageable pageable) {
        return categoryRepository.findAllByClient_idAndStatusIsTrue
            (id, pageable)
            .map(categoryMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllByClient(Long id,Pageable pageable) {
        log.debug("Request to get all Extras");
        return categoryRepository.findAllByClientIdAndStatusIsTrue(id,pageable)
            .map(categoryMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> getCategoriesBystatus(Long id, boolean status, Pageable pageable) {
        return status ? categoryRepository.findAllByClient_idAndStatusIsTrue
            (id,pageable).map(categoryMapper::toDto)
            : categoryRepository.findAllByClient_idAndStatusIsFalse
            (id,pageable).map(categoryMapper::toDto);
    }
}
