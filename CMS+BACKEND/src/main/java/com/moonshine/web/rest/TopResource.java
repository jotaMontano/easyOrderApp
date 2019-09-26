package com.moonshine.web.rest;
import com.moonshine.service.TopService;
import com.moonshine.web.rest.errors.BadRequestAlertException;
import com.moonshine.web.rest.util.HeaderUtil;
import com.moonshine.web.rest.util.PaginationUtil;
import com.moonshine.service.dto.TopDTO;
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
 * REST controller for managing Top.
 */
@RestController
@RequestMapping("/api")
public class TopResource {

    private final Logger log = LoggerFactory.getLogger(TopResource.class);

    private static final String ENTITY_NAME = "top";

    private final TopService topService;

    public TopResource(TopService topService) {
        this.topService = topService;
    }

    /**
     * POST  /tops : Create a new top.
     *
     * @param topDTO the topDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new topDTO, or with status 400 (Bad Request) if the top has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tops")
    public ResponseEntity<TopDTO> createTop(@Valid @RequestBody TopDTO topDTO) throws URISyntaxException {
        log.debug("REST request to save Top : {}", topDTO);
        if (topDTO.getId() != null) {
            throw new BadRequestAlertException("A new top cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopDTO result = topService.save(topDTO);
        return ResponseEntity.created(new URI("/api/tops/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tops : Updates an existing top.
     *
     * @param topDTO the topDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated topDTO,
     * or with status 400 (Bad Request) if the topDTO is not valid,
     * or with status 500 (Internal Server Error) if the topDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tops")
    public ResponseEntity<TopDTO> updateTop(@Valid @RequestBody TopDTO topDTO) throws URISyntaxException {
        log.debug("REST request to update Top : {}", topDTO);
        if (topDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TopDTO result = topService.save(topDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, topDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tops : get all the tops.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tops in body
     */
    @GetMapping("/tops")
    public ResponseEntity<List<TopDTO>> getAllTops(Pageable pageable) {
        log.debug("REST request to get a page of Tops");
        Page<TopDTO> page = topService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tops");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /tops/:id : get the "id" top.
     *
     * @param id the id of the topDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the topDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tops/{id}")
    public ResponseEntity<TopDTO> getTop(@PathVariable Long id) {
        log.debug("REST request to get Top : {}", id);
        Optional<TopDTO> topDTO = topService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topDTO);
    }

    /**
     * DELETE  /tops/:id : delete the "id" top.
     *
     * @param id the id of the topDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tops/{id}")
    public ResponseEntity<Void> deleteTop(@PathVariable Long id) {
        log.debug("REST request to delete Top : {}", id);
        topService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @GetMapping("/tops/getTopByIdProduct/{id}")
    @Timed
    public ResponseEntity<TopDTO> getTopByIdProduct(@PathVariable Long id, Pageable pageable) {
        TopDTO topDTO = topService.getTopByIdProduct(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(topDTO));

    }
}
