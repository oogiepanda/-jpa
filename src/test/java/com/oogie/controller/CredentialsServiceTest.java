package com.oogie.controller;

import com.oogie.BaseTest;
import com.oogie.Credentials;
import org.junit.Test;

import static org.junit.Assert.*;

public class CredentialsServiceTest extends BaseTest {

    private Credentials createCredentials() {
        Credentials credentials = new Credentials();
        credentials.setId(1);
        return credentials;
    }

    @Test
    public void create() {
    }

    @Test
    public void retrieve() {
        Credentials credentials = createCredentials();
        CredentialsService credentialsService = new CredentialsService(conn);

        Credentials retval = credentialsService.retrieve(credentials);
        assertTrue(credentials.getId() == retval.getId());
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}