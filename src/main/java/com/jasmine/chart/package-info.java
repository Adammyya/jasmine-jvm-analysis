/**
 * Chart and visualization builders for JASMINE, wrapping JavaFX Charts.
 *
 * <p>This package provides a fluent, domain-aware API for constructing JavaFX chart
 * nodes from benchmark and monitoring data. Rather than requiring controllers to
 * manually configure {@link javafx.scene.chart.XYChart.Series} and axis formatting,
 * chart builders in this package accept typed DTOs and produce fully styled,
 * ready-to-embed chart nodes. Supported chart types include:
 *
 * <ul>
 *   <li><strong>Line Charts</strong> — time-series plots for heap usage, CPU load,
 *       and thread counts over monitoring sessions</li>
 *   <li><strong>Bar Charts</strong> — comparative benchmark results across experiment
 *       configurations or GC algorithm selections</li>
 *   <li><strong>Area Charts</strong> — stacked memory-pool breakdowns showing Eden,
 *       Survivor, and Old Gen contributions</li>
 *   <li><strong>Pie Charts</strong> — GC time distribution by collector or memory
 *       pool allocation proportions</li>
 *   <li><strong>Scatter Plots</strong> — latency-vs-throughput or allocation-rate
 *       correlation visualizations</li>
 * </ul>
 *
 * <p>All chart builders respect the active theme from {@link com.jasmine.ui.themes},
 * applying consistent colour palettes, fonts, and legend positioning. Charts can be
 * exported to PNG for inclusion in generated reports.
 *
 * @since 1.0
 */
package com.jasmine.chart;
