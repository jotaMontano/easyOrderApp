package com.moonshine.web.rest;
import com.moonshine.service.ListOfValueService;
import com.moonshine.web.rest.errors.BadRequestAlertException;
import com.moonshine.web.rest.util.HeaderUtil;
import com.moonshine.web.rest.util.PaginationUtil;
import com.moonshine.service.dto.ListOfValueDTO;
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
 * REST controller for managing ListOfValue.
 */
@RestController
@RequestMapping("/api")
public class ListOfValueResource {

    private final Logger log = LoggerFactory.getLogger(ListOfValueResource.class);

    private static final String ENTITY_NAME = "listOfValue";

    private final ListOfValueService listOfValueService;

    public ListOfValueResource(ListOfValueService listOfValueService) {
        this.listOfValueService = listOfValueService;
    }

    /**
     * POST  /list-of-values : Create a new listOfValue.
     *
     * @param listOfValueDTO the listOfValueDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new listOfValueDTO, or with status 400 (Bad Request) if the listOfValue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/list-of-values")
    public ResponseEntity<ListOfValueDTO> createListOfValue(@Valid @RequestBody ListOfValueDTO listOfValueDTO) throws URISyntaxException {
        log.debug("REST request to save ListOfValue : {}", listOfValueDTO);
        if (listOfValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new listOfValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListOfValueDTO result = listOfValueService.save(listOfValueDTO);
        return ResponseEntity.created(new URI("/api/list-of-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /list-of-values : Updates an existing listOfValue.
     *
     * @param listOfValueDTO the listOfValueDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated listOfValueDTO,
     * or with status 400 (Bad Request) if the listOfValueDTO is not valid,
     * or with status 500 (Internal Server Error) if the listOfValueDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/list-of-values")
    public ResponseEntity<ListOfValueDTO> updateListOfValue(@Valid @RequestBody ListOfValueDTO listOfValueDTO) throws URISyntaxException {
        log.debug("REST request to update ListOfValue : {}", listOfValueDTO);
        if (listOfValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ListOfValueDTO result = listOfValueService.save(listOfValueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, listOfValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /list-of-values : get all the listOfValues.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of listOfValues in body
     */
    @GetMapping("/list-of-values")
    public ResponseEntity<List<ListOfValueDTO>> getAllListOfValues(Pageable pageable) {
        log.debug("REST request to get a page of ListOfValues");
        Page<ListOfValueDTO> page = listOfValueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/list-of-values");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /list-of-values/:id : get the "id" listOfValue.
     *
     * @param id the id of the listOfValueDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the listOfValueDTO, or with status 404 (Not Found)
     */
    @GetMapping("/list-of-values/{id}")
    public ResponseEntity<ListOfValueDTO> getListOfValue(@PathVariable Long id) {
        log.debug("REST request to get ListOfValue : {}", id);
        Optional<ListOfValueDTO> listOfValueDTO = listOfValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listOfValueDTO);
    }

    /**
     * DELETE  /list-of-values/:id : delete the "id" listOfValue.
     *
     * @param id the id of the listOfValueDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/list-of-values/{id}")
    public ResponseEntity<Void> deleteListOfValue(@PathVariable Long id) {
        log.debug("REST request to delete ListOfValue : {}", id);
        listOfValueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @GetMapping("/list-of-values/LST_PRODUCTS")
    @Timed
    public ResponseEntity<List<ListOfValueDTO>> getListProducts() {
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(listOfValueService.getListProduct("LST_PRODUCT")));
    }
}
