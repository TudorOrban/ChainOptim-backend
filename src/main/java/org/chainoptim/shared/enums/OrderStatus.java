package org.chainoptim.shared.enums;

public enum OrderStatus {
    INITIATED,
    NEGOTIATED,
    PLACED,
    DELIVERED,
    CANCELED;

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }

    public static OrderStatus fromString(String statusStr) {
        for (OrderStatus status : values()) {
            if (status.name().equalsIgnoreCase(statusStr)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No constant with text " + statusStr + " found");
    }
}
