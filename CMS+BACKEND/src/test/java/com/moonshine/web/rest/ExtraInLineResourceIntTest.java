package com.moonshine.web.rest;

import com.moonshine.EasyOrderApp;

import com.moonshine.domain.ExtraInLine;
import com.moonshine.repository.ExtraInLineRepository;
import com.moonshine.service.ExtraInLineService;
import com.moonshine.service.dto.ExtraInLineDTO;
import com.moonshine.service.mapper.ExtraInLineMapper;
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
 * Test class for the ExtraInLineResource REST controller.
 *
 * @see ExtraInLineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EasyOrderApp.class)
public class ExtraInLineResourceIntTest {

    @Autowired
    private ExtraInLineRepository extraInLineRepository;

    @Autowired
    private ExtraInLineMapper extraInLineMapper;

    @Autowired
    private ExtraInLineService extraInLineService;

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

    private MockMvc restExtraInLineMockMvc;

    private ExtraInLine extraInLine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExtraInLineResource extraInLineResource = new ExtraInLineResource(extraInLineService);
        this.restExtraInLineMockMvc = MockMvcBuilders.standaloneSetup(extraInLineResource)
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
    public static ExtraInLine createEntity(EntityManager em) {
        ExtraInLine extraInLine = new ExtraInLine();
        return extraInLine;
    }

    @Before
    public void initTest() {
        extraInLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createExtraInLine() throws Exception {
        int databaseSizeBeforeCreate = extraInLineRepository.findAll().size();

        // Create the ExtraInLine
        ExtraInLineDTO extraInLineDTO = extraInLineMapper.toDto(extraInLine);
        restExtraInLineMockMvc.perform(post("/api/extra-in-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraInLineDTO)))
            .andExpect(status().isCreated());

        // Validate the ExtraInLine in the database
        List<ExtraInLine> extraInLineList = extraInLineRepository.findAll();
        assertThat(extraInLineList).hasSize(databaseSizeBeforeCreate + 1);
        ExtraInLine testExtraInLine = extraInLineList.get(extraInLineList.size() - 1);
    }

    @Test
    @Transactional
    public void createExtraInLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = extraInLineRepository.findAll().size();

        // Create the ExtraInLine with an existing ID
        extraInLine.setId(1L);
        ExtraInLineDTO extraInLineDTO = extraInLineMapper.toDto(extraInLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExtraInLineMockMvc.perform(post("/api/extra-in-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraInLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExtraInLine in the database
        List<ExtraInLine> extraInLineList = extraInLineRepository.findAll();
        assertThat(extraInLineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExtraInLines() throws Exception {
        // Initialize the database
        extraInLineRepository.saveAndFlush(extraInLine);

        // Get all the extraInLineList
        restExtraInLineMockMvc.perform(get("/api/extra-in-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(extraInLine.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getExtraInLine() throws Exception {
        // Initialize the database
        extraInLineRepository.saveAndFlush(extraInLine);

        // Get the extraInLine
        restExtraInLineMockMvc.perform(get("/api/extra-in-lines/{id}", extraInLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(extraInLine.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingExtraInLine() throws Exception {
        // Get the extraInLine
        restExtraInLineMockMvc.perform(get("/api/extra-in-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExtraInLine() throws Exception {
        // Initialize the database
        extraInLineRepository.saveAndFlush(extraInLine);

        int databaseSizeBeforeUpdate = extraInLineRepository.findAll().size();

        // Update the extraInLine
        ExtraInLine updatedExtraInLine = extraInLineRepository.findById(extraInLine.getId()).get();
        // Disconnect from session so that the updates on updatedExtraInLine are not directly saved in db
        em.detach(updatedExtraInLine);
        ExtraInLineDTO extraInLineDTO = extraInLineMapper.toDto(updatedExtraInLine);

        restExtraInLineMockMvc.perform(put("/api/extra-in-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraInLineDTO)))
            .andExpect(status().isOk());

        // Validate the ExtraInLine in the database
        List<ExtraInLine> extraInLineList = extraInLineRepository.findAll();
        assertThat(extraInLineList).hasSize(databaseSizeBeforeUpdate);
        ExtraInLine testExtraInLine = extraInLineList.get(extraInLineList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingExtraInLine() throws Exception {
        int databaseSizeBeforeUpdate = extraInLineRepository.findAll().size();

        // Create the ExtraInLine
        ExtraInLineDTO extraInLineDTO = extraInLineMapper.toDto(extraInLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExtraInLineMockMvc.perform(put("/api/extra-in-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraInLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExtraInLine in the database
        List<ExtraInLine> extraInLineList = extraInLineRepository.findAll();
        assertThat(extraInLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExtraInLine() throws Exception {
        // Initialize the database
        extraInLineRepository.saveAndFlush(extraInLine);

        int databaseSizeBeforeDelete = extraInLineRepository.findAll().size();

        // Delete the extraInLine
        restExtraInLineMockMvc.perform(delete("/api/extra-in-lines/{id}", extraInLine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExtraInLine> extraInLineList = extraInLineRepository.findAll();
        assertThat(extraInLineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExtraInLine.class);
        ExtraInLine extraInLine1 = new ExtraInLine();
        extraInLine1.setId(1L);
        ExtraInLine extraInLine2 = new ExtraInLine();
        extraInLine2.setId(extraInLine1.getId());
        assertThat(extraInLine1).isEqualTo(extraInLine2);
        extraInLine2.setId(2L);
        assertThat(extraInLine1).isNotEqualTo(extraInLine2);
        extraInLine1.setId(null);
        assertThat(extraInLine1).isNotEqualTo(extraInLine2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExtraInLineDTO.class);
        ExtraInLineDTO extraInLineDTO1 = new ExtraInLineDTO();
        extraInLineDTO1.setId(1L);
        ExtraInLineDTO extraInLineDTO2 = new ExtraInLineDTO();
        assertThat(extraInLineDTO1).isNotEqualTo(extraInLineDTO2);
        extraInLineDTO2.setId(extraInLineDTO1.getId());
        assertThat(extraInLineDTO1).isEqualTo(extraInLineDTO2);
        extraInLineDTO2.setId(2L);
        assertThat(extraInLineDTO1).isNotEqualTo(extraInLineDTO2);
        extraInLineDTO1.setId(null);
        assertThat(extraInLineDTO1).isNotEqualTo(extraInLineDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(extraInLineMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(extraInLineMapper.fromId(null)).isNull();
    }
}
