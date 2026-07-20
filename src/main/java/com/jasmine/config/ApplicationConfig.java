package com.jasmine.config;

import com.jasmine.constants.AppConstants;

/**
 * Singleton configuration holder for JASMINE runtime settings.
 *
 * <p>This class serves as the centralized configuration registry for the application,
 * holding mutable runtime settings that may differ from the compile-time defaults
 * in {@link AppConstants}. While {@code AppConstants} provides immutable defaults,
 * {@code ApplicationConfig} allows those defaults to be overridden at runtime
 * (e.g., from user preferences stored in SQLite, or command-line arguments).
 *
 * <p><strong>Singleton Pattern:</strong> Uses the Bill Pugh Singleton (initialization-on-demand
 * holder idiom). The inner static class {@code Holder} is not loaded until
 * {@link #getInstance()} is first called, ensuring:
 * <ul>
 *     <li>Lazy initialization without synchronization overhead</li>
 *     <li>Thread safety guaranteed by the JVM class loading mechanism</li>
 *     <li>No vulnerability to reflection attacks (unlike double-checked locking)</li>
 * </ul>
 *
 * <p><strong>Future Evolution:</strong> In later phases, this class will load
 * persisted settings from SQLite on startup and save changes back. For Phase 1,
 * it simply holds the defaults.
 *
 * @since 1.0
 */
public final class ApplicationConfig {

    /** Path to the active CSS theme resource. */
    private String themePath;

    /** Current window width preference. */
    private double windowWidth;

    /** Current window height preference. */
    private double windowHeight;

    /** Polling interval for real-time monitors, in milliseconds. */
    private long refreshIntervalMs;

    /**
     * Private constructor initializing all fields to their defaults from
     * {@link AppConstants}.
     */
    private ApplicationConfig() {
        this.themePath = AppConstants.DARK_THEME_PATH;
        this.windowWidth = AppConstants.WINDOW_DEFAULT_WIDTH;
        this.windowHeight = AppConstants.WINDOW_DEFAULT_HEIGHT;
        this.refreshIntervalMs = AppConstants.DEFAULT_REFRESH_INTERVAL_MS;
    }

    /**
     * Initialization-on-demand holder. The JVM guarantees that the class
     * is not loaded until {@link ApplicationConfig#getInstance()} is invoked,
     * and that class loading is thread-safe.
     */
    private static final class Holder {
        private static final ApplicationConfig INSTANCE = new ApplicationConfig();
    }

    /**
     * Returns the singleton configuration instance.
     *
     * @return the application configuration, never {@code null}
     */
    public static ApplicationConfig getInstance() {
        return Holder.INSTANCE;
    }

    // ── Theme ───────────────────────────────────────────────────────────────

    /**
     * Returns the classpath resource path to the active CSS theme.
     *
     * @return theme CSS resource path
     */
    public String getThemePath() {
        return themePath;
    }

    /**
     * Sets the active CSS theme resource path.
     *
     * @param themePath classpath resource path to the CSS file
     */
    public void setThemePath(String themePath) {
        this.themePath = themePath;
    }

    // ── Window Dimensions ───────────────────────────────────────────────────

    /**
     * Returns the preferred window width.
     *
     * @return window width in pixels
     */
    public double getWindowWidth() {
        return windowWidth;
    }

    /**
     * Sets the preferred window width.
     *
     * @param windowWidth window width in pixels
     */
    public void setWindowWidth(double windowWidth) {
        this.windowWidth = windowWidth;
    }

    /**
     * Returns the preferred window height.
     *
     * @return window height in pixels
     */
    public double getWindowHeight() {
        return windowHeight;
    }

    /**
     * Sets the preferred window height.
     *
     * @param windowHeight window height in pixels
     */
    public void setWindowHeight(double windowHeight) {
        this.windowHeight = windowHeight;
    }

    // ── Refresh Interval ────────────────────────────────────────────────────

    /**
     * Returns the polling interval for real-time monitors.
     *
     * @return refresh interval in milliseconds
     */
    public long getRefreshIntervalMs() {
        return refreshIntervalMs;
    }

    /**
     * Sets the polling interval for real-time monitors.
     *
     * @param refreshIntervalMs refresh interval in milliseconds, must be positive
     * @throws IllegalArgumentException if the interval is not positive
     */
    public void setRefreshIntervalMs(long refreshIntervalMs) {
        if (refreshIntervalMs <= 0) {
            throw new IllegalArgumentException(
                    "Refresh interval must be positive, got: " + refreshIntervalMs);
        }
        this.refreshIntervalMs = refreshIntervalMs;
    }
}
