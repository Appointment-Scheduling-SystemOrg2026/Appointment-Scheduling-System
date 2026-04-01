package com.scheduling.observer;

import java.util.function.BooleanSupplier;

import com.scheduling.domain.entity.User;

/**
 * Mock notification service for testing (US3.1).
 *
 * <p>Records sent messages in test mode without actually sending.</p>
 *
 * @author Tasneem
 * @version 1.0
 */
public class MockNotificationService implements Observer {

    private int sentCount = 0;
    private StringBuilder log = new StringBuilder();
    private String lastMessage = null;

    @Override
    public void notify(User user, String message) {
        sentCount++;
        lastMessage = message;

        log.append(String.format("[%d] To: %s - %s%n",
                sentCount, user.getUsername(), message));

        System.out.println("🧪 [MockService] Message recorded (test mode)");
    }

    public int getSentCount() {
        return sentCount;
    }

    public String getLog() {
        return log.toString();
    }

    public void reset() {
        sentCount = 0;
        log = new StringBuilder();
        lastMessage = null;
    }

    public boolean wasCalled() {
        return sentCount > 0;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void clear() {
        reset();
    }

    public int getCallCount() {
        return sentCount;
    }
}




/*public class MockNotificationService implements Observer {

    private int sentCount = 0;
    private StringBuilder log = new StringBuilder();

    @Override
    public void notify(User user, String message) {
        sentCount++;
        log.append(String.format("[%d] To: %s - %s%n", sentCount, user.getUsername(), message));
        System.out.println("🧪 [MockService] Message recorded (test mode)");
    }

    /**
     * Returns the number of messages sent.
     *
     * @return sent message count
     */
   /* public int getSentCount() {
        return sentCount;
    }

    /**
     * Returns the log of all sent messages.
     *
     * @return message log
     */
  /*  public String getLog() {
        return log.toString();
    }

    /**
     * Resets the mock service.
     */
   /* public void reset() {
        sentCount = 0;
        log = new StringBuilder();
    }

	public BooleanSupplier wasCalled() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getLastMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public void clear() {
		// TODO Auto-generated method stub
		
	}

	

	public int getCallCount() {
	    return getCallCount();
	}

	

	
}*/