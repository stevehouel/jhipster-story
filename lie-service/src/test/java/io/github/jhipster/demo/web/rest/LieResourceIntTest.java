package io.github.jhipster.demo.web.rest;

import io.github.jhipster.demo.LieServiceApp;

import io.github.jhipster.demo.domain.Lie;
import io.github.jhipster.demo.repository.LieRepository;
import io.github.jhipster.demo.web.rest.errors.ExceptionTranslator;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LieResource REST controller.
 *
 * @see LieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LieServiceApp.class)
public class LieResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private LieRepository lieRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restLieMockMvc;

    private Lie lie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LieResource lieResource = new LieResource(lieRepository);
        this.restLieMockMvc = MockMvcBuilders.standaloneSetup(lieResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lie createEntity() {
        Lie lie = new Lie();
        lie.setTitle(DEFAULT_TITLE);
        lie.setContent(DEFAULT_CONTENT);
        return lie;
    }

    @Before
    public void initTest() {
        lieRepository.deleteAll();
        lie = createEntity();
    }

    @Test
    public void createLie() throws Exception {
        int databaseSizeBeforeCreate = lieRepository.findAll().size();

        // Create the Lie
        restLieMockMvc.perform(post("/api/lies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lie)))
            .andExpect(status().isCreated());

        // Validate the Lie in the database
        List<Lie> lieList = lieRepository.findAll();
        assertThat(lieList).hasSize(databaseSizeBeforeCreate + 1);
        Lie testLie = lieList.get(lieList.size() - 1);
        assertThat(testLie.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testLie.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    public void createLieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lieRepository.findAll().size();

        // Create the Lie with an existing ID
        lie.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restLieMockMvc.perform(post("/api/lies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lie)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Lie> lieList = lieRepository.findAll();
        assertThat(lieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllLies() throws Exception {
        // Initialize the database
        lieRepository.save(lie);

        // Get all the lieList
        restLieMockMvc.perform(get("/api/lies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lie.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }

    @Test
    public void getLie() throws Exception {
        // Initialize the database
        lieRepository.save(lie);

        // Get the lie
        restLieMockMvc.perform(get("/api/lies/{id}", lie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lie.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()));
    }

    @Test
    public void getNonExistingLie() throws Exception {
        // Get the lie
        restLieMockMvc.perform(get("/api/lies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateLie() throws Exception {
        // Initialize the database
        lieRepository.save(lie);
        int databaseSizeBeforeUpdate = lieRepository.findAll().size();

        // Update the lie
        Lie updatedLie = lieRepository.findOne(lie.getId());
        updatedLie.setTitle(UPDATED_TITLE);
        updatedLie.setContent(UPDATED_CONTENT);

        restLieMockMvc.perform(put("/api/lies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLie)))
            .andExpect(status().isOk());

        // Validate the Lie in the database
        List<Lie> lieList = lieRepository.findAll();
        assertThat(lieList).hasSize(databaseSizeBeforeUpdate);
        Lie testLie = lieList.get(lieList.size() - 1);
        assertThat(testLie.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testLie.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    public void updateNonExistingLie() throws Exception {
        int databaseSizeBeforeUpdate = lieRepository.findAll().size();

        // Create the Lie

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLieMockMvc.perform(put("/api/lies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lie)))
            .andExpect(status().isCreated());

        // Validate the Lie in the database
        List<Lie> lieList = lieRepository.findAll();
        assertThat(lieList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteLie() throws Exception {
        // Initialize the database
        lieRepository.save(lie);
        int databaseSizeBeforeDelete = lieRepository.findAll().size();

        // Get the lie
        restLieMockMvc.perform(delete("/api/lies/{id}", lie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lie> lieList = lieRepository.findAll();
        assertThat(lieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lie.class);
    }
}
