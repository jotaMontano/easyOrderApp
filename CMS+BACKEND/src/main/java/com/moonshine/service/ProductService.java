package com.moonshine.service;

import com.moonshine.domain.Product;
import com.moonshine.repository.ProductRepository;
import com.moonshine.service.dto.ProductDTO;
import com.moonshine.service.mapper.ProductMapper;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Product.
 */
@Service
@Transactional
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Save a product.
     *
     * @param productDTO the entity to save
     * @return the persisted entity
     */
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    /**
     * Get all the products.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productRepository.findAll(pageable)
            .map(productMapper::toDto);
    }

    /**
     * Get all the Product with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ProductDTO> findAllWithEagerRelationships(Pageable pageable) {
        return productRepository.findAllWithEagerRelationships(pageable).map(productMapper::toDto);
    }


    /**
     * Get one product by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ProductDTO> findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return productRepository.findOneWithEagerRelationships(id)
            .map(productMapper::toDto);
    }

    /**
     * Delete the product by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> getProductsByClient(Long id, Pageable pageable) {
        return productRepository.findAllByClient_idAndStatusIsTrue
            (id, pageable)
            .map(productMapper::toDto);
    }
//    @Transactional(readOnly = true)
//    public Page<ProductDTO> getProductsByDescription(Long id, String description, Pageable pageable) {
//        return productRepository.findAllByClient_IdAndNameContainsAndStatusIsTrue
//            (id,description,pageable)
//            .map(productMapper::toDto);
//    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> getProductsByDescription(Long id, String description, Pageable pageable) {
        return productRepository.findAllByClient_IdAndNameContainingAndStatusIsTrue(
            id, description, pageable).map(productMapper::toDto);
    }
    @Transactional(readOnly = true)
    public Page<ProductDTO> getProductsByCategory(Long id, Pageable pageable) {
        return productRepository.findAllByCategoriesIdAndStatusIsTrue
            (id, pageable)
            .map(productMapper::toDto);
    }
    @Transactional(readOnly = true)
    public Page<ProductDTO> getProductsBystatus(Long id, boolean status, Pageable pageable) {
        return status ? productRepository.findAllByClient_idAndStatusIsTrue
            (id,pageable).map(productMapper::toDto)
            : productRepository.findAllByClient_idAndStatusIsFalse
            (id,pageable).map(productMapper::toDto);
    }
    @Transactional(readOnly = true)
    public Page<ProductDTO> getProductsTop(Long id, Pageable pageable) {
        return productRepository.findAllProducts
            ( id, pageable)
            .map(productMapper::toDto);
    }
}
