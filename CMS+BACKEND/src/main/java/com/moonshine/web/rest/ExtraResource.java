package com.moonshine.web.rest;
import com.moonshine.domain.Product;
import com.moonshine.service.ExtraService;
import com.moonshine.web.rest.errors.BadRequestAlertException;
import com.moonshine.web.rest.util.HeaderUtil;
import com.moonshine.web.rest.util.PaginationUtil;
import com.moonshine.service.dto.ExtraDTO;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Extra.
 */
@RestController
@RequestMapping("/api")
public class ExtraResource {

    private final Logger log = LoggerFactory.getLogger(ExtraResource.class);

    private static final String ENTITY_NAME = "extra";

    private final ExtraService extraService;

    public ExtraResource(ExtraService extraService) {
        this.extraService = extraService;
    }

    /**
     * POST  /extras : Create a new extra.
     *
     * @param extraDTO the extraDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new extraDTO, or with status 400 (Bad Request) if the extra has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/extras")
    public ResponseEntity<ExtraDTO> createExtra(@Valid @RequestBody ExtraDTO extraDTO) throws URISyntaxException {
        log.debug("REST request to save Extra : {}", extraDTO);
        if (extraDTO.getId() != null) {
            throw new BadRequestAlertException("A new extra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExtraDTO result = extraService.save(extraDTO);
        return ResponseEntity.created(new URI("/api/extras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /extras : Updates an existing extra.
     *
     * @param extraDTO the extraDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated extraDTO,
     * or with status 400 (Bad Request) if the extraDTO is not valid,
     * or with status 500 (Internal Server Error) if the extraDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/extras")
    public ResponseEntity<ExtraDTO> updateExtra(@Valid @RequestBody ExtraDTO extraDTO) throws URISyntaxException {
        log.debug("REST request to update Extra : {}", extraDTO);
        if (extraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExtraDTO result = extraService.save(extraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, extraDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /extras : get all the extras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of extras in body
     */
    @GetMapping("/extras")
    public ResponseEntity<List<ExtraDTO>> getAllExtras(Pageable pageable) {
        log.debug("REST request to get a page of Extras");
        Page<ExtraDTO> page = extraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/extras");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /extras/:id : get the "id" extra.
     *
     * @param id the id of the extraDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the extraDTO, or with status 404 (Not Found)
     */
    @GetMapping("/extras/{id}")
    public ResponseEntity<ExtraDTO> getExtra(@PathVariable Long id) {
        log.debug("REST request to get Extra : {}", id);
        Optional<ExtraDTO> extraDTO = extraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(extraDTO);
    }

    /**
     * DELETE  /extras/:id : delete the "id" extra.
     *
     * @param id the id of the extraDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/extras/{id}")
    public ResponseEntity<Void> deleteExtra(@PathVariable Long id) {
        log.debug("REST request to delete Extra : {}", id);
        extraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @GetMapping("/extrasByClient/{id}")
    public ResponseEntity<List<ExtraDTO>> getAllExtras(@PathVariable Long id, Pageable pageable) {
        log.debug("REST request to get a page of Extras");
        Page<ExtraDTO> page = extraService.findAllByClient(id,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/extras");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    @GetMapping("/extrasByProduct/{id}")
    public ResponseEntity getAllExtrasByClient(@PathVariable Long id) {
        log.debug("REST request to get a page of Extras");
        Product product = new Product();
        product.setId(id);
        List<ExtraDTO> page = extraService.findAllByProduct(product);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(page));
    }

    @GetMapping("/extras/getExtrasByStatus/{id}/{status}")
    @Timed
    public ResponseEntity<List<ExtraDTO>> getDiscountsByStatus(@PathVariable Long id,  @PathVariable boolean status, Pageable pageable) {
        Page<ExtraDTO> page = extraService.getExtrasByStatus(id, status, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/getExtrasByStatus/{id}/{status}");
        return ResponseEntity.ok().headers(headers).body(page.getContent());

    }
}
