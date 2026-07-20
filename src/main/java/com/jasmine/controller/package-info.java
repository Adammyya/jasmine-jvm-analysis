/**
 * JavaFX controllers for JASMINE, implementing the Model-View-Controller pattern.
 *
 * <p>Controllers in this package serve as the bridge between the FXML-defined UI layer
 * and the underlying service layer. Each controller is associated with one or more FXML
 * views and is responsible for:
 *
 * <ul>
 *   <li>Handling user interactions — button clicks, text input, table selection —
 *       and translating them into service-layer calls</li>
 *   <li>Binding observable properties from domain models and DTOs to UI controls
 *       so that data flows reactively from services to the view</li>
 *   <li>Managing UI state transitions such as loading spinners, error banners,
 *       and navigation between views</li>
 *   <li>Performing lightweight input sanitization before delegating to the
 *       {@link com.jasmine.validation} package for full constraint checking</li>
 * </ul>
 *
 * <p>Controllers never access the database or repository layer directly; all data
 * operations are mediated through services in {@link com.jasmine.service}. This
 * separation keeps controllers thin, testable, and focused on presentation concerns.
 *
 * <p>Controller instances are typically instantiated by the JavaFX {@code FXMLLoader}
 * or by a lightweight dependency-injection mechanism configured in the
 * {@link com.jasmine.app} package.
 *
 * @since 1.0
 */
package com.jasmine.controller;
