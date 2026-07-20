package com.jasmine.model;

/**
 * Immutable snapshot of CPU utilization metrics collected from JMX.
 *
 * <p>CPU load values are expressed as ratios in the range {@code [0.0, 1.0]},
 * where {@code 1.0} represents 100% utilization. A value of {@code -1.0} is a
 * JMX sentinel indicating the metric is not available on the current platform or
 * JVM implementation.
 *
 * <p>The distinction between process and system CPU load is important:
 * <ul>
 *     <li>{@code processCpuLoad} — CPU consumed exclusively by this JVM process.</li>
 *     <li>{@code systemCpuLoad} — Total CPU consumed by all processes on the host OS.</li>
 * </ul>
 *
 * <p>This record is a <em>domain object</em>, not a DTO. It represents the raw reading
 * as delivered by JMX, with no formatting applied.
 *
 * @param processCpuLoad       JVM process CPU load ratio (0.0–1.0), or {@code -1.0} if unavailable
 * @param systemCpuLoad        System-wide CPU load ratio (0.0–1.0), or {@code -1.0} if unavailable
 * @param availableProcessors  Number of logical processors available to the JVM
 * @param timestamp            Collection time in epoch milliseconds
 * @param available            {@code true} if at least the process CPU load is valid
 * @since 2.0
 */
public record CpuSnapshot(
        double processCpuLoad,
        double systemCpuLoad,
        int availableProcessors,
        long timestamp,
        boolean available
) {

    /**
     * Sentinel value returned by the JVM for unavailable CPU metrics.
     * Defined by the {@code com.sun.management.OperatingSystemMXBean} contract.
     */
    public static final double UNAVAILABLE = -1.0;

    /**
     * Creates an unavailable snapshot, used when JMX CPU data cannot be collected.
     *
     * @return a snapshot where {@code available} is {@code false} and all load values are {@code -1.0}
     */
    public static CpuSnapshot unavailable() {
        return new CpuSnapshot(UNAVAILABLE, UNAVAILABLE, 0, System.currentTimeMillis(), false);
    }

    /**
     * Returns the process CPU load as a percentage (0.0–100.0).
     *
     * @return percentage value, or {@code -1.0} if unavailable
     */
    public double processCpuPercent() {
        return processCpuLoad >= 0.0 ? processCpuLoad * 100.0 : UNAVAILABLE;
    }

    /**
     * Returns the system CPU load as a percentage (0.0–100.0).
     *
     * @return percentage value, or {@code -1.0} if unavailable
     */
    public double systemCpuPercent() {
        return systemCpuLoad >= 0.0 ? systemCpuLoad * 100.0 : UNAVAILABLE;
    }
}
