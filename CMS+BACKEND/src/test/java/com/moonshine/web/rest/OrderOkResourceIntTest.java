package com.moonshine.web.rest;

import com.moonshine.EasyOrderApp;

import com.moonshine.domain.OrderOk;
import com.moonshine.repository.OrderOkRepository;
import com.moonshine.service.OrderOkService;
import com.moonshine.service.dto.OrderOkDTO;
import com.moonshine.service.mapper.OrderOkMapper;
import com.moonshine.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.moonshine.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrderOkResource REST controller.
 *
 * @see OrderOkResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EasyOrderApp.class)
public class OrderOkResourceIntTest {

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private OrderOkRepository orderOkRepository;

    @Autowired
    private OrderOkMapper orderOkMapper;

    @Autowired
    private OrderOkService orderOkService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restOrderOkMockMvc;

    private OrderOk orderOk;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderOkResource orderOkResource = new OrderOkResource(orderOkService);
        this.restOrderOkMockMvc = MockMvcBuilders.standaloneSetup(orderOkResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderOk createEntity(EntityManager em) {
        OrderOk orderOk = new OrderOk()
            .total(DEFAULT_TOTAL)
            .status(DEFAULT_STATUS);
        return orderOk;
    }

    @Before
    public void initTest() {
        orderOk = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderOk() throws Exception {
        int databaseSizeBeforeCreate = orderOkRepository.findAll().size();

        // Create the OrderOk
        OrderOkDTO orderOkDTO = orderOkMapper.toDto(orderOk);
        restOrderOkMockMvc.perform(post("/api/order-oks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderOkDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderOk in the database
        List<OrderOk> orderOkList = orderOkRepository.findAll();
        assertThat(orderOkList).hasSize(databaseSizeBeforeCreate + 1);
        OrderOk testOrderOk = orderOkList.get(orderOkList.size() - 1);
        assertThat(testOrderOk.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testOrderOk.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createOrderOkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderOkRepository.findAll().size();

        // Create the OrderOk with an existing ID
        orderOk.setId(1L);
        OrderOkDTO orderOkDTO = orderOkMapper.toDto(orderOk);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderOkMockMvc.perform(post("/api/order-oks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderOkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderOk in the database
        List<OrderOk> orderOkList = orderOkRepository.findAll();
        assertThat(orderOkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderOkRepository.findAll().size();
        // set the field null
        orderOk.setTotal(null);

        // Create the OrderOk, which fails.
        OrderOkDTO orderOkDTO = orderOkMapper.toDto(orderOk);

        restOrderOkMockMvc.perform(post("/api/order-oks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderOkDTO)))
            .andExpect(status().isBadRequest());

        List<OrderOk> orderOkList = orderOkRepository.findAll();
        assertThat(orderOkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderOkRepository.findAll().size();
        // set the field null
        orderOk.setStatus(null);

        // Create the OrderOk, which fails.
        OrderOkDTO orderOkDTO = orderOkMapper.toDto(orderOk);

        restOrderOkMockMvc.perform(post("/api/order-oks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderOkDTO)))
            .andExpect(status().isBadRequest());

        List<OrderOk> orderOkList = orderOkRepository.findAll();
        assertThat(orderOkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrderOks() throws Exception {
        // Initialize the database
        orderOkRepository.saveAndFlush(orderOk);

        // Get all the orderOkList
        restOrderOkMockMvc.perform(get("/api/order-oks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderOk.getId().intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getOrderOk() throws Exception {
        // Initialize the database
        orderOkRepository.saveAndFlush(orderOk);

        // Get the orderOk
        restOrderOkMockMvc.perform(get("/api/order-oks/{id}", orderOk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderOk.getId().intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderOk() throws Exception {
        // Get the orderOk
        restOrderOkMockMvc.perform(get("/api/order-oks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderOk() throws Exception {
        // Initialize the database
        orderOkRepository.saveAndFlush(orderOk);

        int databaseSizeBeforeUpdate = orderOkRepository.findAll().size();

        // Update the orderOk
        OrderOk updatedOrderOk = orderOkRepository.findById(orderOk.getId()).get();
        // Disconnect from session so that the updates on updatedOrderOk are not directly saved in db
        em.detach(updatedOrderOk);
        updatedOrderOk
            .total(UPDATED_TOTAL)
            .status(UPDATED_STATUS);
        OrderOkDTO orderOkDTO = orderOkMapper.toDto(updatedOrderOk);

        restOrderOkMockMvc.perform(put("/api/order-oks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderOkDTO)))
            .andExpect(status().isOk());

        // Validate the OrderOk in the database
        List<OrderOk> orderOkList = orderOkRepository.findAll();
        assertThat(orderOkList).hasSize(databaseSizeBeforeUpdate);
        OrderOk testOrderOk = orderOkList.get(orderOkList.size() - 1);
        assertThat(testOrderOk.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testOrderOk.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderOk() throws Exception {
        int databaseSizeBeforeUpdate = orderOkRepository.findAll().size();

        // Create the OrderOk
        OrderOkDTO orderOkDTO = orderOkMapper.toDto(orderOk);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderOkMockMvc.perform(put("/api/order-oks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderOkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderOk in the database
        List<OrderOk> orderOkList = orderOkRepository.findAll();
        assertThat(orderOkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderOk() throws Exception {
        // Initialize the database
        orderOkRepository.saveAndFlush(orderOk);

        int databaseSizeBeforeDelete = orderOkRepository.findAll().size();

        // Delete the orderOk
        restOrderOkMockMvc.perform(delete("/api/order-oks/{id}", orderOk.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderOk> orderOkList = orderOkRepository.findAll();
        assertThat(orderOkList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderOk.class);
        OrderOk orderOk1 = new OrderOk();
        orderOk1.setId(1L);
        OrderOk orderOk2 = new OrderOk();
        orderOk2.setId(orderOk1.getId());
        assertThat(orderOk1).isEqualTo(orderOk2);
        orderOk2.setId(2L);
        assertThat(orderOk1).isNotEqualTo(orderOk2);
        orderOk1.setId(null);
        assertThat(orderOk1).isNotEqualTo(orderOk2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderOkDTO.class);
        OrderOkDTO orderOkDTO1 = new OrderOkDTO();
        orderOkDTO1.setId(1L);
        OrderOkDTO orderOkDTO2 = new OrderOkDTO();
        assertThat(orderOkDTO1).isNotEqualTo(orderOkDTO2);
        orderOkDTO2.setId(orderOkDTO1.getId());
        assertThat(orderOkDTO1).isEqualTo(orderOkDTO2);
        orderOkDTO2.setId(2L);
        assertThat(orderOkDTO1).isNotEqualTo(orderOkDTO2);
        orderOkDTO1.setId(null);
        assertThat(orderOkDTO1).isNotEqualTo(orderOkDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderOkMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orderOkMapper.fromId(null)).isNull();
    }
}
