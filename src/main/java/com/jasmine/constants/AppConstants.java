package com.jasmine.constants;

/**
 * Centralized application-wide constants for JASMINE.
 *
 * <p>This class provides a single source of truth for all configuration defaults,
 * dimension constraints, file paths, and identity strings used throughout the
 * application. By centralizing these values, we avoid magic numbers and strings
 * scattered across the codebase, making maintenance and configuration changes
 * straightforward.
 *
 * <p><strong>Design Decision:</strong> This is a utility class with a private
 * constructor — it cannot be instantiated or subclassed. All fields are
 * {@code public static final}, making them compile-time constants where possible.
 *
 * @since 1.0
 */
public final class AppConstants {

    /** Prevent instantiation. */
    private AppConstants() {
        throw new UnsupportedOperationException("Constants class cannot be instantiated");
    }

    // ── Application Identity ────────────────────────────────────────────────

    /** Short application name used in titles and logs. */
    public static final String APP_NAME = "JASMINE";

    /** Full expanded application name. */
    public static final String APP_FULL_NAME =
            "Java Adaptive Smart Memory Intelligence & Runtime Performance Evaluation Platform";

    /** Current application version following semantic versioning. */
    public static final String APP_VERSION = "1.0.0";

    /** Subtitle displayed beneath the application name in the UI. */
    public static final String APP_SUBTITLE = "JVM Runtime Analysis Platform";

    // ── Window Dimensions ───────────────────────────────────────────────────

    /**
     * Minimum window width in pixels.
    /**
     * Minimum window width in pixels.
     * Reduced to accommodate lower resolution screens or high display scaling.
     */
    public static final double WINDOW_MIN_WIDTH = 960;

    /** Minimum window height in pixels. */
    public static final double WINDOW_MIN_HEIGHT = 600;

    /**
     * Default window width in pixels.
     * Targets a comfortable layout on 1920×1080 displays without
     * occupying the full screen.
     */
    public static final double WINDOW_DEFAULT_WIDTH = 1440;

    /** Default window height in pixels. */
    public static final double WINDOW_DEFAULT_HEIGHT = 900;

    // ── Sidebar ─────────────────────────────────────────────────────────────

    /** Fixed sidebar width in pixels, matching IntelliJ IDEA's tool window width. */
    public static final double SIDEBAR_WIDTH = 220;

    // ── Refresh Intervals ───────────────────────────────────────────────────

    /** Default polling interval for real-time monitoring, in milliseconds. */
    public static final long DEFAULT_REFRESH_INTERVAL_MS = 1000;

    /** Slower polling interval for less time-sensitive metrics, in milliseconds. */
    public static final long SLOW_REFRESH_INTERVAL_MS = 5000;

    // ── Database ────────────────────────────────────────────────────────────

    /** SQLite database filename. */
    public static final String DATABASE_FILENAME = "jasmine.db";

    /** Directory containing the SQLite database file, relative to project root. */
    public static final String DATABASE_DIRECTORY = "database";

    // ── Theme Paths ─────────────────────────────────────────────────────────

    /** Classpath resource path to the dark theme CSS stylesheet. */
    public static final String DARK_THEME_PATH = "/com/jasmine/ui/themes/jds-theme.css";
}
