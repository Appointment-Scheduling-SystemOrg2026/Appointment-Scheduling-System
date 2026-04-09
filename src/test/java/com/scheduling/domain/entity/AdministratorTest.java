package com.scheduling.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdministratorTest {

    @Test
    void isAdmin_shouldReturnTrueSupplier() {
        Administrator admin = new Administrator("a", "b");

        assertTrue(admin.isAdmin().getAsBoolean());
    }
}




