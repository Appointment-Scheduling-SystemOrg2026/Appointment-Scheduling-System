package com.scheduling.observer;

import com.scheduling.domain.entity.User;

/**
 * Basic notification service implementation.
 *
 * @author Tasneem
 * @version 1.0
 */
public class NotificationService implements Observer {

    @Override
    public void notify(User user, String message) {
        // Silent - no console output
    }
}








/*package com.scheduling.observer;

import com.scheduling.domain.entity.User;

/**
 * Basic notification service implementation.
 *
 * <p>Logs notifications to console for testing purposes (US3.1).</p>
 *
 * @author Tasneem
 * @version 1.0
 */
/*public class NotificationService implements Observer {

    @Override
    public void notify(User user, String message) {
        System.out.println("📧 [NotificationService] To: " + user.getUsername());
        System.out.println("   Message: " + message);
    }
}*/