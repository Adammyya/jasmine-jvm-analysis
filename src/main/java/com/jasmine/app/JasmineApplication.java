package com.jasmine.app;

import com.jasmine.config.ApplicationConfig;
import com.jasmine.constants.AppConstants;
import com.jasmine.ui.dashboard.DashboardController;
import com.jasmine.service.MonitoringService;
import com.jasmine.scheduler.MonitoringScheduler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main JavaFX application class for JASMINE.
 *
 * <p>This class manages the JavaFX application lifecycle through three phases:
 * <ol>
 *     <li>{@link #init()} — Pre-UI initialization (database connections, services).
 *         Runs on the JavaFX Launcher thread, <em>not</em> the FX Application Thread.
 *         Suitable for blocking I/O operations that should complete before the UI appears.</li>
 *     <li>{@link #start(Stage)} — UI construction and display. Runs on the
 *         FX Application Thread. Builds the scene graph, applies styling, and shows
 *         the primary stage.</li>
 *     <li>{@link #stop()} — Clean shutdown. Runs on the FX Application Thread.
 *         Releases resources, stops schedulers, closes database connections.</li>
 * </ol>
 *
 * <p><strong>Architectural Note:</strong> This class is intentionally thin. It delegates
 * UI construction to {@link DashboardController} and service initialization to
 * dedicated service classes (in future phases). The Application class should never
 * contain business logic or complex UI layout code — it is a lifecycle orchestrator.
 *
 * @since 1.0
 */
public class JasmineApplication extends Application {

    private static final Logger logger = LoggerFactory.getLogger(JasmineApplication.class);

    /** Application configuration (singleton). */
    private final ApplicationConfig config = ApplicationConfig.getInstance();

    /** Core monitoring orchestrator. */
    private MonitoringService monitoringService;

    /** Background scheduler for UI updates. */
    private MonitoringScheduler monitoringScheduler;

    /**
     * Creates a new JASMINE application instance.
     *
     * <p>This public no-arg constructor is required by JavaFX. The
     * {@link javafx.application.Application#launch(Class, String...)} method
     * reflectively instantiates this class.
     */
    public JasmineApplication() {
        // Required by JavaFX for reflective instantiation
    }

    /**
     * Pre-UI initialization phase.
     *
     * <p>This method runs on the JavaFX Launcher thread (not the FX Application Thread),
     * making it safe for blocking operations like database initialization.
     *
     * <p><strong>Phase 1:</strong> No services to initialize yet. This method
     * will be populated in Phase 2 when database and monitoring services are added.
     *
     * @throws Exception if initialization fails, preventing the UI from launching
     */
    @Override
    public void init() throws Exception {
        logger.info("Initializing JASMINE application...");

        // Phase 2+: Database initialization
        
        // Phase 2: Service layer bootstrap
        monitoringService = new MonitoringService();
        monitoringService.initialize();
        
        // Phase 2+: Load user preferences from SQLite

        logger.info("Initialization complete.");
    }

    /**
     * Constructs and displays the primary application window.
     *
     * <p>This method assembles the main scene graph:
     * <ul>
     *     <li>Creates the {@link DashboardController} which builds the entire UI</li>
     *     <li>Wraps the dashboard view in a {@link Scene} with configured dimensions</li>
     *     <li>Applies the dark theme CSS stylesheet</li>
     *     <li>Configures the primary {@link Stage} with title, minimum dimensions, and scene</li>
     *     <li>Shows the stage</li>
     * </ul>
     *
     * @param primaryStage the primary stage provided by the JavaFX runtime
     */
    @Override
    public void start(Stage primaryStage) {
        logger.info("Building application UI...");

        try {
            // Build the dashboard (the main UI shell)
            DashboardController dashboardController = new DashboardController();
            BorderPane rootLayout = dashboardController.buildView();

            // Create the scene
            Scene scene = new Scene(
                    rootLayout,
                    config.getWindowWidth(),
                    config.getWindowHeight()
            );

            // Apply the dark theme
            applyTheme(scene);

            // Configure the primary stage
            configurePrimaryStage(primaryStage, scene);

            // Show the window
            primaryStage.show();

            // Start background monitoring scheduler
            monitoringScheduler = new MonitoringScheduler(monitoringService, config.getRefreshIntervalMs());
            monitoringScheduler.start(dashboardController::update);

            logger.info("JASMINE application started successfully. Window: {}x{}",
                    (int) primaryStage.getWidth(), (int) primaryStage.getHeight());

        } catch (Exception e) {
            logger.error("Failed to start JASMINE application", e);
            throw e;
        }
    }

    /**
     * Clean shutdown phase.
     *
     * <p>Called when the user closes the window or the application is terminated.
     * Ensures all resources are properly released.
     *
     * <p><strong>Phase 1:</strong> No resources to release yet. This method
     * will close database connections, stop scheduler threads, and persist
     * user preferences in future phases.
     */
    @Override
    public void stop() {
        logger.info("Shutting down JASMINE application...");

        // Phase 2: Stop monitoring schedulers
        if (monitoringScheduler != null) {
            monitoringScheduler.stop();
        }

        // Phase 2+: Close database connections
        // Phase 2+: Persist window position/size to preferences

        logger.info("JASMINE application shut down cleanly.");
    }

    /**
     * Applies the configured CSS theme to the scene.
     *
     * <p>Theme CSS is loaded from the classpath as a resource. If the theme file
     * is not found, the application logs a warning and continues with default
     * JavaFX styling — it does not crash.
     *
     * @param scene the scene to apply the theme to
     */
    private void applyTheme(Scene scene) {
        String themeResource = config.getThemePath();
        var themeUrl = getClass().getResource(themeResource);

        if (themeUrl != null) {
            scene.getStylesheets().add(themeUrl.toExternalForm());
            logger.debug("Applied theme: {}", themeResource);
        } else {
            logger.warn("Theme CSS not found: {}. Using default JavaFX styling.", themeResource);
        }
    }

    /**
     * Configures the primary stage with title, dimensions, and constraints.
     *
     * @param stage the primary stage to configure
     * @param scene the scene to set on the stage
     */
    private void configurePrimaryStage(Stage stage, Scene scene) {
        stage.setTitle(AppConstants.APP_NAME + " — " + AppConstants.APP_SUBTITLE);
        stage.setScene(scene);

        // Enforce minimum dimensions to prevent layout breakage
        stage.setMinWidth(AppConstants.WINDOW_MIN_WIDTH);
        stage.setMinHeight(AppConstants.WINDOW_MIN_HEIGHT);
    }
}
