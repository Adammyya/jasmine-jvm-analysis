/**
 * Theme management and CSS loading for JASMINE.
 *
 * <p>This package manages the visual appearance of the application by controlling
 * which CSS stylesheets are applied to the JavaFX scene graph. It supports runtime
 * theme switching without requiring application restart and provides infrastructure
 * for maintaining multiple visual themes. Capabilities include:
 *
 * <ul>
 *   <li><strong>Theme Registry</strong> — a catalogue of available themes (e.g.,
 *       Light, Dark, System-Follow) each defined by one or more CSS files that
 *       override base styles with theme-specific colours, shadows, and
 *       typography</li>
 *   <li><strong>Runtime Switching</strong> — applying a new theme by swapping
 *       stylesheet references on the active {@link javafx.scene.Scene}, with all
 *       existing nodes immediately reflecting the updated styles via CSS
 *       inheritance</li>
 *   <li><strong>CSS Custom Properties</strong> — defining a palette of CSS
 *       variables ({@code -fx-primary-color}, {@code -fx-surface-color}, etc.)
 *       that component stylesheets reference, enabling single-source-of-truth
 *       colour management</li>
 *   <li><strong>System Theme Detection</strong> — optional integration with the
 *       operating system's dark/light mode preference, automatically switching
 *       themes when the OS setting changes</li>
 *   <li><strong>Preference Persistence</strong> — remembering the user's theme
 *       selection across sessions via the {@link com.jasmine.config} package</li>
 * </ul>
 *
 * <p>All UI sub-packages depend on the CSS variables defined here rather than
 * hard-coding colour or font values, ensuring a consistent look-and-feel and
 * painless theme customization.
 *
 * @since 1.0
 */
package com.jasmine.ui.themes;
