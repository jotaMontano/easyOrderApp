package com.moonshine.web.rest;
import com.moonshine.service.ExtraInLineService;
import com.moonshine.web.rest.errors.BadRequestAlertException;
import com.moonshine.web.rest.util.HeaderUtil;
import com.moonshine.web.rest.util.PaginationUtil;
import com.moonshine.service.dto.ExtraInLineDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ExtraInLine.
 */
@RestController
@RequestMapping("/api")
public class ExtraInLineResource {

    private final Logger log = LoggerFactory.getLogger(ExtraInLineResource.class);

    private static final String ENTITY_NAME = "extraInLine";

    private final ExtraInLineService extraInLineService;

    public ExtraInLineResource(ExtraInLineService extraInLineService) {
        this.extraInLineService = extraInLineService;
    }

    /**
     * POST  /extra-in-lines : Create a new extraInLine.
     *
     * @param extraInLineDTO the extraInLineDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new extraInLineDTO, or with status 400 (Bad Request) if the extraInLine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/extra-in-lines")
    public ResponseEntity<ExtraInLineDTO> createExtraInLine(@RequestBody ExtraInLineDTO extraInLineDTO) throws URISyntaxException {
        log.debug("REST request to save ExtraInLine : {}", extraInLineDTO);
        if (extraInLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new extraInLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExtraInLineDTO result = extraInLineService.save(extraInLineDTO);
        return ResponseEntity.created(new URI("/api/extra-in-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /extra-in-lines : Updates an existing extraInLine.
     *
     * @param extraInLineDTO the extraInLineDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated extraInLineDTO,
     * or with status 400 (Bad Request) if the extraInLineDTO is not valid,
     * or with status 500 (Internal Server Error) if the extraInLineDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/extra-in-lines")
    public ResponseEntity<ExtraInLineDTO> updateExtraInLine(@RequestBody ExtraInLineDTO extraInLineDTO) throws URISyntaxException {
        log.debug("REST request to update ExtraInLine : {}", extraInLineDTO);
        if (extraInLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExtraInLineDTO result = extraInLineService.save(extraInLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, extraInLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /extra-in-lines : get all the extraInLines.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of extraInLines in body
     */
    @GetMapping("/extra-in-lines")
    public ResponseEntity<List<ExtraInLineDTO>> getAllExtraInLines(Pageable pageable) {
        log.debug("REST request to get a page of ExtraInLines");
        Page<ExtraInLineDTO> page = extraInLineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/extra-in-lines");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /extra-in-lines/:id : get the "id" extraInLine.
     *
     * @param id the id of the extraInLineDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the extraInLineDTO, or with status 404 (Not Found)
     */
    @GetMapping("/extra-in-lines/{id}")
    public ResponseEntity<ExtraInLineDTO> getExtraInLine(@PathVariable Long id) {
        log.debug("REST request to get ExtraInLine : {}", id);
        Optional<ExtraInLineDTO> extraInLineDTO = extraInLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(extraInLineDTO);
    }

    /**
     * DELETE  /extra-in-lines/:id : delete the "id" extraInLine.
     *
     * @param id the id of the extraInLineDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/extra-in-lines/{id}")
    public ResponseEntity<Void> deleteExtraInLine(@PathVariable Long id) {
        log.debug("REST request to delete ExtraInLine : {}", id);
        extraInLineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/extraLine/{id}")
    public ResponseEntity<List<ExtraInLineDTO>> getExtrasInLine(@PathVariable Long id) {
        log.debug("REST request to get Product : {}", id);
        List<ExtraInLineDTO> extrasInLine = extraInLineService.findExtrasInLine(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(extrasInLine));
    }
}
