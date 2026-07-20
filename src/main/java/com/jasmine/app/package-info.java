/**
 * Application lifecycle management for JASMINE.
 *
 * <p>This package contains the main entry point and JavaFX {@link javafx.application.Application}
 * subclass that bootstraps the entire JASMINE runtime. It is responsible for:
 *
 * <ul>
 *   <li>Initializing the JavaFX toolkit and primary {@link javafx.stage.Stage}</li>
 *   <li>Coordinating startup sequencing — configuration loading, database initialization,
 *       service wiring, and UI construction — in the correct order</li>
 *   <li>Managing graceful shutdown, ensuring background threads, database connections,
 *       and scheduled tasks are terminated cleanly</li>
 *   <li>Providing the static {@code main} launcher method that delegates to
 *       {@link javafx.application.Application#launch}</li>
 * </ul>
 *
 * <p>The application class acts as the composition root: it constructs top-level services
 * and controllers and wires them together before handing control to the JavaFX event loop.
 * No business logic resides here; this package is purely infrastructural.
 *
 * @since 1.0
 */
package com.jasmine.app;
