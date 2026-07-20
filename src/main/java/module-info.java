/**
 * JASMINE — Java Adaptive Smart Memory Intelligence and Runtime Performance Evaluation Platform.
 *
 * <p>This module defines the core application, encompassing the JavaFX UI layer,
 * JMX-based runtime monitoring, benchmark execution, analytics, and reporting.
 *
 * <p>Architecture follows a layered design:
 * <ul>
 *     <li>{@code com.jasmine.app} — Application lifecycle and entry point</li>
 *     <li>{@code com.jasmine.controller} — UI controllers (MVC bridge)</li>
 *     <li>{@code com.jasmine.service} — Business logic layer</li>
 *     <li>{@code com.jasmine.repository} — Data access layer</li>
 *     <li>{@code com.jasmine.model} — Domain entities</li>
 *     <li>{@code com.jasmine.ui.*} — JavaFX view layer</li>
 * </ul>
 */
module com.jasmine {

    // ── JavaFX ──────────────────────────────────────────────────────────────
    requires javafx.controls;
    requires javafx.fxml;

    // ── Logging ─────────────────────────────────────────────────────────────
    requires org.slf4j;

    // ── Database ────────────────────────────────────────────────────────────
    requires org.xerial.sqlitejdbc;

    // ── JVM Management (JMX) ────────────────────────────────────────────────
    requires java.management;
    requires jdk.management;

    // ── SQL (for JDBC) ──────────────────────────────────────────────────────
    requires java.sql;

    // ── Opens for JavaFX reflection ─────────────────────────────────────────
    // JavaFX needs reflective access to Application subclasses and
    // FXML controller classes for dependency injection.
    opens com.jasmine.app to javafx.graphics;
    opens com.jasmine.ui.dashboard to javafx.fxml;

    // ── Exports ─────────────────────────────────────────────────────────────
    exports com.jasmine.app;
}
