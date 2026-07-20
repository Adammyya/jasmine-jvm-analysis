package com.jasmine.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entry point for the JASMINE application.
 *
 * <p><strong>Why a separate launcher?</strong> JavaFX with the Java Platform Module System
 * (JPMS) requires that the class containing {@code main()} does <em>not</em> extend
 * {@link javafx.application.Application}. If the main class extends {@code Application},
 * the JVM attempts to load the {@code javafx.graphics} module before the module path
 * is fully initialized, resulting in a {@code RuntimeException}. This is a well-documented
 * JavaFX limitation (see <a href="https://openjfx.io/openjfx-docs/">OpenJFX Docs</a>).
 *
 * <p>This class acts as a thin delegation layer:
 * <ol>
 *     <li>Configures a global uncaught exception handler for defensive error reporting</li>
 *     <li>Logs essential environment information (Java version, OS, memory) for diagnostics</li>
 *     <li>Delegates to {@link JasmineApplication#launch(Class, String...)} to start the JavaFX lifecycle</li>
 * </ol>
 *
 * <p>No business logic, no UI construction, no service initialization should occur here.
 * Those responsibilities belong to {@link JasmineApplication#init()} and
 * {@link JasmineApplication#start(javafx.stage.Stage)}.
 *
 * @since 1.0
 */
public final class JasmineLauncher {

    private static final Logger logger = LoggerFactory.getLogger(JasmineLauncher.class);

    /** Prevent instantiation. */
    private JasmineLauncher() {
        throw new UnsupportedOperationException("Launcher class");
    }

    /**
     * Application entry point.
     *
     * @param args command-line arguments (currently unused; reserved for future
     *             configuration overrides such as {@code --theme=light})
     */
    public static void main(String[] args) {
        configureGlobalExceptionHandler();
        logEnvironmentInfo();

        logger.info("Launching JASMINE application...");
        JasmineApplication.launch(JasmineApplication.class, args);
    }

    /**
     * Installs a global uncaught exception handler on the default thread.
     *
     * <p>This ensures that any unhandled exception on any thread is logged
     * with full stack trace information rather than silently crashing.
     * JavaFX has its own exception handling on the FX Application Thread,
     * but background threads (schedulers, monitors) need this safety net.
     */
    private static void configureGlobalExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            logger.error("Uncaught exception on thread [{}]: {}",
                    thread.getName(), throwable.getMessage(), throwable);
        });
    }

    /**
     * Logs key environment details for diagnostic and reproducibility purposes.
     *
     * <p>When a user reports a bug, this information in the log file allows
     * developers to immediately understand the runtime context without
     * asking follow-up questions.
     */
    private static void logEnvironmentInfo() {
        Runtime runtime = Runtime.getRuntime();

        logger.info("=".repeat(60));
        logger.info("JASMINE — JVM Runtime Analysis Platform");
        logger.info("=".repeat(60));
        logger.info("Java Version    : {} ({})",
                System.getProperty("java.version"),
                System.getProperty("java.vendor"));
        logger.info("Java Home       : {}", System.getProperty("java.home"));
        logger.info("OS              : {} {} ({})",
                System.getProperty("os.name"),
                System.getProperty("os.version"),
                System.getProperty("os.arch"));
        logger.info("Available CPUs  : {}", runtime.availableProcessors());
        logger.info("Max Memory      : {} MB", runtime.maxMemory() / (1024 * 1024));
        logger.info("Working Dir     : {}", System.getProperty("user.dir"));
        logger.info("=".repeat(60));
    }
}
