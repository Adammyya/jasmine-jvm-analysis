/**
 * JMX-based runtime monitoring for JASMINE.
 *
 * <p>This package provides live JVM health monitoring by leveraging the
 * {@link java.lang.management.ManagementFactory} MXBean infrastructure. It captures
 * and exposes real-time metrics including:
 *
 * <ul>
 *   <li><strong>Heap &amp; Non-Heap Memory</strong> — used, committed, and maximum
 *       memory for each memory pool (Eden, Survivor, Old Gen, Metaspace)</li>
 *   <li><strong>Thread Activity</strong> — live thread count, daemon count, peak count,
 *       and deadlock detection via {@link java.lang.management.ThreadMXBean}</li>
 *   <li><strong>CPU Utilization</strong> — process CPU time and system load average
 *       from {@link java.lang.management.OperatingSystemMXBean}</li>
 *   <li><strong>Garbage Collection</strong> — per-collector invocation counts,
 *       cumulative pause times, and last-GC cause from
 *       {@link java.lang.management.GarbageCollectorMXBean}</li>
 *   <li><strong>Class Loading</strong> — loaded, unloaded, and total class counts</li>
 * </ul>
 *
 * <p>Metric snapshots are published as immutable {@link com.jasmine.dto} records onto
 * the {@link com.jasmine.event} bus at configurable intervals controlled by the
 * {@link com.jasmine.scheduler} package. The {@link com.jasmine.analytics} package
 * consumes these snapshots for trend analysis and anomaly detection.
 *
 * @since 1.0
 */
package com.jasmine.monitor;
