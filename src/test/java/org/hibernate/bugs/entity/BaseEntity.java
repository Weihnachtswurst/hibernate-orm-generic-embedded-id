package org.hibernate.bugs.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity<T> {
    @EmbeddedId
    private T id;

    public BaseEntity() {
    }

    public BaseEntity(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

}
