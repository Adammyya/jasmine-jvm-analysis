package com.jasmine.model;

/**
 * Immutable snapshot of Garbage Collection metrics collected from JMX.
 *
 * <p>Modern JVMs typically use generational garbage collection, meaning there are
 * often multiple GC collectors active simultaneously (e.g., a "young" generation
 * collector and an "old" generation collector like G1 Young Generation and
 * G1 Old Generation).
 *
 * <p>This snapshot aggregates metrics across <em>all</em> active collectors to provide
 * a total view of GC activity for the JVM.
 *
 * <p>Sprint 2 addition: {@code lastCollectionDurationMs} is tracked heuristically
 * by the monitor comparing collection counts between ticks. When the count increases,
 * the delta in total GC time approximates the last collection's duration.
 *
 * @param totalCollections         total number of GC cycles since JVM start
 * @param totalCollectionTimeMs    total accumulated time spent performing GC, in milliseconds
 * @param lastCollectionDurationMs approximate duration of the most recent GC event, in ms (0 if unknown)
 * @param collectorNames           comma-separated list of active GC collector names
 * @param timestamp                collection time in epoch milliseconds
 * @param available                {@code true} if the snapshot contains valid data
 * @since 2.0
 */
public record GcSnapshot(
        long totalCollections,
        long totalCollectionTimeMs,
        long lastCollectionDurationMs,
        String collectorNames,
        long timestamp,
        boolean available
) {

    /**
     * Creates an unavailable snapshot for use when JMX GC data cannot be collected.
     *
     * @return a snapshot where {@code available} is {@code false} and all values are {@code 0} or empty
     */
    public static GcSnapshot unavailable() {
        return new GcSnapshot(0L, 0L, 0L, "N/A", System.currentTimeMillis(), false);
    }
}
