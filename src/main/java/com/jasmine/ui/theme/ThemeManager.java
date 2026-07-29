package com.jasmine.ui.theme;

import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;
import javafx.scene.text.Font;

/**
 * Centralized Theme Manager for the JASMINE Design System (JDS).
 *
 * <p>While CSS handles standard JavaFX controls, this manager provides
 * programmatic access to design tokens for custom rendering on {@link javafx.scene.canvas.Canvas}
 * and dynamic procedural animations that require explicit JavaFX Color objects.
 *
 * @since 2.0
 */
public final class ThemeManager {

    private ThemeManager() {}

    // ── Colors ──────────────────────────────────────────────────────────────

    // Background
    public static final Color SPACE_DARK = Color.web("#05080F");
    public static final Color SPACE_LIGHT = Color.web("#0B111E");

    // Accents
    public static final Color ELECTRIC_CYAN = Color.web("#00E5FF");
    public static final Color AURORA_PURPLE = Color.web("#B388FF");
    public static final Color CRYSTAL_WHITE = Color.web("#FFFFFF");
    
    // Status
    public static final Color SOFT_AMBER = Color.web("#FFB300");
    public static final Color HEALTHY_GREEN = Color.web("#00E676");
    public static final Color DANGER_RED = Color.web("#FF1744");

    // UI Elements
    public static final Color TEXT_PRIMARY = Color.web("#FFFFFF");
    public static final Color TEXT_SECONDARY = Color.web("#A0AABF");
    public static final Color GLASS_BORDER = Color.color(1, 1, 1, 0.15);

    // ── Gradients ───────────────────────────────────────────────────────────

    public static LinearGradient createSpaceGradient() {
        return new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, SPACE_DARK),
                new Stop(1, SPACE_LIGHT)
        );
    }

    public static LinearGradient createGlassGradient(double opacityAlpha) {
        return new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#1E2437").deriveColor(0, 1, 1, opacityAlpha)),
                new Stop(1, Color.web("#121626").deriveColor(0, 1, 1, opacityAlpha * 0.7))
        );
    }

    // ── Typography ──────────────────────────────────────────────────────────

    /**
     * Attempts to load the system font with a fallback sequence.
     * Note: In a production environment, we would load TTF resources directly here.
     * For now, we rely on standard system fonts styled to look futuristic.
     */
    public static Font getHeaderFont(double size) {
        return Font.font("Space Grotesk", size);
    }

    public static Font getBodyFont(double size) {
        return Font.font("Inter", size);
    }

    public static Font getMetricFont(double size) {
        return Font.font("JetBrains Mono", size);
    }
}
