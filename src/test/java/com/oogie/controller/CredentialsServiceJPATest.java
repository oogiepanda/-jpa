package com.oogie.controller;

import com.oogie.BaseTest;
import com.oogie.model.CredentialsEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

class CredentialsServiceJPATest extends BaseTest {

    private static CredentialsServiceJPA credentialsServiceJPA;
    private static EntityManager entityManager;
    private static EntityManagerFactory emfactory;
    private static final String NAME = "May";

    @BeforeAll
    public static void config() {
        emfactory = Persistence.createEntityManagerFactory("discography");
        entityManager = emfactory.createEntityManager();
        credentialsServiceJPA = new CredentialsServiceJPA(entityManager);
    }

    @AfterAll
    public static void destroy() {
        entityManager.close();
        emfactory.close();
    }

    private CredentialsEntity createCredentialsEntity() {
        CredentialsEntity credentialsEntity = new CredentialsEntity();
        credentialsEntity.setUsername("Hello");
        credentialsEntity.setPassword("meow");
        credentialsEntity.setAffiliation(1);
        return credentialsEntity;
    }

    @Test
    public void create() {
        CredentialsEntity original = createCredentialsEntity();
        int id = credentialsServiceJPA.create(original);
    }

    @Test
    public void crudJPA() {
        CredentialsEntity original = createCredentialsEntity();
        int id = credentialsServiceJPA.create(original);
        CredentialsEntity nuCred = credentialsServiceJPA.retrieve(id);
        assertThat(nuCred.getUsername(), is(original.getUsername()));

        nuCred.setUsername(NAME);
        credentialsServiceJPA.update(nuCred, id);
        CredentialsEntity thirdCred = credentialsServiceJPA.retrieve(id);
        assertThat(thirdCred.getUsername(), is(NAME));

        credentialsServiceJPA.delete(id);
        CredentialsEntity fourthCred = credentialsServiceJPA.retrieve(id);
        assertThat(fourthCred, is(nullValue()));
    }

    @Test
    public void retrieveNoResults() {
        CredentialsEntity searchCred = new CredentialsEntity();
        List<CredentialsEntity> credentials = credentialsServiceJPA.retrieve(searchCred);
        assertThat(credentials.isEmpty(), is(true));
    }

    @Test
    public void retrieveOneResult() {
        CredentialsEntity original = createCredentialsEntity();
        int id = credentialsServiceJPA.create(original);
        CredentialsEntity searchCred = new CredentialsEntity();
        searchCred.setUsername(original.getUsername());
        List<CredentialsEntity> credentials = credentialsServiceJPA.retrieve(searchCred);
        assertThat(credentials.size(), is(1));
        credentialsServiceJPA.delete(id);
    }

    @Test
    public void retrieveManyResults() {
        CredentialsEntity original = createCredentialsEntity();
        List<CredentialsEntity> nuCredentials = createAndStoreId(3);
        CredentialsEntity searchCred = new CredentialsEntity();
        searchCred.setUsername(original.getUsername());
        List<CredentialsEntity> credentials = credentialsServiceJPA.retrieve(searchCred);
        assertThat(credentials.size(), is(3));
        for (CredentialsEntity c : nuCredentials) {
            credentialsServiceJPA.delete(c.getId());
        }
    }

    private List<CredentialsEntity> createAndStoreId(int counter) {
        List<CredentialsEntity> credentials = new ArrayList<>();
        for (int i = 0; i < counter; i++) {
            CredentialsEntity nuCred = createCredentialsEntity();
            int id = credentialsServiceJPA.create(nuCred);
            CredentialsEntity clone = clone(nuCred, id);
            credentials.add(clone);
        }
        return credentials;
    }

    private CredentialsEntity clone(CredentialsEntity credentials, int id) {
        CredentialsEntity clone = new CredentialsEntity();
        clone.setId(id);
        clone.setUsername(credentials.getUsername());
        clone.setPassword(credentials.getPassword());
        clone.setAffiliation(credentials.getAffiliation());
        return clone;
    }
}