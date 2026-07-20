/**
 * Top-level UI layer package for JASMINE.
 *
 * <p>This package serves as the root of the user-interface module hierarchy. It
 * contains shared UI infrastructure and acts as the organizational parent for all
 * view-specific sub-packages. Responsibilities at this level include:
 *
 * <ul>
 *   <li><strong>View Orchestration</strong> — the primary stage layout, root scene
 *       graph construction, and top-level content-area management into which
 *       child views from sub-packages are loaded</li>
 *   <li><strong>FXML Loader Utilities</strong> — centralized helper methods for
 *       loading FXML resources, injecting controller factories, and handling
 *       resource-bundle localization</li>
 *   <li><strong>Shared CSS</strong> — base stylesheets and CSS custom properties
 *       inherited by all child views, establishing the visual baseline</li>
 *   <li><strong>Scene Lifecycle</strong> — listening for scene-level events
 *       (resize, focus, close-request) and delegating them to the appropriate
 *       handlers</li>
 * </ul>
 *
 * <p>Sub-packages under {@code com.jasmine.ui} are organized by functional area:
 * {@link com.jasmine.ui.dashboard}, {@link com.jasmine.ui.benchmark},
 * {@link com.jasmine.ui.experiment}, {@link com.jasmine.ui.reports},
 * {@link com.jasmine.ui.settings}, {@link com.jasmine.ui.components},
 * {@link com.jasmine.ui.layouts}, {@link com.jasmine.ui.dialogs},
 * {@link com.jasmine.ui.navigation}, and {@link com.jasmine.ui.themes}.
 *
 * @since 1.0
 */
package com.jasmine.ui;
