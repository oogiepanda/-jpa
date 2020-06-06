package com.oogie.controller;

import com.oogie.model.CredentialsEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

import static com.mysql.cj.util.StringUtils.isNullOrEmpty;

public class CredentialsServiceJPA extends BaseServiceJPA{
    public CredentialsServiceJPA(EntityManager entityManager) {
        super(entityManager);
    }

    public int create(CredentialsEntity ce) {
        entityManager.getTransaction().begin();
        CredentialsEntity credentials = clone(ce);
        entityManager.persist(credentials);
        Query query = entityManager.createNativeQuery("select max(id) from credentials");
        int val = (int) query.getSingleResult();
        entityManager.getTransaction().commit();
        return val;
    }

    private CredentialsEntity clone(CredentialsEntity credentialsEntity) {
        CredentialsEntity clone = new CredentialsEntity();
        clone.setUsername(credentialsEntity.getUsername());
        clone.setPassword(credentialsEntity.getPassword());
        clone.setAffiliation(credentialsEntity.getAffiliation());
        return clone;
    }

    public CredentialsEntity retrieve(int id) {
        entityManager.getTransaction().begin();
        CredentialsEntity credentialsEntity = entityManager.find(CredentialsEntity.class, id);
        entityManager.getTransaction().commit();
        return credentialsEntity;
    }

    public List<CredentialsEntity> retrieve(CredentialsEntity ce) {
        if (isEmpty(ce)) {
            return Collections.emptyList();
        }
        StringBuilder sql = new StringBuilder("select c from CredentialsEntity c where 1 = 1");
        if (!isNullOrEmpty(ce.getUsername())) {
            sql.append(" and username = '").append(ce.getUsername()).append("'");
        }
        if (!isNullOrEmpty(ce.getPassword())) {
            sql.append(" and password = '").append(ce.getPassword()).append("'");
        }
        if (ce.getAffiliation() != null) {
            sql.append(" and affiliation = ").append(ce.getAffiliation()).append(" ");
        }
        System.out.println(sql);
        TypedQuery<CredentialsEntity> query = entityManager.createQuery(sql.toString(), CredentialsEntity.class);
        List<CredentialsEntity> credentials = query.getResultList();
        return credentials;
    }

    private boolean isEmpty(CredentialsEntity credentials) {
        return (isNullOrEmpty(credentials.getUsername()) &&
                isNullOrEmpty(credentials.getPassword()) &&
                credentials.getAffiliation() == null);
    }

    public void update(CredentialsEntity ce, int id) {
        CredentialsEntity credentialsEntity = entityManager.find(CredentialsEntity.class, id);
        entityManager.getTransaction().begin();
        if(ce.getUsername() != null && !ce.getUsername().isEmpty()) {
            credentialsEntity.setUsername(ce.getUsername());
        }
        if(ce.getPassword() != null && !ce.getPassword().isEmpty()) {
            credentialsEntity.setPassword(ce.getPassword());
        }
        if(ce.getAffiliation() != null) {
            credentialsEntity.setAffiliation(ce.getAffiliation());
        }
        entityManager.getTransaction().commit();
    }

    public void delete(int id) {
        entityManager.getTransaction().begin();
        CredentialsEntity credentialsEntity = entityManager.find(CredentialsEntity.class, id);
        entityManager.remove(credentialsEntity);
        entityManager.getTransaction().commit();
    }
}
