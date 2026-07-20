/**
 * Experiment configuration and management views for JASMINE.
 *
 * <p>This package provides the JavaFX views for creating, editing, and managing
 * JVM performance experiments — the top-level organizational unit that groups
 * related benchmark runs under a common configuration. Views include:
 *
 * <ul>
 *   <li><strong>Experiment Wizard</strong> — a multi-step form guiding users through
 *       experiment creation: naming, selecting benchmark suites, configuring JVM
 *       flags, defining success criteria, and scheduling execution</li>
 *   <li><strong>Experiment List</strong> — a searchable, sortable master table of
 *       all saved experiments with status badges (Draft, Running, Completed,
 *       Failed) and quick-action menus</li>
 *   <li><strong>Experiment Detail</strong> — a read-only summary panel showing an
 *       experiment's full configuration, associated benchmark results, and
 *       generated recommendations</li>
 *   <li><strong>Comparison View</strong> — side-by-side comparison of two or more
 *       experiments, highlighting performance deltas across key metrics</li>
 * </ul>
 *
 * <p>All persistence and business logic is delegated to the
 * {@link com.jasmine.service} layer; views in this package focus exclusively on
 * presentation and user interaction.
 *
 * @since 1.0
 */
package com.jasmine.ui.experiment;
