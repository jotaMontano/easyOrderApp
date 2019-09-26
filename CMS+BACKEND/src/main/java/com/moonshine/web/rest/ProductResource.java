package com.moonshine.web.rest;
import com.moonshine.service.ProductService;
import com.moonshine.web.rest.errors.BadRequestAlertException;
import com.moonshine.web.rest.util.HeaderUtil;
import com.moonshine.web.rest.util.PaginationUtil;
import com.moonshine.service.dto.ProductDTO;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Product.
 */
@RestController
@RequestMapping("/api")
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    private static final String ENTITY_NAME = "product";

    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    /**
     * POST  /products : Create a new product.
     *
     * @param productDTO the productDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productDTO, or with status 400 (Bad Request) if the product has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) throws URISyntaxException {
        log.debug("REST request to save Product : {}", productDTO);
        if (productDTO.getId() != null) {
            throw new BadRequestAlertException("A new product cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductDTO result = productService.save(productDTO);
        return ResponseEntity.created(new URI("/api/products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /products : Updates an existing product.
     *
     * @param productDTO the productDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productDTO,
     * or with status 400 (Bad Request) if the productDTO is not valid,
     * or with status 500 (Internal Server Error) if the productDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/products")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO) throws URISyntaxException {
        log.debug("REST request to update Product : {}", productDTO);
        if (productDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductDTO result = productService.save(productDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /products : get all the products.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of products in body
     */
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Products");
        Page<ProductDTO> page;
        if (eagerload) {
            page = productService.findAllWithEagerRelationships(pageable);
        } else {
            page = productService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/products?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /products/:id : get the "id" product.
     *
     * @param id the id of the productDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productDTO, or with status 404 (Not Found)
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);
        Optional<ProductDTO> productDTO = productService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productDTO);
    }

    /**
     * DELETE  /products/:id : delete the "id" product.
     *
     * @param id the id of the productDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);
        productService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/products/getProductsByClient/{id}")
    @Timed
    public ResponseEntity<List<ProductDTO>> getProductsByClient(@PathVariable Long id, Pageable pageable) {
        Page<ProductDTO> page = productService.getProductsByClient(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/getProductsByClient/{id}");
        return ResponseEntity.ok().headers(headers).body(page.getContent());

    }
    @GetMapping("/products/getProductsByDescription/{id}/{description}")
    @Timed
    public ResponseEntity<List<ProductDTO>> getProductsExtraByClient(@PathVariable Long id,  @PathVariable String description, Pageable pageable) {
        Page<ProductDTO> page =  productService.getProductsByDescription(id, description, pageable);
        HttpHeaders headers =  PaginationUtil.generatePaginationHttpHeaders(page, "/api/getProductsByDescription/{id}/{description}");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

    }
    @GetMapping("/products/getProductsByStatus/{id}/{status}")
    @Timed
    public ResponseEntity<List<ProductDTO>> getProductsByStatus(@PathVariable Long id,  @PathVariable boolean status, Pageable pageable) {
        Page<ProductDTO> page = productService.getProductsBystatus(id, status, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/getProductsByStatus/{id}/{status}");
        return ResponseEntity.ok().headers(headers).body(page.getContent());

    }

    @GetMapping("getProductsByCategory/{id}")
    @Timed
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long id, Pageable pageable) {
        Page<ProductDTO> page = productService.getProductsByCategory(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/getProductsByCategory/{id}");
        return ResponseEntity.ok().headers(headers).body(page.getContent());

    }


    @GetMapping("/products/getProductsTop/{id}")
    @Timed
    public ResponseEntity<List<ProductDTO>> getProductsTop(@PathVariable Long id, Pageable pageable) {
        Page<ProductDTO> page = productService.getProductsTop(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/getProductsTop/{id}");
        return ResponseEntity.ok().headers(headers).body(page.getContent());

    }

}
