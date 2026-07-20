/**
 * Statistical analysis and data processing for JASMINE.
 *
 * <p>This package provides the analytical backbone of the application, transforming
 * raw benchmark and monitoring data into meaningful insights. It operates on the
 * immutable {@link com.jasmine.dto} records produced by the {@link com.jasmine.monitor}
 * and {@link com.jasmine.benchmark} packages and outputs processed results consumed
 * by the {@link com.jasmine.recommendation} and {@link com.jasmine.report} packages.
 *
 * <p>Core analytical capabilities include:
 *
 * <ul>
 *   <li><strong>Descriptive Statistics</strong> — mean, median, standard deviation,
 *       percentiles (p50, p95, p99), min/max, and coefficient of variation</li>
 *   <li><strong>Trend Detection</strong> — linear regression and moving-average
 *       calculations over time-series monitoring data to identify memory leaks,
 *       CPU ramp-ups, or GC degradation</li>
 *   <li><strong>Outlier Detection</strong> — IQR-based and z-score filtering to
 *       isolate anomalous benchmark iterations or monitoring spikes</li>
 *   <li><strong>Comparative Analysis</strong> — side-by-side comparison of benchmark
 *       runs across different JVM configurations or GC algorithms</li>
 *   <li><strong>Aggregation</strong> — rolling up fine-grained snapshots into
 *       summary windows for dashboard display and report generation</li>
 * </ul>
 *
 * <p>All computations are performed in-process using pure Java; no external numerical
 * libraries are required.
 *
 * @since 1.0
 */
package com.jasmine.analytics;
