package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name_and_first_name")
    private String nameAndFirstName;

    @Column(name = "last_event")
    private String lastEvent;

    @Column(name = "last_event_date")
    private LocalDate lastEventDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "event_count")
    private Integer eventCount;

    @Column(name = "ticket_count")
    private Integer ticketCount;

    @Column(name = "first_event_date")
    private LocalDate firstEventDate;

    @Column(name = "sold_for_the_amount")
    private Double soldForTheAmount;

    @Column(name = "all_events")
    private String allEvents;

    @Column(name = "mailings")
    private String mailings;

    @Column(name = "region_residence")
    private String regionResidence;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Person id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameAndFirstName() {
        return this.nameAndFirstName;
    }

    public Person nameAndFirstName(String nameAndFirstName) {
        this.setNameAndFirstName(nameAndFirstName);
        return this;
    }

    public void setNameAndFirstName(String nameAndFirstName) {
        this.nameAndFirstName = nameAndFirstName;
    }

    public String getLastEvent() {
        return this.lastEvent;
    }

    public Person lastEvent(String lastEvent) {
        this.setLastEvent(lastEvent);
        return this;
    }

    public void setLastEvent(String lastEvent) {
        this.lastEvent = lastEvent;
    }

    public LocalDate getLastEventDate() {
        return this.lastEventDate;
    }

    public Person lastEventDate(LocalDate lastEventDate) {
        this.setLastEventDate(lastEventDate);
        return this;
    }

    public void setLastEventDate(LocalDate lastEventDate) {
        this.lastEventDate = lastEventDate;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Person phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public Person email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEventCount() {
        return this.eventCount;
    }

    public Person eventCount(Integer eventCount) {
        this.setEventCount(eventCount);
        return this;
    }

    public void setEventCount(Integer eventCount) {
        this.eventCount = eventCount;
    }

    public Integer getTicketCount() {
        return this.ticketCount;
    }

    public Person ticketCount(Integer ticketCount) {
        this.setTicketCount(ticketCount);
        return this;
    }

    public void setTicketCount(Integer ticketCount) {
        this.ticketCount = ticketCount;
    }

    public LocalDate getFirstEventDate() {
        return this.firstEventDate;
    }

    public Person firstEventDate(LocalDate firstEventDate) {
        this.setFirstEventDate(firstEventDate);
        return this;
    }

    public void setFirstEventDate(LocalDate firstEventDate) {
        this.firstEventDate = firstEventDate;
    }

    public Double getSoldForTheAmount() {
        return this.soldForTheAmount;
    }

    public Person soldForTheAmount(Double soldForTheAmount) {
        this.setSoldForTheAmount(soldForTheAmount);
        return this;
    }

    public void setSoldForTheAmount(Double soldForTheAmount) {
        this.soldForTheAmount = soldForTheAmount;
    }

    public String getAllEvents() {
        return this.allEvents;
    }

    public Person allEvents(String allEvents) {
        this.setAllEvents(allEvents);
        return this;
    }

    public void setAllEvents(String allEvents) {
        this.allEvents = allEvents;
    }

    public String getMailings() {
        return this.mailings;
    }

    public Person mailings(String mailings) {
        this.setMailings(mailings);
        return this;
    }

    public void setMailings(String mailings) {
        this.mailings = mailings;
    }

    public String getRegionResidence() {
        return this.regionResidence;
    }

    public Person regionResidence(String regionResidence) {
        this.setRegionResidence(regionResidence);
        return this;
    }

    public void setRegionResidence(String regionResidence) {
        this.regionResidence = regionResidence;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return getId() != null && getId().equals(((Person) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", nameAndFirstName='" + getNameAndFirstName() + "'" +
            ", lastEvent='" + getLastEvent() + "'" +
            ", lastEventDate='" + getLastEventDate() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", eventCount=" + getEventCount() +
            ", ticketCount=" + getTicketCount() +
            ", firstEventDate='" + getFirstEventDate() + "'" +
            ", soldForTheAmount=" + getSoldForTheAmount() +
            ", allEvents='" + getAllEvents() + "'" +
            ", mailings='" + getMailings() + "'" +
            ", regionResidence='" + getRegionResidence() + "'" +
            "}";
    }
}
