package com.jasmine.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Static utility class for formatting raw metrics into human-readable strings.
 *
 * <p>This class bridges the gap between the domain model (which holds raw bytes
 * and milliseconds) and the UI layer (which needs strings like "512 MB").
 *
 * <p><strong>Design Decision:</strong> These methods are stateless and static.
 * They are placed in a central utility class rather than on the domain records
 * to keep the domain pure (unaware of display formats) and to ensure consistent
 * formatting across the entire application.
 *
 * @since 2.0
 */
public final class FormatUtil {

    private static final long KILOBYTE = 1024L;
    private static final long MEGABYTE = KILOBYTE * 1024L;
    private static final long GIGABYTE = MEGABYTE * 1024L;
    private static final long TERABYTE = GIGABYTE * 1024L;

    private static final ThreadLocal<DecimalFormat> PERCENT_FORMAT = 
            ThreadLocal.withInitial(() -> new DecimalFormat("0.0%"));
            
    private static final ThreadLocal<DecimalFormat> MEMORY_FORMAT = 
            ThreadLocal.withInitial(() -> new DecimalFormat("#,##0"));

    private static final ThreadLocal<NumberFormat> COUNT_FORMAT = 
            ThreadLocal.withInitial(() -> NumberFormat.getNumberInstance(Locale.US));

    /** Prevent instantiation. */
    private FormatUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Formats bytes into a human-readable string with dynamic units (B, KB, MB, GB, TB).
     *
     * @param bytes the size in bytes
     * @return formatted string (e.g., "512 MB")
     */
    public static String formatBytes(long bytes) {
        if (bytes < 0) return "N/A";
        if (bytes < KILOBYTE) return bytes + " B";
        if (bytes < MEGABYTE) return (bytes / KILOBYTE) + " KB";
        
        // For MB and above, we want at most one decimal place, or none if it's exact
        if (bytes < GIGABYTE) {
            double mb = (double) bytes / MEGABYTE;
            return MEMORY_FORMAT.get().format(mb) + " MB";
        }
        if (bytes < TERABYTE) {
            double gb = (double) bytes / GIGABYTE;
            return new DecimalFormat("#,##0.0").format(gb) + " GB";
        }
        
        double tb = (double) bytes / TERABYTE;
        return new DecimalFormat("#,##0.0").format(tb) + " TB";
    }

    /**
     * Formats a duration in milliseconds into a string (HH:MM:SS).
     *
     * @param millis duration in milliseconds
     * @return formatted string, e.g., "01:23:45"
     */
    public static String formatDuration(long millis) {
        if (millis < 0) return "00:00:00";
        
        long seconds = (millis / 1000) % 60;
        long minutes = (millis / (1000 * 60)) % 60;
        long hours = (millis / (1000 * 60 * 60));
        
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Formats a percentage value (0.0 to 100.0) into a string.
     *
     * @param percent the percentage value
     * @return formatted string (e.g., "42.7%")
     */
    public static String formatPercent(double percent) {
        if (percent < 0.0 || Double.isNaN(percent)) return "N/A";
        // Divide by 100 because DecimalFormat("0.0%") expects 0.0 to 1.0 range
        return PERCENT_FORMAT.get().format(percent / 100.0);
    }

    /**
     * Formats a large integer or long count with comma separators.
     *
     * @param count the number
     * @return formatted string (e.g., "1,234,567")
     */
    public static String formatCount(long count) {
        if (count < 0) return "N/A";
        return COUNT_FORMAT.get().format(count);
    }
}
