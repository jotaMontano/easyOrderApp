package com.moonshine.web.rest;

import com.moonshine.EasyOrderApp;

import com.moonshine.domain.ProductByOrder;
import com.moonshine.repository.ProductByOrderRepository;
import com.moonshine.service.ProductByOrderService;
import com.moonshine.service.dto.ProductByOrderDTO;
import com.moonshine.service.mapper.ProductByOrderMapper;
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
 * Test class for the ProductByOrderResource REST controller.
 *
 * @see ProductByOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EasyOrderApp.class)
public class ProductByOrderResourceIntTest {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private ProductByOrderRepository productByOrderRepository;

    @Autowired
    private ProductByOrderMapper productByOrderMapper;

    @Autowired
    private ProductByOrderService productByOrderService;

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

    private MockMvc restProductByOrderMockMvc;

    private ProductByOrder productByOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductByOrderResource productByOrderResource = new ProductByOrderResource(productByOrderService);
        this.restProductByOrderMockMvc = MockMvcBuilders.standaloneSetup(productByOrderResource)
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
    public static ProductByOrder createEntity(EntityManager em) {
        ProductByOrder productByOrder = new ProductByOrder()
            .quantity(DEFAULT_QUANTITY)
            .comment(DEFAULT_COMMENT)
            .status(DEFAULT_STATUS);
        return productByOrder;
    }

