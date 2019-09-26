package com.moonshine.web.rest;
import com.moonshine.domain.ProductByOrder;
import com.moonshine.service.ProductByOrderService;
import com.moonshine.web.rest.errors.BadRequestAlertException;
import com.moonshine.web.rest.util.HeaderUtil;
import com.moonshine.web.rest.util.PaginationUtil;
import com.moonshine.service.dto.ProductByOrderDTO;
import io.github.jhipster.web.util.ResponseUtil;
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
 * REST controller for managing ProductByOrder.
 */
@RestController
@RequestMapping("/api")
public class ProductByOrderResource {

    private final Logger log = LoggerFactory.getLogger(ProductByOrderResource.class);

    private static final String ENTITY_NAME = "productByOrder";

    private final ProductByOrderService productByOrderService;

    public ProductByOrderResource(ProductByOrderService productByOrderService) {
        this.productByOrderService = productByOrderService;
    }

    /**
     * POST  /product-by-orders : Create a new productByOrder.
     *
     * @param productByOrderDTO the productByOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productByOrderDTO, or with status 400 (Bad Request) if the productByOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-by-orders")
    public ResponseEntity<ProductByOrderDTO> createProductByOrder(@Valid @RequestBody ProductByOrderDTO productByOrderDTO) throws URISyntaxException {
        log.debug("REST request to save ProductByOrder : {}", productByOrderDTO);
        if (productByOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new productByOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductByOrderDTO result = productByOrderService.save(productByOrderDTO);
        return ResponseEntity.created(new URI("/api/product-by-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-by-orders : Updates an existing productByOrder.
     *
     * @param productByOrderDTO the productByOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productByOrderDTO,
     * or with status 400 (Bad Request) if the productByOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the productByOrderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-by-orders")
    public ResponseEntity<ProductByOrderDTO> updateProductByOrder(@Valid @RequestBody ProductByOrderDTO productByOrderDTO) throws URISyntaxException {
        log.debug("REST request to update ProductByOrder : {}", productByOrderDTO);
        if (productByOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductByOrderDTO result = productByOrderService.save(productByOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productByOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-by-orders : get all the productByOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productByOrders in body
     */
    @GetMapping("/product-by-orders")
    public ResponseEntity<List<ProductByOrderDTO>> getAllProductByOrders(Pageable pageable) {
        log.debug("REST request to get a page of ProductByOrders");
        Page<ProductByOrderDTO> page = productByOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product-by-orders");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /product-by-orders/:id : get the "id" productByOrder.
     *
     * @param id the id of the productByOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productByOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/product-by-orders/{id}")
    public ResponseEntity<ProductByOrderDTO> getProductByOrder(@PathVariable Long id) {
        log.debug("REST request to get ProductByOrder : {}", id);
        Optional<ProductByOrderDTO> productByOrderDTO = productByOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productByOrderDTO);
    }

    /**
     * DELETE  /product-by-orders/:id : delete the "id" productByOrder.
     *
     * @param id the id of the productByOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-by-orders/{id}")
    public ResponseEntity<Void> deleteProductByOrder(@PathVariable Long id) {
        log.debug("REST request to delete ProductByOrder : {}", id);
        productByOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/productsByOrder/{id}")
    public ResponseEntity<List<ProductByOrderDTO>> getProductsByOrder(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);
        List<ProductByOrderDTO> productsByOrder = productByOrderService.findProductsByOrder(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(productsByOrder));
    }
}
