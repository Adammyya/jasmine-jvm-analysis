package com.jasmine.scheduler;

import com.jasmine.model.MonitoringSnapshot;
import com.jasmine.service.MonitoringService;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * Scheduled background task runner using JavaFX Timeline.
 *
 * <p>This scheduler takes the {@link MonitoringService} and a callback. Every tick,
 * it tells the service to collect a snapshot, and then passes that snapshot to the
 * callback (which is typically a method on a UI Controller).
 *
 * <p><strong>Why Timeline?</strong>
 * Since JMX reads are locally incredibly fast, there is no need for a background
 * executor thread. A JavaFX {@link Timeline} naturally executes its KeyFrame actions
 * directly on the FX Application Thread, meaning the callback can update UI
 * elements directly without needing {@code Platform.runLater()}.
 *
 * @since 2.0
 */
public class MonitoringScheduler {

    private static final Logger logger = LoggerFactory.getLogger(MonitoringScheduler.class);
    
    private final MonitoringService monitoringService;
    private final long intervalMs;
    
    private Timeline timeline;

    /**
     * Creates a new scheduler.
     *
     * @param monitoringService the service to pull data from
     * @param intervalMs        the tick interval in milliseconds
     */
    public MonitoringScheduler(MonitoringService monitoringService, long intervalMs) {
        this.monitoringService = monitoringService;
        this.intervalMs = intervalMs;
    }

    /**
     * Starts the scheduled task.
     *
     * @param onTick callback to receive the newly collected snapshot
     */
    public void start(Consumer<MonitoringSnapshot> onTick) {
        if (isRunning()) {
            logger.warn("Scheduler is already running.");
            return;
        }

        logger.info("Starting monitoring scheduler (interval: {} ms)", intervalMs);
        
        timeline = new Timeline(new KeyFrame(
                Duration.millis(intervalMs),
                event -> {
                    MonitoringSnapshot snapshot = monitoringService.collectSnapshot();
                    onTick.accept(snapshot);
                }
        ));
        
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Stops the scheduled task.
     */
    public void stop() {
        if (timeline != null) {
            logger.info("Stopping monitoring scheduler.");
            timeline.stop();
            timeline = null;
        }
    }

    /**
     * Checks if the scheduler is actively ticking.
     *
     * @return true if running
     */
    public boolean isRunning() {
        return timeline != null && timeline.getStatus() == Animation.Status.RUNNING;
    }
}
