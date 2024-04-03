package org.chainoptim.exception;

public class PlanLimitReachedException extends RuntimeException {

    public PlanLimitReachedException(String message) {
        super(message);
    }
}
