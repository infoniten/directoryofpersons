package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PersonTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Person getPersonSample1() {
        return new Person()
            .id(1L)
            .nameAndFirstName("nameAndFirstName1")
            .lastEvent("lastEvent1")
            .phoneNumber("phoneNumber1")
            .email("email1")
            .eventCount(1)
            .ticketCount(1)
            .allEvents("allEvents1")
            .mailings("mailings1")
            .regionResidence("regionResidence1");
    }

    public static Person getPersonSample2() {
        return new Person()
            .id(2L)
            .nameAndFirstName("nameAndFirstName2")
            .lastEvent("lastEvent2")
            .phoneNumber("phoneNumber2")
            .email("email2")
            .eventCount(2)
            .ticketCount(2)
            .allEvents("allEvents2")
            .mailings("mailings2")
            .regionResidence("regionResidence2");
    }

    public static Person getPersonRandomSampleGenerator() {
        return new Person()
            .id(longCount.incrementAndGet())
            .nameAndFirstName(UUID.randomUUID().toString())
            .lastEvent(UUID.randomUUID().toString())
            .phoneNumber(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .eventCount(intCount.incrementAndGet())
            .ticketCount(intCount.incrementAndGet())
            .allEvents(UUID.randomUUID().toString())
            .mailings(UUID.randomUUID().toString())
            .regionResidence(UUID.randomUUID().toString());
    }
}
