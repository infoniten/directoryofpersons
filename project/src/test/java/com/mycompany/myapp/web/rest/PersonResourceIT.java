package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.PersonAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Person;
import com.mycompany.myapp.repository.PersonRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PersonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonResourceIT {

    private static final String DEFAULT_NAME_AND_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME_AND_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_EVENT = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EVENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LAST_EVENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_EVENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_EVENT_COUNT = 1;
    private static final Integer UPDATED_EVENT_COUNT = 2;

    private static final Integer DEFAULT_TICKET_COUNT = 1;
    private static final Integer UPDATED_TICKET_COUNT = 2;

    private static final LocalDate DEFAULT_FIRST_EVENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIRST_EVENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_SOLD_FOR_THE_AMOUNT = 1D;
    private static final Double UPDATED_SOLD_FOR_THE_AMOUNT = 2D;

    private static final String DEFAULT_ALL_EVENTS = "AAAAAAAAAA";
    private static final String UPDATED_ALL_EVENTS = "BBBBBBBBBB";

    private static final String DEFAULT_MAILINGS = "AAAAAAAAAA";
    private static final String UPDATED_MAILINGS = "BBBBBBBBBB";

    private static final String DEFAULT_REGION_RESIDENCE = "AAAAAAAAAA";
    private static final String UPDATED_REGION_RESIDENCE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/people";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonMockMvc;

    private Person person;

    private Person insertedPerson;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createEntity(EntityManager em) {
        Person person = new Person()
            .nameAndFirstName(DEFAULT_NAME_AND_FIRST_NAME)
            .lastEvent(DEFAULT_LAST_EVENT)
            .lastEventDate(DEFAULT_LAST_EVENT_DATE)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .email(DEFAULT_EMAIL)
            .eventCount(DEFAULT_EVENT_COUNT)
            .ticketCount(DEFAULT_TICKET_COUNT)
            .firstEventDate(DEFAULT_FIRST_EVENT_DATE)
            .soldForTheAmount(DEFAULT_SOLD_FOR_THE_AMOUNT)
            .allEvents(DEFAULT_ALL_EVENTS)
            .mailings(DEFAULT_MAILINGS)
            .regionResidence(DEFAULT_REGION_RESIDENCE);
        return person;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createUpdatedEntity(EntityManager em) {
        Person person = new Person()
            .nameAndFirstName(UPDATED_NAME_AND_FIRST_NAME)
            .lastEvent(UPDATED_LAST_EVENT)
            .lastEventDate(UPDATED_LAST_EVENT_DATE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .eventCount(UPDATED_EVENT_COUNT)
            .ticketCount(UPDATED_TICKET_COUNT)
            .firstEventDate(UPDATED_FIRST_EVENT_DATE)
            .soldForTheAmount(UPDATED_SOLD_FOR_THE_AMOUNT)
            .allEvents(UPDATED_ALL_EVENTS)
            .mailings(UPDATED_MAILINGS)
            .regionResidence(UPDATED_REGION_RESIDENCE);
        return person;
    }

    @BeforeEach
    public void initTest() {
        person = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPerson != null) {
            personRepository.delete(insertedPerson);
            insertedPerson = null;
        }
    }

    @Test
    @Transactional
    void createPerson() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Person
        var returnedPerson = om.readValue(
            restPersonMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(person)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Person.class
        );

        // Validate the Person in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPersonUpdatableFieldsEquals(returnedPerson, getPersistedPerson(returnedPerson));

        insertedPerson = returnedPerson;
    }

    @Test
    @Transactional
    void createPersonWithExistingId() throws Exception {
        // Create the Person with an existing ID
        person.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(person)))
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPeople() throws Exception {
        // Initialize the database
        insertedPerson = personRepository.saveAndFlush(person);

        // Get all the personList
        restPersonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameAndFirstName").value(hasItem(DEFAULT_NAME_AND_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastEvent").value(hasItem(DEFAULT_LAST_EVENT)))
            .andExpect(jsonPath("$.[*].lastEventDate").value(hasItem(DEFAULT_LAST_EVENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].eventCount").value(hasItem(DEFAULT_EVENT_COUNT)))
            .andExpect(jsonPath("$.[*].ticketCount").value(hasItem(DEFAULT_TICKET_COUNT)))
            .andExpect(jsonPath("$.[*].firstEventDate").value(hasItem(DEFAULT_FIRST_EVENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].soldForTheAmount").value(hasItem(DEFAULT_SOLD_FOR_THE_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].allEvents").value(hasItem(DEFAULT_ALL_EVENTS)))
            .andExpect(jsonPath("$.[*].mailings").value(hasItem(DEFAULT_MAILINGS)))
            .andExpect(jsonPath("$.[*].regionResidence").value(hasItem(DEFAULT_REGION_RESIDENCE)));
    }

    @Test
    @Transactional
    void getPerson() throws Exception {
        // Initialize the database
        insertedPerson = personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc
            .perform(get(ENTITY_API_URL_ID, person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.nameAndFirstName").value(DEFAULT_NAME_AND_FIRST_NAME))
            .andExpect(jsonPath("$.lastEvent").value(DEFAULT_LAST_EVENT))
            .andExpect(jsonPath("$.lastEventDate").value(DEFAULT_LAST_EVENT_DATE.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.eventCount").value(DEFAULT_EVENT_COUNT))
            .andExpect(jsonPath("$.ticketCount").value(DEFAULT_TICKET_COUNT))
            .andExpect(jsonPath("$.firstEventDate").value(DEFAULT_FIRST_EVENT_DATE.toString()))
            .andExpect(jsonPath("$.soldForTheAmount").value(DEFAULT_SOLD_FOR_THE_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.allEvents").value(DEFAULT_ALL_EVENTS))
            .andExpect(jsonPath("$.mailings").value(DEFAULT_MAILINGS))
            .andExpect(jsonPath("$.regionResidence").value(DEFAULT_REGION_RESIDENCE));
    }

    @Test
    @Transactional
    void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPerson() throws Exception {
        // Initialize the database
        insertedPerson = personRepository.saveAndFlush(person);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the person
        Person updatedPerson = personRepository.findById(person.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPerson are not directly saved in db
        em.detach(updatedPerson);
        updatedPerson
            .nameAndFirstName(UPDATED_NAME_AND_FIRST_NAME)
            .lastEvent(UPDATED_LAST_EVENT)
            .lastEventDate(UPDATED_LAST_EVENT_DATE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .eventCount(UPDATED_EVENT_COUNT)
            .ticketCount(UPDATED_TICKET_COUNT)
            .firstEventDate(UPDATED_FIRST_EVENT_DATE)
            .soldForTheAmount(UPDATED_SOLD_FOR_THE_AMOUNT)
            .allEvents(UPDATED_ALL_EVENTS)
            .mailings(UPDATED_MAILINGS)
            .regionResidence(UPDATED_REGION_RESIDENCE);

        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPerson.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPerson))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPersonToMatchAllProperties(updatedPerson);
    }

    @Test
    @Transactional
    void putNonExistingPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        person.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(put(ENTITY_API_URL_ID, person.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(person)))
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        person.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(person))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        person.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(person)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Person in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonWithPatch() throws Exception {
        // Initialize the database
        insertedPerson = personRepository.saveAndFlush(person);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the person using partial update
        Person partialUpdatedPerson = new Person();
        partialUpdatedPerson.setId(person.getId());

        partialUpdatedPerson
            .lastEvent(UPDATED_LAST_EVENT)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .ticketCount(UPDATED_TICKET_COUNT)
            .allEvents(UPDATED_ALL_EVENTS)
            .mailings(UPDATED_MAILINGS)
            .regionResidence(UPDATED_REGION_RESIDENCE);

        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPerson))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersonUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPerson, person), getPersistedPerson(person));
    }

    @Test
    @Transactional
    void fullUpdatePersonWithPatch() throws Exception {
        // Initialize the database
        insertedPerson = personRepository.saveAndFlush(person);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the person using partial update
        Person partialUpdatedPerson = new Person();
        partialUpdatedPerson.setId(person.getId());

        partialUpdatedPerson
            .nameAndFirstName(UPDATED_NAME_AND_FIRST_NAME)
            .lastEvent(UPDATED_LAST_EVENT)
            .lastEventDate(UPDATED_LAST_EVENT_DATE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .eventCount(UPDATED_EVENT_COUNT)
            .ticketCount(UPDATED_TICKET_COUNT)
            .firstEventDate(UPDATED_FIRST_EVENT_DATE)
            .soldForTheAmount(UPDATED_SOLD_FOR_THE_AMOUNT)
            .allEvents(UPDATED_ALL_EVENTS)
            .mailings(UPDATED_MAILINGS)
            .regionResidence(UPDATED_REGION_RESIDENCE);

        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPerson))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersonUpdatableFieldsEquals(partialUpdatedPerson, getPersistedPerson(partialUpdatedPerson));
    }

    @Test
    @Transactional
    void patchNonExistingPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        person.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, person.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(person))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        person.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(person))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        person.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(person)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Person in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePerson() throws Exception {
        // Initialize the database
        insertedPerson = personRepository.saveAndFlush(person);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the person
        restPersonMockMvc
            .perform(delete(ENTITY_API_URL_ID, person.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return personRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Person getPersistedPerson(Person person) {
        return personRepository.findById(person.getId()).orElseThrow();
    }

    protected void assertPersistedPersonToMatchAllProperties(Person expectedPerson) {
        assertPersonAllPropertiesEquals(expectedPerson, getPersistedPerson(expectedPerson));
    }

    protected void assertPersistedPersonToMatchUpdatableProperties(Person expectedPerson) {
        assertPersonAllUpdatablePropertiesEquals(expectedPerson, getPersistedPerson(expectedPerson));
    }
}
