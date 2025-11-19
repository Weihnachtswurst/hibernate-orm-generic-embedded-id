package org.hibernate.bugs.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Visitor extends BaseEntity<PersonId>{

    public Visitor() {
    }

    public Visitor(PersonId personId) {
        super(personId);
    }
}
