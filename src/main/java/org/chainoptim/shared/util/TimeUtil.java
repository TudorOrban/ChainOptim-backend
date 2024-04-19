package org.chainoptim.shared.util;

public class TimeUtil {

    public static String formatDuration(Float durationDays) {
        if (Math.abs(durationDays) < 1) {
            return String.format("%.0f hours", durationDays * 24);
        } else if (Math.abs(durationDays) < 7) {
            return String.format("%.0f days", durationDays);
        } else if (Math.abs(durationDays) < 28) {
            return String.format("%.0f weeks", durationDays / 7);
        } else if (Math.abs(durationDays) < 365) {
            return String.format("%.0f months", durationDays / 28);
        } else {
            return String.format("%.0f years", durationDays / 365);
        }
    }
}
