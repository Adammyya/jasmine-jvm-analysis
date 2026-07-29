package com.jasmine.ui.components;

import com.jasmine.ui.theme.AnimationManager;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * Reusable Glass Panel component for the JASMINE Design System.
 * 
 * Features:
 * - Refractive borders
 * - Translucent backgrounds
 * - Soft bloom drop-shadows
 * - Built-in slow hover micro-interactions (Scale up to 1.02x)
 */
public class GlassPanel extends VBox {

    public GlassPanel() {
        super();
        init();
    }

    public GlassPanel(double spacing) {
        super(spacing);
        init();
    }

    public GlassPanel(Node... children) {
        super(children);
        init();
    }

    public GlassPanel(double spacing, Node... children) {
        super(spacing, children);
        init();
    }

    private void init() {
        // Link to the jds-theme.css classes
        getStyleClass().add("metric-card");
        
        // Apply the centralized JDS hover micro-interaction
        AnimationManager.applyHoverScale(this, 1.02);
    }
}
