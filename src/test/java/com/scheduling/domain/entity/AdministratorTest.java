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




/*package com.scheduling.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdministratorTest {
	@Test
    void shouldCreateAdmin() {

        Administrator admin =
                new Administrator("admin","123");

        assertEquals("admin", admin.getUsername());
    }
}*/
/*
    @Test
    void shouldLoginAndLogout() {

        Administrator admin =
                new Administrator("admin", "123");

        assertTrue(admin.login("admin", "123"));

        admin.logout();

        assertFalse(admin.isLoggedIn());
    }
}
*/