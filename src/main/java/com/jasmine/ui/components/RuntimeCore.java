package com.jasmine.ui.components;

import com.jasmine.ui.theme.AnimationManager;
import com.jasmine.ui.theme.ThemeManager;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 * The Runtime Core: Central living visualization of JVM health.
 * 
 * Forms a refractive crystal star geometry. It reacts to JVM telemetry:
 * - CPU load dictates the intensity of the glow.
 * - Memory pressure expands the outer crystal shell.
 */
public class RuntimeCore extends StackPane {

    private final Circle innerCore;
    private final Polygon crystal1;
    private final Polygon crystal2;
    private final Bloom bloomEffect;
    
    private Timeline idleAnimation;

    public RuntimeCore() {
        super();
        
        // Base dimensions
        setPrefSize(200, 200);
        setMinSize(200, 200);
        setMaxSize(200, 200);

        // Inner energy core
        innerCore = new Circle(40, ThemeManager.ELECTRIC_CYAN);
        
        // Outer crystal shell (Square 1)
        crystal1 = createSquare(80);
        crystal1.setStroke(ThemeManager.CRYSTAL_WHITE.deriveColor(0, 1, 1, 0.4));
        crystal1.setStrokeWidth(2);
        crystal1.setFill(ThemeManager.ELECTRIC_CYAN.deriveColor(0, 1, 1, 0.1));

        // Outer crystal shell (Square 2 - rotated 45 deg)
        crystal2 = createSquare(80);
        crystal2.setRotate(45);
        crystal2.setStroke(ThemeManager.AURORA_PURPLE.deriveColor(0, 1, 1, 0.4));
        crystal2.setStrokeWidth(2);
        crystal2.setFill(ThemeManager.AURORA_PURPLE.deriveColor(0, 1, 1, 0.1));

        // Glow effects (GPU friendly)
        bloomEffect = new Bloom();
        bloomEffect.setThreshold(0.3);
        
        DropShadow outerGlow = new DropShadow(30, ThemeManager.ELECTRIC_CYAN);
        outerGlow.setInput(bloomEffect);
        
        setEffect(outerGlow);
        getChildren().addAll(crystal1, crystal2, innerCore);

        // Start healthy slow idle pulse
        startIdleAnimation();
    }

    private Polygon createSquare(double size) {
        Polygon p = new Polygon();
        p.getPoints().addAll(
                -size, -size,
                 size, -size,
                 size,  size,
                -size,  size
        );
        return p;
    }

    private void startIdleAnimation() {
        idleAnimation = new Timeline(
            new KeyFrame(Duration.ZERO, 
                new KeyValue(innerCore.radiusProperty(), 38, Interpolator.EASE_BOTH),
                new KeyValue(crystal1.rotateProperty(), 0, Interpolator.LINEAR),
                new KeyValue(crystal2.rotateProperty(), 45, Interpolator.LINEAR)
            ),
            new KeyFrame(Duration.seconds(6), 
                new KeyValue(innerCore.radiusProperty(), 42, Interpolator.EASE_BOTH),
                new KeyValue(crystal1.rotateProperty(), 45, Interpolator.LINEAR),
                new KeyValue(crystal2.rotateProperty(), 90, Interpolator.LINEAR)
            )
        );
        idleAnimation.setCycleCount(Timeline.INDEFINITE);
        idleAnimation.setAutoReverse(true);
        idleAnimation.play();
    }

    /**
     * Feed telemetry into the Core to alter its geometry and glow.
     * 
     * @param cpuLoad 0.0 to 100.0 (determines bloom intensity and color)
     * @param memoryLoad 0.0 to 100.0 (determines crystal expansion)
     */
    public void updateTelemetry(double cpuLoad, double memoryLoad) {
        // CPU alters glow: above 80% shifts to amber/red
        double normalizedCpu = Math.min(cpuLoad, 100) / 100.0;
        bloomEffect.setThreshold(0.8 - (normalizedCpu * 0.7)); // Higher CPU = lower threshold = more bloom

        if (cpuLoad > 85) {
            innerCore.setFill(ThemeManager.DANGER_RED);
            ((DropShadow) getEffect()).setColor(ThemeManager.DANGER_RED);
        } else if (cpuLoad > 60) {
            innerCore.setFill(ThemeManager.SOFT_AMBER);
            ((DropShadow) getEffect()).setColor(ThemeManager.SOFT_AMBER);
        } else {
            innerCore.setFill(ThemeManager.ELECTRIC_CYAN);
            ((DropShadow) getEffect()).setColor(ThemeManager.ELECTRIC_CYAN);
        }

        // Memory expands the crystal shell
        double targetScale = 1.0 + (Math.min(memoryLoad, 100) / 100.0) * 0.5; // Up to 50% larger
        
        Timeline reaction = new Timeline(
            new KeyFrame(Duration.seconds(1), 
                new KeyValue(crystal1.scaleXProperty(), targetScale, Interpolator.EASE_OUT),
                new KeyValue(crystal1.scaleYProperty(), targetScale, Interpolator.EASE_OUT),
                new KeyValue(crystal2.scaleXProperty(), targetScale, Interpolator.EASE_OUT),
                new KeyValue(crystal2.scaleYProperty(), targetScale, Interpolator.EASE_OUT)
            )
        );
        reaction.play();
    }

    public void triggerGcRipple() {
        Circle ripple = new Circle(40, Color.TRANSPARENT);
        ripple.setStroke(ThemeManager.AURORA_PURPLE);
        ripple.setStrokeWidth(4);
        getChildren().add(1, ripple);

        Timeline rippleAnim = new Timeline(
            new KeyFrame(Duration.ZERO, 
                new KeyValue(ripple.radiusProperty(), 40, Interpolator.EASE_OUT),
                new KeyValue(ripple.opacityProperty(), 1.0, Interpolator.EASE_OUT)
            ),
            new KeyFrame(Duration.seconds(1.5), 
                new KeyValue(ripple.radiusProperty(), 150, Interpolator.EASE_OUT),
                new KeyValue(ripple.opacityProperty(), 0.0, Interpolator.EASE_OUT)
            )
        );
        rippleAnim.setOnFinished(e -> getChildren().remove(ripple));
        rippleAnim.play();
    }
}
