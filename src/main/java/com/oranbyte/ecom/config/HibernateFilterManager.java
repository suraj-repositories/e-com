package com.oranbyte.ecom.config;

import org.hibernate.Session;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HibernateFilterManager {

    private final EntityManager entityManager;

    public void enableActiveFilter() {
        Session session = entityManager.unwrap(Session.class);

        if (session.getEnabledFilter("activeFilter") == null) {
            session.enableFilter("activeFilter");
        }
    }

    public void disableActiveFilter() {
        Session session = entityManager.unwrap(Session.class);

        if (session.getEnabledFilter("activeFilter") != null) {
            session.disableFilter("activeFilter");
        }
    }
}