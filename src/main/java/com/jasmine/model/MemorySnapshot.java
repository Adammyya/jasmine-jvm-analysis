package com.jasmine.model;

/**
 * Immutable snapshot of JVM memory utilization collected from JMX.
 *
 * <p>All memory values are expressed in <strong>bytes</strong>. The dashboard
 * layer is responsible for converting to MB/GB for display via {@code FormatUtil}.
 *
 * <p>JVM memory is divided into two pools:
 * <ul>
 *     <li><strong>Heap:</strong> Managed by the garbage collector. Contains all
 *         objects created by Java code. The primary target of GC operations.</li>
 *     <li><strong>Non-Heap:</strong> Includes the metaspace (class metadata),
 *         code cache (JIT-compiled bytecode), and other JVM-internal structures.
 *         Not garbage collected in the traditional sense.</li>
 * </ul>
 *
 * <p>Three memory levels exist for heap:
 * <ol>
 *     <li>{@code used} — bytes currently occupied by live objects</li>
 *     <li>{@code committed} — bytes currently allocated from the OS (may exceed used)</li>
 *     <li>{@code max} — the hard ceiling configured by {@code -Xmx} (may be {@code -1} if unlimited)</li>
 * </ol>
 *
 * @param heapUsed       heap memory currently used, in bytes
 * @param heapCommitted  heap memory committed (allocated from OS), in bytes
 * @param heapMax        maximum heap memory ({@code -Xmx}), in bytes; {@code -1} if unlimited
 * @param nonHeapUsed    non-heap memory used (metaspace + code cache etc.), in bytes
 * @param nonHeapMax     maximum non-heap memory, in bytes; {@code -1} if unlimited
 * @param timestamp      collection time in epoch milliseconds
 * @param available      {@code true} if the snapshot contains valid data
 * @since 2.0
 */
public record MemorySnapshot(
        long heapUsed,
        long heapCommitted,
        long heapMax,
        long nonHeapUsed,
        long nonHeapMax,
        long timestamp,
        boolean available
) {

    /**
     * Creates an unavailable snapshot for use when JMX memory data cannot be collected.
     *
     * @return a snapshot where {@code available} is {@code false} and all values are {@code 0}
     */
    public static MemorySnapshot unavailable() {
        return new MemorySnapshot(0L, 0L, -1L, 0L, -1L, System.currentTimeMillis(), false);
    }

    /**
     * Returns the heap utilization as a ratio in the range {@code [0.0, 1.0]}.
     *
     * <p>Returns {@code 0.0} if the maximum heap size is unknown ({@code heapMax <= 0}).
     *
     * @return heap utilization ratio, or {@code 0.0} if max is unknown
     */
    public double heapUtilizationRatio() {
        if (heapMax <= 0) {
            return heapCommitted > 0 ? (double) heapUsed / heapCommitted : 0.0;
        }
        return (double) heapUsed / heapMax;
    }

    /**
     * Returns the heap utilization as a percentage.
     *
     * @return percentage in range {@code [0.0, 100.0]}
     */
    public double heapUtilizationPercent() {
        return heapUtilizationRatio() * 100.0;
    }
}
