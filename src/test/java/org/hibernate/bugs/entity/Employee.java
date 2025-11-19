package org.hibernate.bugs.entity;

import jakarta.persistence.Entity;

@Entity
public class Employee extends BaseEntity<EmployeeId> {
    public Employee() {}

    public Employee(EmployeeId id) {
        super(id);
    }
}
