package com.jasmine.ui.components;

import com.jasmine.ui.theme.ThemeManager;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 * Metric node that orbits the Runtime Core.
 * Contains a thin tether line, an orbital pivot, and a small GlassPanel payload.
 */
public class OrbitalMetricNode extends Group {

    private final GlassPanel payload;
    private final Label titleLabel;
    private final Label valueLabel;

    private final RotateTransition orbitAnimation;
    private final RotateTransition counterRotation;

    /**
     * @param title The metric name (e.g. "CPU LOAD")
     * @param orbitRadius The distance from the center core
     * @param orbitDurationSec The time in seconds for a full 360 orbit
     * @param initialAngle The starting angle (0-360)
     */
    public OrbitalMetricNode(String title, double orbitRadius, double orbitDurationSec, double initialAngle) {
        
        // Pivot group to handle the rotation around the center
        Group pivotGroup = new Group();
        
        // The tether line drawn from the center to the payload
        Line tether = new Line(0, 0, orbitRadius, 0);
        tether.setStroke(ThemeManager.GLASS_BORDER);
        tether.getStrokeDashArray().addAll(4d, 4d);
        
        // The payload container (counter-rotated so text stays upright)
        payload = new GlassPanel();
        payload.setPadding(new Insets(8, 12, 8, 12));
        payload.setTranslateX(orbitRadius); // Push it out to the radius
        payload.setTranslateY(-20); // Center the box roughly on the line
        
        titleLabel = new Label(title.toUpperCase());
        titleLabel.setStyle("-fx-font-family: 'Space Grotesk', sans-serif; -fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #A0AABF;");
        
        valueLabel = new Label("--");
        valueLabel.setStyle("-fx-font-family: 'JetBrains Mono', monospace; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #00E5FF;");
        
        payload.getChildren().addAll(titleLabel, valueLabel);
        
        // Small orbital tracking dot
        Circle tracker = new Circle(orbitRadius, 0, 3, ThemeManager.ELECTRIC_CYAN);
        
        pivotGroup.getChildren().addAll(tether, tracker, payload);
        pivotGroup.setRotate(initialAngle);
        
        getChildren().add(pivotGroup);

        // Animate the entire pivot group
        orbitAnimation = new RotateTransition(Duration.seconds(orbitDurationSec), pivotGroup);
        orbitAnimation.setByAngle(360);
        orbitAnimation.setInterpolator(Interpolator.LINEAR);
        orbitAnimation.setCycleCount(RotateTransition.INDEFINITE);
        
        // Counter-rotate the payload so text remains upright
        counterRotation = new RotateTransition(Duration.seconds(orbitDurationSec), payload);
        counterRotation.setByAngle(-360);
        counterRotation.setInterpolator(Interpolator.LINEAR);
        counterRotation.setCycleCount(RotateTransition.INDEFINITE);
        
        orbitAnimation.play();
        counterRotation.play();
    }

    public void updateValue(String text, boolean highlight) {
        valueLabel.setText(text);
        if (highlight) {
            valueLabel.setStyle("-fx-font-family: 'JetBrains Mono', monospace; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #FF1744;");
        } else {
            valueLabel.setStyle("-fx-font-family: 'JetBrains Mono', monospace; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #00E5FF;");
        }
    }
}
