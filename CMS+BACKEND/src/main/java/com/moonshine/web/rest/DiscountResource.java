package com.moonshine.web.rest;
import com.moonshine.domain.Product;
import com.moonshine.service.DiscountService;
import com.moonshine.web.rest.errors.BadRequestAlertException;
import com.moonshine.web.rest.util.HeaderUtil;
import com.moonshine.web.rest.util.PaginationUtil;
import com.moonshine.service.dto.DiscountDTO;
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
 * REST controller for managing Discount.
 */
@RestController
@RequestMapping("/api")
public class DiscountResource {

    private final Logger log = LoggerFactory.getLogger(DiscountResource.class);

    private static final String ENTITY_NAME = "discount";

    private final DiscountService discountService;

    public DiscountResource(DiscountService discountService) {
        this.discountService = discountService;
    }

    /**
     * POST  /discounts : Create a new discount.
     *
     * @param discountDTO the discountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new discountDTO, or with status 400 (Bad Request) if the discount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/discounts")
    public ResponseEntity<DiscountDTO> createDiscount(@Valid @RequestBody DiscountDTO discountDTO) throws URISyntaxException {
        log.debug("REST request to save Discount : {}", discountDTO);
        if (discountDTO.getId() != null) {
            throw new BadRequestAlertException("A new discount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiscountDTO result = discountService.save(discountDTO);
        return ResponseEntity.created(new URI("/api/discounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /discounts : Updates an existing discount.
     *
     * @param discountDTO the discountDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated discountDTO,
     * or with status 400 (Bad Request) if the discountDTO is not valid,
     * or with status 500 (Internal Server Error) if the discountDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/discounts")
    public ResponseEntity<DiscountDTO> updateDiscount(@Valid @RequestBody DiscountDTO discountDTO) throws URISyntaxException {
        log.debug("REST request to update Discount : {}", discountDTO);
        if (discountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiscountDTO result = discountService.save(discountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, discountDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /discounts : get all the discounts.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of discounts in body
     */
    @GetMapping("/discounts")
    public ResponseEntity<List<DiscountDTO>> getAllDiscounts(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Discounts");
        Page<DiscountDTO> page;
        if (eagerload) {
            page = discountService.findAllWithEagerRelationships(pageable);
        } else {
            page = discountService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/discounts?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /discounts/:id : get the "id" discount.
     *
     * @param id the id of the discountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the discountDTO, or with status 404 (Not Found)
     */
    @GetMapping("/discounts/{id}")
    public ResponseEntity<DiscountDTO> getDiscount(@PathVariable Long id) {
        log.debug("REST request to get Discount : {}", id);
        Optional<DiscountDTO> discountDTO = discountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(discountDTO);
    }

    /**
     * DELETE  /discounts/:id : delete the "id" discount.
     *
     * @param id the id of the discountDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/discounts/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
        log.debug("REST request to delete Discount : {}", id);
        discountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @GetMapping("/discounts/findDiscountByUser/{id}")
    public ResponseEntity<List<DiscountDTO>> getDiscountByUser(@PathVariable Long id,Pageable pageable) {
        Page<DiscountDTO> page = discountService.getDiscountByUser(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/findDiscountByUser/{id}");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/discountsByClient/{id}")
    public ResponseEntity<List<DiscountDTO>> getAllDiscounts(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get a page of Extras");
        Page<DiscountDTO> page = discountService.findAllByClient(id,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/discounts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/discountsByProduct/{id}")
    public ResponseEntity getAllExtrasByClient(@PathVariable Long id) {
        log.debug("REST request to get a page of Extras");
        Product product = new Product();
        product.setId(id);
        List<DiscountDTO> page = discountService.findAllByProduct(product);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(page));
    }

    @GetMapping("/discounts/getDiscountsByStatus/{id}/{status}")
    @Timed
    public ResponseEntity<List<DiscountDTO>> getDiscountsByStatus(@PathVariable Long id,  @PathVariable boolean status, Pageable pageable) {
        Page<DiscountDTO> page = discountService.getDiscountsBystatus(id, status, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/getDiscountsByStatus/{id}/{status}");
        return ResponseEntity.ok().headers(headers).body(page.getContent());

    }
}
