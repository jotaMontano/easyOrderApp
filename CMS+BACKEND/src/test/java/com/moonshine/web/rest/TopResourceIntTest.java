package com.moonshine.web.rest;

import com.moonshine.EasyOrderApp;

import com.moonshine.domain.Top;
import com.moonshine.repository.TopRepository;
import com.moonshine.service.TopService;
import com.moonshine.service.dto.TopDTO;
import com.moonshine.service.mapper.TopMapper;
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
 * Test class for the TopResource REST controller.
 *
 * @see TopResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EasyOrderApp.class)
public class TopResourceIntTest {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private TopRepository topRepository;

    @Autowired
    private TopMapper topMapper;

    @Autowired
    private TopService topService;

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

    private MockMvc restTopMockMvc;

    private Top top;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TopResource topResource = new TopResource(topService);
        this.restTopMockMvc = MockMvcBuilders.standaloneSetup(topResource)
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
    public static Top createEntity(EntityManager em) {
        Top top = new Top()
            .quantity(DEFAULT_QUANTITY)
            .type(DEFAULT_TYPE);
        return top;
    }

    @Before
    public void initTest() {
        top = createEntity(em);
    }

    @Test
    @Transactional
    public void createTop() throws Exception {
        int databaseSizeBeforeCreate = topRepository.findAll().size();

        // Create the Top
        TopDTO topDTO = topMapper.toDto(top);
        restTopMockMvc.perform(post("/api/tops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topDTO)))
            .andExpect(status().isCreated());

        // Validate the Top in the database
        List<Top> topList = topRepository.findAll();
        assertThat(topList).hasSize(databaseSizeBeforeCreate + 1);
        Top testTop = topList.get(topList.size() - 1);
        assertThat(testTop.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testTop.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createTopWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topRepository.findAll().size();

        // Create the Top with an existing ID
        top.setId(1L);
        TopDTO topDTO = topMapper.toDto(top);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopMockMvc.perform(post("/api/tops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Top in the database
        List<Top> topList = topRepository.findAll();
        assertThat(topList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = topRepository.findAll().size();
        // set the field null
        top.setQuantity(null);

        // Create the Top, which fails.
        TopDTO topDTO = topMapper.toDto(top);

        restTopMockMvc.perform(post("/api/tops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topDTO)))
            .andExpect(status().isBadRequest());

        List<Top> topList = topRepository.findAll();
        assertThat(topList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = topRepository.findAll().size();
        // set the field null
        top.setType(null);

        // Create the Top, which fails.
        TopDTO topDTO = topMapper.toDto(top);

        restTopMockMvc.perform(post("/api/tops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topDTO)))
            .andExpect(status().isBadRequest());

        List<Top> topList = topRepository.findAll();
        assertThat(topList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTops() throws Exception {
        // Initialize the database
        topRepository.saveAndFlush(top);

        // Get all the topList
        restTopMockMvc.perform(get("/api/tops?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(top.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getTop() throws Exception {
        // Initialize the database
        topRepository.saveAndFlush(top);

        // Get the top
        restTopMockMvc.perform(get("/api/tops/{id}", top.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(top.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTop() throws Exception {
        // Get the top
        restTopMockMvc.perform(get("/api/tops/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTop() throws Exception {
        // Initialize the database
        topRepository.saveAndFlush(top);

        int databaseSizeBeforeUpdate = topRepository.findAll().size();

        // Update the top
        Top updatedTop = topRepository.findById(top.getId()).get();
        // Disconnect from session so that the updates on updatedTop are not directly saved in db
        em.detach(updatedTop);
        updatedTop
            .quantity(UPDATED_QUANTITY)
            .type(UPDATED_TYPE);
        TopDTO topDTO = topMapper.toDto(updatedTop);

        restTopMockMvc.perform(put("/api/tops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topDTO)))
            .andExpect(status().isOk());

        // Validate the Top in the database
        List<Top> topList = topRepository.findAll();
        assertThat(topList).hasSize(databaseSizeBeforeUpdate);
        Top testTop = topList.get(topList.size() - 1);
        assertThat(testTop.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testTop.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTop() throws Exception {
        int databaseSizeBeforeUpdate = topRepository.findAll().size();

        // Create the Top
        TopDTO topDTO = topMapper.toDto(top);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopMockMvc.perform(put("/api/tops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(topDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Top in the database
        List<Top> topList = topRepository.findAll();
        assertThat(topList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTop() throws Exception {
        // Initialize the database
        topRepository.saveAndFlush(top);

        int databaseSizeBeforeDelete = topRepository.findAll().size();

        // Delete the top
        restTopMockMvc.perform(delete("/api/tops/{id}", top.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Top> topList = topRepository.findAll();
        assertThat(topList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Top.class);
        Top top1 = new Top();
        top1.setId(1L);
        Top top2 = new Top();
        top2.setId(top1.getId());
        assertThat(top1).isEqualTo(top2);
        top2.setId(2L);
        assertThat(top1).isNotEqualTo(top2);
        top1.setId(null);
        assertThat(top1).isNotEqualTo(top2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TopDTO.class);
        TopDTO topDTO1 = new TopDTO();
        topDTO1.setId(1L);
        TopDTO topDTO2 = new TopDTO();
        assertThat(topDTO1).isNotEqualTo(topDTO2);
        topDTO2.setId(topDTO1.getId());
        assertThat(topDTO1).isEqualTo(topDTO2);
        topDTO2.setId(2L);
        assertThat(topDTO1).isNotEqualTo(topDTO2);
        topDTO1.setId(null);
        assertThat(topDTO1).isNotEqualTo(topDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(topMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(topMapper.fromId(null)).isNull();
    }
}
