package com.jasmine.ui.components;

import com.jasmine.ui.theme.AnimationManager;
import com.jasmine.ui.theme.ThemeManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

import java.util.Random;

/**
 * Animated Particle Background Canvas.
 * Renders volumetric fog and slow drifting particles/stars for the Deep Space theme.
 */
public class ParticleCanvas extends Pane {

    private final Canvas canvas;
    private final Particle[] particles;
    private final Random random = new Random();

    private double width;
    private double height;

    private static final int PARTICLE_COUNT = 80;

    public ParticleCanvas() {
        canvas = new Canvas();
        getChildren().add(canvas);
        
        // Ensure Canvas resizes with the Pane
        canvas.widthProperty().bind(widthProperty());
        canvas.heightProperty().bind(heightProperty());

        particles = new Particle[PARTICLE_COUNT];

        // Register to the global animation loop
        AnimationManager.getInstance().addFrameListener(this::render);
        
        // Listen to resize to respawn particles if needed
        layoutBoundsProperty().addListener((obs, oldVal, newVal) -> {
            width = newVal.getWidth();
            height = newVal.getHeight();
            if (particles[0] == null && width > 0 && height > 0) {
                initParticles();
            }
        });
    }

    private void initParticles() {
        for (int i = 0; i < PARTICLE_COUNT; i++) {
            particles[i] = new Particle(
                    random.nextDouble() * width,
                    random.nextDouble() * height,
                    (random.nextDouble() * 0.2) + 0.05, // very slow speed
                    random.nextDouble() * 2, // size
                    random.nextDouble() * Math.PI * 2
            );
        }
    }

    private void render(long now) {
        if (width <= 0 || height <= 0 || particles[0] == null) return;

        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear with slight transparency to allow the CSS background gradient to show through,
        // or just clear fully and draw our own deep space gradient.
        gc.clearRect(0, 0, width, height);

        // Draw soft volumetric fog/nebula using large faint radial gradients
        drawNebula(gc);

        // Draw and update particles
        gc.setFill(ThemeManager.CRYSTAL_WHITE.deriveColor(0, 1, 1, 0.4));
        for (Particle p : particles) {
            p.update(width, height);
            gc.fillOval(p.x, p.y, p.size, p.size);
        }
    }

    private void drawNebula(GraphicsContext gc) {
        // Soft Aurora purple fog top-left
        RadialGradient nebula1 = new RadialGradient(0, 0, width * 0.2, height * 0.2, Math.max(width, height) * 0.6, false, CycleMethod.NO_CYCLE,
                new Stop(0, ThemeManager.AURORA_PURPLE.deriveColor(0, 1, 1, 0.05)),
                new Stop(1, Color.TRANSPARENT));
        gc.setFill(nebula1);
        gc.fillRect(0, 0, width, height);

        // Soft Electric cyan fog bottom-right
        RadialGradient nebula2 = new RadialGradient(0, 0, width * 0.8, height * 0.8, Math.max(width, height) * 0.5, false, CycleMethod.NO_CYCLE,
                new Stop(0, ThemeManager.ELECTRIC_CYAN.deriveColor(0, 1, 1, 0.03)),
                new Stop(1, Color.TRANSPARENT));
        gc.setFill(nebula2);
        gc.fillRect(0, 0, width, height);
    }

    // ── Internal Particle Class ─────────────────────────────────────────────

    private static class Particle {
        double x, y;
        double speed;
        double size;
        double angle;

        Particle(double x, double y, double speed, double size, double angle) {
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.size = size;
            this.angle = angle;
        }

        void update(double width, double height) {
            x += Math.cos(angle) * speed;
            y += Math.sin(angle) * speed;

            // Slowly drift, wrap around screen
            if (x < 0) x = width;
            if (x > width) x = 0;
            if (y < 0) y = height;
            if (y > height) y = 0;
        }
    }
}
