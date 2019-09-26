package com.moonshine.web.rest;

import com.moonshine.EasyOrderApp;

import com.moonshine.domain.Extra;
import com.moonshine.repository.ExtraRepository;
import com.moonshine.service.ExtraService;
import com.moonshine.service.dto.ExtraDTO;
import com.moonshine.service.mapper.ExtraMapper;
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
 * Test class for the ExtraResource REST controller.
 *
 * @see ExtraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EasyOrderApp.class)
public class ExtraResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private ExtraRepository extraRepository;

    @Autowired
    private ExtraMapper extraMapper;

    @Autowired
    private ExtraService extraService;

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

    private MockMvc restExtraMockMvc;

    private Extra extra;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExtraResource extraResource = new ExtraResource(extraService);
        this.restExtraMockMvc = MockMvcBuilders.standaloneSetup(extraResource)
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
    public static Extra createEntity(EntityManager em) {
        Extra extra = new Extra()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE)
            .status(DEFAULT_STATUS);
        return extra;
    }

    @Before
    public void initTest() {
        extra = createEntity(em);
    }

    @Test
    @Transactional
    public void createExtra() throws Exception {
        int databaseSizeBeforeCreate = extraRepository.findAll().size();

        // Create the Extra
        ExtraDTO extraDTO = extraMapper.toDto(extra);
        restExtraMockMvc.perform(post("/api/extras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraDTO)))
            .andExpect(status().isCreated());

        // Validate the Extra in the database
        List<Extra> extraList = extraRepository.findAll();
        assertThat(extraList).hasSize(databaseSizeBeforeCreate + 1);
        Extra testExtra = extraList.get(extraList.size() - 1);
        assertThat(testExtra.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExtra.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testExtra.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createExtraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = extraRepository.findAll().size();

        // Create the Extra with an existing ID
        extra.setId(1L);
        ExtraDTO extraDTO = extraMapper.toDto(extra);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExtraMockMvc.perform(post("/api/extras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Extra in the database
        List<Extra> extraList = extraRepository.findAll();
        assertThat(extraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = extraRepository.findAll().size();
        // set the field null
        extra.setName(null);

        // Create the Extra, which fails.
        ExtraDTO extraDTO = extraMapper.toDto(extra);

        restExtraMockMvc.perform(post("/api/extras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraDTO)))
            .andExpect(status().isBadRequest());

        List<Extra> extraList = extraRepository.findAll();
        assertThat(extraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = extraRepository.findAll().size();
        // set the field null
        extra.setPrice(null);

        // Create the Extra, which fails.
        ExtraDTO extraDTO = extraMapper.toDto(extra);

        restExtraMockMvc.perform(post("/api/extras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraDTO)))
            .andExpect(status().isBadRequest());

        List<Extra> extraList = extraRepository.findAll();
        assertThat(extraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = extraRepository.findAll().size();
        // set the field null
        extra.setStatus(null);

        // Create the Extra, which fails.
        ExtraDTO extraDTO = extraMapper.toDto(extra);

        restExtraMockMvc.perform(post("/api/extras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraDTO)))
            .andExpect(status().isBadRequest());

        List<Extra> extraList = extraRepository.findAll();
        assertThat(extraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExtras() throws Exception {
        // Initialize the database
        extraRepository.saveAndFlush(extra);

        // Get all the extraList
        restExtraMockMvc.perform(get("/api/extras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(extra.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getExtra() throws Exception {
        // Initialize the database
        extraRepository.saveAndFlush(extra);

        // Get the extra
        restExtraMockMvc.perform(get("/api/extras/{id}", extra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(extra.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingExtra() throws Exception {
        // Get the extra
        restExtraMockMvc.perform(get("/api/extras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExtra() throws Exception {
        // Initialize the database
        extraRepository.saveAndFlush(extra);

        int databaseSizeBeforeUpdate = extraRepository.findAll().size();

        // Update the extra
        Extra updatedExtra = extraRepository.findById(extra.getId()).get();
        // Disconnect from session so that the updates on updatedExtra are not directly saved in db
        em.detach(updatedExtra);
        updatedExtra
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .status(UPDATED_STATUS);
        ExtraDTO extraDTO = extraMapper.toDto(updatedExtra);

        restExtraMockMvc.perform(put("/api/extras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraDTO)))
            .andExpect(status().isOk());

        // Validate the Extra in the database
        List<Extra> extraList = extraRepository.findAll();
        assertThat(extraList).hasSize(databaseSizeBeforeUpdate);
        Extra testExtra = extraList.get(extraList.size() - 1);
        assertThat(testExtra.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExtra.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testExtra.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingExtra() throws Exception {
        int databaseSizeBeforeUpdate = extraRepository.findAll().size();

        // Create the Extra
        ExtraDTO extraDTO = extraMapper.toDto(extra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExtraMockMvc.perform(put("/api/extras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(extraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Extra in the database
        List<Extra> extraList = extraRepository.findAll();
        assertThat(extraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExtra() throws Exception {
        // Initialize the database
        extraRepository.saveAndFlush(extra);

        int databaseSizeBeforeDelete = extraRepository.findAll().size();

        // Delete the extra
        restExtraMockMvc.perform(delete("/api/extras/{id}", extra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Extra> extraList = extraRepository.findAll();
        assertThat(extraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Extra.class);
        Extra extra1 = new Extra();
        extra1.setId(1L);
        Extra extra2 = new Extra();
        extra2.setId(extra1.getId());
        assertThat(extra1).isEqualTo(extra2);
        extra2.setId(2L);
        assertThat(extra1).isNotEqualTo(extra2);
        extra1.setId(null);
        assertThat(extra1).isNotEqualTo(extra2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExtraDTO.class);
        ExtraDTO extraDTO1 = new ExtraDTO();
        extraDTO1.setId(1L);
        ExtraDTO extraDTO2 = new ExtraDTO();
        assertThat(extraDTO1).isNotEqualTo(extraDTO2);
        extraDTO2.setId(extraDTO1.getId());
        assertThat(extraDTO1).isEqualTo(extraDTO2);
        extraDTO2.setId(2L);
        assertThat(extraDTO1).isNotEqualTo(extraDTO2);
        extraDTO1.setId(null);
        assertThat(extraDTO1).isNotEqualTo(extraDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(extraMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(extraMapper.fromId(null)).isNull();
    }
}
