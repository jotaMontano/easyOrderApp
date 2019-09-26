package com.moonshine.web.rest;
import com.moonshine.domain.OrderOk;
import com.moonshine.service.OrderOkService;
import com.moonshine.web.rest.errors.BadRequestAlertException;
import com.moonshine.web.rest.util.HeaderUtil;
import com.moonshine.web.rest.util.PaginationUtil;
import com.moonshine.service.dto.OrderOkDTO;
import com.moonshine.websocket.ActivityService;
import io.github.jhipster.web.util.ResponseUtil;
import org.hibernate.criterion.Order;
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
 * REST controller for managing OrderOk.
 */
@RestController
@RequestMapping("/api")
public class OrderOkResource {

    private final Logger log = LoggerFactory.getLogger(OrderOkResource.class);

    private static final String ENTITY_NAME = "orderOk";

    private final OrderOkService orderOkService;

    ActivityService activityService = new ActivityService();

    public OrderOkResource(OrderOkService orderOkService) {
        this.orderOkService = orderOkService;
    }

    /**
     * POST  /order-oks : Create a new orderOk.
     *
     * @param orderOkDTO the orderOkDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderOkDTO, or with status 400 (Bad Request) if the orderOk has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-oks")
    public ResponseEntity<OrderOkDTO> createOrderOk(@Valid @RequestBody OrderOkDTO orderOkDTO) throws URISyntaxException {
        log.debug("REST request to save OrderOk : {}", orderOkDTO);
        if (orderOkDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderOk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderOkDTO result = orderOkService.save(orderOkDTO);
        activityService.sendActivityOrder("Payment ready");
        return ResponseEntity.created(new URI("/api/order-oks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-oks : Updates an existing orderOk.
     *
     * @param orderOkDTO the orderOkDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderOkDTO,
     * or with status 400 (Bad Request) if the orderOkDTO is not valid,
     * or with status 500 (Internal Server Error) if the orderOkDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-oks")
    public ResponseEntity<OrderOkDTO> updateOrderOk(@Valid @RequestBody OrderOkDTO orderOkDTO) throws URISyntaxException {
        log.debug("REST request to update OrderOk : {}", orderOkDTO);
        if (orderOkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderOkDTO result = orderOkService.save(orderOkDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderOkDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-oks : get all the orderOks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of orderOks in body
     */
    @GetMapping("/order-oks")
    public ResponseEntity<List<OrderOkDTO>> getAllOrderOks(Pageable pageable) {
        log.debug("REST request to get a page of OrderOks");
        Page<OrderOkDTO> page = orderOkService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/order-oks");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /order-oks/:id : get the "id" orderOk.
     *
     * @param id the id of the orderOkDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderOkDTO, or with status 404 (Not Found)
     */
    @GetMapping("/order-oks/{id}")
    public ResponseEntity<OrderOkDTO> getOrderOk(@PathVariable Long id) {
        log.debug("REST request to get OrderOk : {}", id);
        Optional<OrderOkDTO> orderOkDTO = orderOkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderOkDTO);
    }

    /**
     * DELETE  /order-oks/:id : delete the "id" orderOk.
     *
     * @param id the id of the orderOkDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-oks/{id}")
    public ResponseEntity<Void> deleteOrderOk(@PathVariable Long id) {
        log.debug("REST request to delete OrderOk : {}", id);
        orderOkService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/ordersList/{id}/{filter}")
    public ResponseEntity<List<OrderOkDTO>> getOrdersByClient(@PathVariable Long id,@PathVariable String filter) {
        log.debug("REST request to get a list of OrderOks");
        List<OrderOkDTO> orders = orderOkService.findOrdersByClient(id,filter);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(orders));
    }
    @PutMapping("/order-oks-toPay")
    public ResponseEntity<OrderOkDTO> updateOrderOkToPay(@Valid @RequestBody OrderOkDTO orderOkDTO) {
        log.debug("REST request to update OrderOk : {}", orderOkDTO);
        if (orderOkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderOkDTO result = orderOkService.save(orderOkDTO);
        activityService.sendActivityOrder("Payment ready");
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderOkDTO.getId().toString()))
            .body(result);
    }
}
