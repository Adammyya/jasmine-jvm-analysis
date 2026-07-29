package com.jasmine.ui.theme;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Centralized Animation Manager to ensure 60 FPS performance.
 * 
 * <p>By managing a single global {@link AnimationTimer}, we avoid the overhead of
 * starting and stopping dozens of independent timers for particle effects,
 * floating panels, and orbital rotations.
 */
public final class AnimationManager {

    private static AnimationManager instance;
    private final AnimationTimer globalTimer;
    
    // Thread-safe list for fast iteration during the JavaFX pulse
    private final CopyOnWriteArrayList<Consumer<Long>> frameListeners;

    private AnimationManager() {
        frameListeners = new CopyOnWriteArrayList<>();
        
        globalTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Consumer<Long> listener : frameListeners) {
                    listener.accept(now);
                }
            }
        };
    }

    public static synchronized AnimationManager getInstance() {
        if (instance == null) {
            instance = new AnimationManager();
        }
        return instance;
    }

    /**
     * Start the global animation loop. Should be called on application startup.
     */
    public void start() {
        globalTimer.start();
    }

    /**
     * Stop the global animation loop. Should be called on application shutdown.
     */
    public void stop() {
        globalTimer.stop();
        frameListeners.clear();
    }

    /**
     * Register a listener to be called on every frame (approx 60 times a second).
     * @param listener Consumer accepting the current nano time.
     */
    public void addFrameListener(Consumer<Long> listener) {
        if (!frameListeners.contains(listener)) {
            frameListeners.add(listener);
        }
    }

    public void removeFrameListener(Consumer<Long> listener) {
        frameListeners.remove(listener);
    }

    // ── Pre-configured JDS Transitions ──────────────────────────────────────

    /**
     * Applies a slow, subtle hovering scale micro-interaction to a node.
     * Fits the JDS constraint: "Never bounce. Never shake."
     */
    public static void applyHoverScale(Node node, double scaleFactor) {
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(300), node);
        scaleIn.setToX(scaleFactor);
        scaleIn.setToY(scaleFactor);
        scaleIn.setInterpolator(Interpolator.EASE_OUT);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(300), node);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);
        scaleOut.setInterpolator(Interpolator.EASE_IN);

        node.setOnMouseEntered(e -> {
            scaleOut.stop();
            scaleIn.playFromStart();
            node.toFront();
        });

        node.setOnMouseExited(e -> {
            scaleIn.stop();
            scaleOut.playFromStart();
        });
    }
}
