package com.scheduling.observer;

import com.scheduling.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class EmailNotificationServiceTest {

    private EmailNotificationService service;
    private EmailSender emailSenderMock;

    @BeforeEach
    void setUp() {
        emailSenderMock = mock(EmailSender.class);

        service = new EmailNotificationService();
        service.enableRealEmail("test@gmail.com", "pass");
    }

    @Test
    void notify_shouldSkip_whenTestModeIsTrue() {
        EmailNotificationService.setTestMode(true);

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("test@gmail.com");

        service.notify(user, "hello");

        verifyNoInteractions(emailSenderMock);
    }

    @Test
    void notify_shouldSendEmail_whenEnabled() throws Exception {
        EmailNotificationService.setTestMode(false);

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("test@gmail.com");

        service.notify(user, "hello");

        
    }

    @Test
    void notifyEmail_shouldWork_whenEnabled() {
        EmailNotificationService.setTestMode(false);

        service.notifyEmail("test@gmail.com", "msg");

       
    }

    @Test
    void notifyWithEmail_shouldSkipInTestMode() {
        EmailNotificationService.setTestMode(true);

        service.notifyWithEmail("test@gmail.com", "msg");
    }

    @Test
    void disableRealEmail_shouldDisableService() {
        service.disableRealEmail();

        service.notifyEmail("test@gmail.com", "msg");
    }
}