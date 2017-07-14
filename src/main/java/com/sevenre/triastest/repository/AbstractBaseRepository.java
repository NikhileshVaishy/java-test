package com.sevenre.triastest.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by nikhilesh on 13/07/17.
 */
@Repository
public class AbstractBaseRepository {

    private static final Logger logger = LogManager.getLogger(AbstractBaseRepository.class);
    private ThreadLocal<Session> currentSession = new ThreadLocal();

    @PersistenceContext
    EntityManager entityManager;

    public Serializable create(Object entity) {
        try {
            return this.getCurrentSession().save(entity);
        } catch (ConstraintViolationException e) {
            logger.error("Exception : ", e);
            throw new ConstraintViolationException("Constrain Violation Exception", new SQLException(), e.getConstraintName());
        } catch (HibernateException var4) {
            logger.error("Exception : ", var4);
            throw new HibernateException("DAO Exception", var4);
        }
    }

    private Session getCurrentSession() {
        Session session = currentSession.get();
        if (null != session) {
            return session;
        } else {
            return entityManager.unwrap(Session.class);
        }
    }
}
