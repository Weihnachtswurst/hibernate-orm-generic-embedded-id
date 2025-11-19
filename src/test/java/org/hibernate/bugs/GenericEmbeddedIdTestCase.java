package org.hibernate.bugs;

import org.hibernate.bugs.entity.Employee;
import org.hibernate.bugs.entity.EmployeeId_;
import org.hibernate.bugs.entity.Employee_;
import org.hibernate.bugs.entity.Visitor;
import org.hibernate.bugs.entity.Visitor_;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
class GenericEmbeddedIdTestCase {

    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
    }

    @AfterEach
    void destroy() {
        entityManagerFactory.close();
    }

    //  Compilation Fails with:
    //  no suitable method found for get(jakarta.persistence.metamodel.SingularAttribute<org.hibernate.bugs.entity.EmployeeId,org.hibernate.bugs.entity.PersonId>)
    @Test
    void testCompareUsingCriteriaStaticMetamodel() throws Exception {
        var criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(Employee.class);

        var employee = query.from(Employee.class);
        var visitor = query.from(Visitor.class);

        var visitorPersonId = visitor.get(Visitor_.id);
        var employeePersonId = employee.get(Employee_.id).get(EmployeeId_.personId);

        var equal = criteriaBuilder.equal(employeePersonId, visitorPersonId);

        Assertions.assertDoesNotThrow(() -> query.select(employee).where(equal));
    }

    @Test
    //  Fails with:
    //  Semantic Cannot compare tuples of different lengths
    void testCompareUsingCriteriaAttributeNames() throws Exception {
        var entityManager = entityManagerFactory.createEntityManager();
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(Employee.class);

        var employee = query.from(Employee.class);
        var visitor = query.from(Visitor.class);

        var equal = criteriaBuilder.equal(employee.get(Employee_.ID).get(EmployeeId_.PERSON_ID), visitor.get(Visitor_.ID));

        // Select every Employee Entity that is also a Visitor
        query.select(employee).where(equal);

        Assertions.assertDoesNotThrow(() -> entityManager.createQuery(query)
                .getResultStream()
                .toList());
    }

    @Test
    // Fails with:
    // Semantic Cannot compare tuples of different lengths
    void testCompareUsingJpql() throws Exception {
        var entityManager = entityManagerFactory.createEntityManager();
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(Employee.class);

        var employee = query.from(Employee.class);
        var visitor = query.from(Visitor.class);

        var equal = criteriaBuilder.equal(employee.get(Employee_.ID).get(EmployeeId_.PERSON_ID), visitor.get(Visitor_.ID));

        // Select every Employee Entity that is also a Visitor
        query.select(employee).where(equal);

        Assertions.assertDoesNotThrow(() -> entityManager.createQuery(query)
                .getResultStream()
                .toList());
    }
}