    @Before
    public void initTest() {
        productByOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductByOrder() throws Exception {
        int databaseSizeBeforeCreate = productByOrderRepository.findAll().size();

        // Create the ProductByOrder
        ProductByOrderDTO productByOrderDTO = productByOrderMapper.toDto(productByOrder);
        restProductByOrderMockMvc.perform(post("/api/product-by-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productByOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductByOrder in the database
        List<ProductByOrder> productByOrderList = productByOrderRepository.findAll();
        assertThat(productByOrderList).hasSize(databaseSizeBeforeCreate + 1);
        ProductByOrder testProductByOrder = productByOrderList.get(productByOrderList.size() - 1);
        assertThat(testProductByOrder.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testProductByOrder.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testProductByOrder.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createProductByOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productByOrderRepository.findAll().size();

        // Create the ProductByOrder with an existing ID
        productByOrder.setId(1L);
        ProductByOrderDTO productByOrderDTO = productByOrderMapper.toDto(productByOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductByOrderMockMvc.perform(post("/api/product-by-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productByOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductByOrder in the database
        List<ProductByOrder> productByOrderList = productByOrderRepository.findAll();
        assertThat(productByOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = productByOrderRepository.findAll().size();
        // set the field null
        productByOrder.setQuantity(null);

        // Create the ProductByOrder, which fails.
        ProductByOrderDTO productByOrderDTO = productByOrderMapper.toDto(productByOrder);

        restProductByOrderMockMvc.perform(post("/api/product-by-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productByOrderDTO)))
            .andExpect(status().isBadRequest());

        List<ProductByOrder> productByOrderList = productByOrderRepository.findAll();
        assertThat(productByOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = productByOrderRepository.findAll().size();
        // set the field null
        productByOrder.setComment(null);

        // Create the ProductByOrder, which fails.
        ProductByOrderDTO productByOrderDTO = productByOrderMapper.toDto(productByOrder);

        restProductByOrderMockMvc.perform(post("/api/product-by-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productByOrderDTO)))
            .andExpect(status().isBadRequest());

        List<ProductByOrder> productByOrderList = productByOrderRepository.findAll();
        assertThat(productByOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = productByOrderRepository.findAll().size();
        // set the field null
        productByOrder.setStatus(null);

        // Create the ProductByOrder, which fails.
        ProductByOrderDTO productByOrderDTO = productByOrderMapper.toDto(productByOrder);

        restProductByOrderMockMvc.perform(post("/api/product-by-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productByOrderDTO)))
            .andExpect(status().isBadRequest());

        List<ProductByOrder> productByOrderList = productByOrderRepository.findAll();
        assertThat(productByOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductByOrders() throws Exception {
        // Initialize the database
        productByOrderRepository.saveAndFlush(productByOrder);

        // Get all the productByOrderList
        restProductByOrderMockMvc.perform(get("/api/product-by-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productByOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProductByOrder() throws Exception {
        // Initialize the database
        productByOrderRepository.saveAndFlush(productByOrder);

        // Get the productByOrder
        restProductByOrderMockMvc.perform(get("/api/product-by-orders/{id}", productByOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productByOrder.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProductByOrder() throws Exception {
        // Get the productByOrder
        restProductByOrderMockMvc.perform(get("/api/product-by-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductByOrder() throws Exception {
        // Initialize the database
        productByOrderRepository.saveAndFlush(productByOrder);

        int databaseSizeBeforeUpdate = productByOrderRepository.findAll().size();

        // Update the productByOrder
        ProductByOrder updatedProductByOrder = productByOrderRepository.findById(productByOrder.getId()).get();
        // Disconnect from session so that the updates on updatedProductByOrder are not directly saved in db
        em.detach(updatedProductByOrder);
        updatedProductByOrder
            .quantity(UPDATED_QUANTITY)
            .comment(UPDATED_COMMENT)
            .status(UPDATED_STATUS);
        ProductByOrderDTO productByOrderDTO = productByOrderMapper.toDto(updatedProductByOrder);

        restProductByOrderMockMvc.perform(put("/api/product-by-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productByOrderDTO)))
            .andExpect(status().isOk());

        // Validate the ProductByOrder in the database
        List<ProductByOrder> productByOrderList = productByOrderRepository.findAll();
        assertThat(productByOrderList).hasSize(databaseSizeBeforeUpdate);
        ProductByOrder testProductByOrder = productByOrderList.get(productByOrderList.size() - 1);
        assertThat(testProductByOrder.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProductByOrder.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testProductByOrder.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingProductByOrder() throws Exception {
        int databaseSizeBeforeUpdate = productByOrderRepository.findAll().size();

        // Create the ProductByOrder
        ProductByOrderDTO productByOrderDTO = productByOrderMapper.toDto(productByOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductByOrderMockMvc.perform(put("/api/product-by-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productByOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductByOrder in the database
        List<ProductByOrder> productByOrderList = productByOrderRepository.findAll();
        assertThat(productByOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductByOrder() throws Exception {
        // Initialize the database
        productByOrderRepository.saveAndFlush(productByOrder);

        int databaseSizeBeforeDelete = productByOrderRepository.findAll().size();

        // Delete the productByOrder
        restProductByOrderMockMvc.perform(delete("/api/product-by-orders/{id}", productByOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductByOrder> productByOrderList = productByOrderRepository.findAll();
        assertThat(productByOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductByOrder.class);
        ProductByOrder productByOrder1 = new ProductByOrder();
        productByOrder1.setId(1L);
        ProductByOrder productByOrder2 = new ProductByOrder();
        productByOrder2.setId(productByOrder1.getId());
        assertThat(productByOrder1).isEqualTo(productByOrder2);
        productByOrder2.setId(2L);
        assertThat(productByOrder1).isNotEqualTo(productByOrder2);
        productByOrder1.setId(null);
        assertThat(productByOrder1).isNotEqualTo(productByOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductByOrderDTO.class);
        ProductByOrderDTO productByOrderDTO1 = new ProductByOrderDTO();
        productByOrderDTO1.setId(1L);
        ProductByOrderDTO productByOrderDTO2 = new ProductByOrderDTO();
        assertThat(productByOrderDTO1).isNotEqualTo(productByOrderDTO2);
        productByOrderDTO2.setId(productByOrderDTO1.getId());
        assertThat(productByOrderDTO1).isEqualTo(productByOrderDTO2);
        productByOrderDTO2.setId(2L);
        assertThat(productByOrderDTO1).isNotEqualTo(productByOrderDTO2);
        productByOrderDTO1.setId(null);
        assertThat(productByOrderDTO1).isNotEqualTo(productByOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productByOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productByOrderMapper.fromId(null)).isNull();
    }
}
