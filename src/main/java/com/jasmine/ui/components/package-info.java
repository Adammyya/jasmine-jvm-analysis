/**
 * Reusable UI components for JASMINE.
 *
 * <p>This package contains self-contained, composable JavaFX controls and widgets
 * that are shared across multiple views throughout the application. Each component
 * encapsulates its own layout (FXML or programmatic), styling, and interaction
 * logic, exposing a clean property-based API for integration. Components include:
 *
 * <ul>
 *   <li><strong>MetricCard</strong> — a compact tile displaying a labelled numeric
 *       value with an optional unit suffix, trend arrow, and embedded sparkline
 *       chart; used extensively on the dashboard</li>
 *   <li><strong>StatusIndicator</strong> — a colour-coded badge (green / amber / red)
 *       with tooltip text representing system health or task state</li>
 *   <li><strong>ProgressPanel</strong> — a composite widget combining a
 *       {@link javafx.scene.control.ProgressBar}, elapsed-time label, and cancel
 *       button for long-running operations</li>
 *   <li><strong>SearchableTable</strong> — a {@link javafx.scene.control.TableView}
 *       wrapper with built-in text-filter field and column-sort persistence</li>
 *   <li><strong>SparklineChart</strong> — a minimal, borderless line chart for
 *       inline trend visualization within cards and table cells</li>
 *   <li><strong>IconButton</strong> — a styled button with embedded SVG icon and
 *       optional tooltip, conforming to the active theme</li>
 * </ul>
 *
 * <p>All components respect the theme system managed by {@link com.jasmine.ui.themes}
 * and adapt automatically when the user switches themes.
 *
 * @since 1.0
 */
package com.jasmine.ui.components;
