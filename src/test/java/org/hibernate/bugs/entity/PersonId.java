package org.hibernate.bugs.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class PersonId {
    private long code;

    public PersonId() {}

    public PersonId(long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
    }
}
