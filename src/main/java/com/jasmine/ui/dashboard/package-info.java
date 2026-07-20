/**
 * Dashboard view providing the system-overview landing page for JASMINE.
 *
 * <p>This package implements the primary dashboard that users see upon launching the
 * application. It presents a high-level, at-a-glance summary of the monitored JVM's
 * health and recent experiment activity through a grid of metric cards and summary
 * widgets. Key elements rendered by this view include:
 *
 * <ul>
 *   <li><strong>Real-Time Metric Cards</strong> — compact tiles displaying current
 *       heap usage, CPU load, live thread count, and GC pause rate, each with
 *       sparkline trend indicators powered by {@link com.jasmine.chart}</li>
 *   <li><strong>System Health Indicator</strong> — an aggregate status badge
 *       (Healthy / Warning / Critical) derived from the latest
 *       {@link com.jasmine.recommendation} engine output</li>
 *   <li><strong>Recent Experiments</strong> — a summary table of the last N
 *       benchmark experiments with quick-access links to detailed results</li>
 *   <li><strong>Recommendation Highlights</strong> — the top tuning suggestions
 *       surfaced from the recommendation engine for immediate visibility</li>
 *   <li><strong>Quick Actions</strong> — shortcut buttons for starting a new
 *       benchmark, opening the report generator, or navigating to settings</li>
 * </ul>
 *
 * <p>The dashboard subscribes to monitoring events via the {@link com.jasmine.event}
 * bus and updates its metric cards on the JavaFX Application Thread in near
 * real-time.
 *
 * @since 1.0
 */
package com.jasmine.ui.dashboard;
