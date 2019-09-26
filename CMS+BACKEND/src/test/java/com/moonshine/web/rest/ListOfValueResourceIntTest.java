package com.moonshine.web.rest;

import com.moonshine.EasyOrderApp;

import com.moonshine.domain.ListOfValue;
import com.moonshine.repository.ListOfValueRepository;
import com.moonshine.service.ListOfValueService;
import com.moonshine.service.dto.ListOfValueDTO;
import com.moonshine.service.mapper.ListOfValueMapper;
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
 * Test class for the ListOfValueResource REST controller.
 *
 * @see ListOfValueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EasyOrderApp.class)
public class ListOfValueResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private ListOfValueRepository listOfValueRepository;

    @Autowired
    private ListOfValueMapper listOfValueMapper;

    @Autowired
    private ListOfValueService listOfValueService;

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

    private MockMvc restListOfValueMockMvc;

    private ListOfValue listOfValue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ListOfValueResource listOfValueResource = new ListOfValueResource(listOfValueService);
        this.restListOfValueMockMvc = MockMvcBuilders.standaloneSetup(listOfValueResource)
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
    public static ListOfValue createEntity(EntityManager em) {
        ListOfValue listOfValue = new ListOfValue()
            .value(DEFAULT_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE);
        return listOfValue;
    }

    @Before
    public void initTest() {
        listOfValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createListOfValue() throws Exception {
        int databaseSizeBeforeCreate = listOfValueRepository.findAll().size();

        // Create the ListOfValue
        ListOfValueDTO listOfValueDTO = listOfValueMapper.toDto(listOfValue);
        restListOfValueMockMvc.perform(post("/api/list-of-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listOfValueDTO)))
            .andExpect(status().isCreated());

        // Validate the ListOfValue in the database
        List<ListOfValue> listOfValueList = listOfValueRepository.findAll();
        assertThat(listOfValueList).hasSize(databaseSizeBeforeCreate + 1);
        ListOfValue testListOfValue = listOfValueList.get(listOfValueList.size() - 1);
        assertThat(testListOfValue.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testListOfValue.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testListOfValue.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createListOfValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listOfValueRepository.findAll().size();

        // Create the ListOfValue with an existing ID
        listOfValue.setId(1L);
        ListOfValueDTO listOfValueDTO = listOfValueMapper.toDto(listOfValue);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListOfValueMockMvc.perform(post("/api/list-of-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listOfValueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ListOfValue in the database
        List<ListOfValue> listOfValueList = listOfValueRepository.findAll();
        assertThat(listOfValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = listOfValueRepository.findAll().size();
        // set the field null
        listOfValue.setValue(null);

        // Create the ListOfValue, which fails.
        ListOfValueDTO listOfValueDTO = listOfValueMapper.toDto(listOfValue);

        restListOfValueMockMvc.perform(post("/api/list-of-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listOfValueDTO)))
            .andExpect(status().isBadRequest());

        List<ListOfValue> listOfValueList = listOfValueRepository.findAll();
        assertThat(listOfValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = listOfValueRepository.findAll().size();
        // set the field null
        listOfValue.setDescription(null);

        // Create the ListOfValue, which fails.
        ListOfValueDTO listOfValueDTO = listOfValueMapper.toDto(listOfValue);

        restListOfValueMockMvc.perform(post("/api/list-of-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listOfValueDTO)))
            .andExpect(status().isBadRequest());

        List<ListOfValue> listOfValueList = listOfValueRepository.findAll();
        assertThat(listOfValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllListOfValues() throws Exception {
        // Initialize the database
        listOfValueRepository.saveAndFlush(listOfValue);

        // Get all the listOfValueList
        restListOfValueMockMvc.perform(get("/api/list-of-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listOfValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getListOfValue() throws Exception {
        // Initialize the database
        listOfValueRepository.saveAndFlush(listOfValue);

        // Get the listOfValue
        restListOfValueMockMvc.perform(get("/api/list-of-values/{id}", listOfValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(listOfValue.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingListOfValue() throws Exception {
        // Get the listOfValue
        restListOfValueMockMvc.perform(get("/api/list-of-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListOfValue() throws Exception {
        // Initialize the database
        listOfValueRepository.saveAndFlush(listOfValue);

        int databaseSizeBeforeUpdate = listOfValueRepository.findAll().size();

        // Update the listOfValue
        ListOfValue updatedListOfValue = listOfValueRepository.findById(listOfValue.getId()).get();
        // Disconnect from session so that the updates on updatedListOfValue are not directly saved in db
        em.detach(updatedListOfValue);
        updatedListOfValue
            .value(UPDATED_VALUE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE);
        ListOfValueDTO listOfValueDTO = listOfValueMapper.toDto(updatedListOfValue);

        restListOfValueMockMvc.perform(put("/api/list-of-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listOfValueDTO)))
            .andExpect(status().isOk());

        // Validate the ListOfValue in the database
        List<ListOfValue> listOfValueList = listOfValueRepository.findAll();
        assertThat(listOfValueList).hasSize(databaseSizeBeforeUpdate);
        ListOfValue testListOfValue = listOfValueList.get(listOfValueList.size() - 1);
        assertThat(testListOfValue.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testListOfValue.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testListOfValue.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingListOfValue() throws Exception {
        int databaseSizeBeforeUpdate = listOfValueRepository.findAll().size();

        // Create the ListOfValue
        ListOfValueDTO listOfValueDTO = listOfValueMapper.toDto(listOfValue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListOfValueMockMvc.perform(put("/api/list-of-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listOfValueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ListOfValue in the database
        List<ListOfValue> listOfValueList = listOfValueRepository.findAll();
        assertThat(listOfValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteListOfValue() throws Exception {
        // Initialize the database
        listOfValueRepository.saveAndFlush(listOfValue);

        int databaseSizeBeforeDelete = listOfValueRepository.findAll().size();

        // Delete the listOfValue
        restListOfValueMockMvc.perform(delete("/api/list-of-values/{id}", listOfValue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ListOfValue> listOfValueList = listOfValueRepository.findAll();
        assertThat(listOfValueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListOfValue.class);
        ListOfValue listOfValue1 = new ListOfValue();
        listOfValue1.setId(1L);
        ListOfValue listOfValue2 = new ListOfValue();
        listOfValue2.setId(listOfValue1.getId());
        assertThat(listOfValue1).isEqualTo(listOfValue2);
        listOfValue2.setId(2L);
        assertThat(listOfValue1).isNotEqualTo(listOfValue2);
        listOfValue1.setId(null);
        assertThat(listOfValue1).isNotEqualTo(listOfValue2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListOfValueDTO.class);
        ListOfValueDTO listOfValueDTO1 = new ListOfValueDTO();
        listOfValueDTO1.setId(1L);
        ListOfValueDTO listOfValueDTO2 = new ListOfValueDTO();
        assertThat(listOfValueDTO1).isNotEqualTo(listOfValueDTO2);
        listOfValueDTO2.setId(listOfValueDTO1.getId());
        assertThat(listOfValueDTO1).isEqualTo(listOfValueDTO2);
        listOfValueDTO2.setId(2L);
        assertThat(listOfValueDTO1).isNotEqualTo(listOfValueDTO2);
        listOfValueDTO1.setId(null);
        assertThat(listOfValueDTO1).isNotEqualTo(listOfValueDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(listOfValueMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(listOfValueMapper.fromId(null)).isNull();
    }
}
