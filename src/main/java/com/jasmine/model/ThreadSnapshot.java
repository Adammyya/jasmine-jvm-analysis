package com.jasmine.model;

/**
 * Immutable snapshot of JVM thread metrics collected from JMX.
 *
 * <p>Threads are the fundamental unit of execution in Java. JMX provides metrics on:
 * <ul>
 *     <li><strong>Live threads:</strong> Currently executing or blocked (includes both daemon and user threads).</li>
 *     <li><strong>Daemon threads:</strong> Background threads (e.g., GC, JMX, RMI) that do not prevent the JVM from exiting.</li>
 *     <li><strong>Peak threads:</strong> The maximum number of live threads since JVM start.</li>
 *     <li><strong>Total started:</strong> The total number of threads created since JVM start.</li>
 *     <li><strong>Waiting threads:</strong> Threads in {@code WAITING} or {@code TIMED_WAITING} state
 *         (blocked on I/O, locks, sleep, or condition variables).</li>
 *     <li><strong>Blocked threads:</strong> Threads in {@code BLOCKED} state (contending for a monitor lock).</li>
 * </ul>
 *
 * <p>A high blocked count relative to live threads indicates lock contention,
 * which is a critical performance concern that may require lock-free data
 * structures or reduced synchronized scope.
 *
 * @param threadCount             current number of live threads (daemon + user)
 * @param daemonThreadCount       current number of live daemon threads
 * @param peakThreadCount         peak live thread count since JVM start
 * @param totalStartedThreadCount total threads created and started since JVM start
 * @param waitingCount            threads in WAITING or TIMED_WAITING state
 * @param blockedCount            threads in BLOCKED state (monitor contention)
 * @param timestamp               collection time in epoch milliseconds
 * @param available               {@code true} if the snapshot contains valid data
 * @since 2.0
 */
public record ThreadSnapshot(
        int threadCount,
        int daemonThreadCount,
        int peakThreadCount,
        long totalStartedThreadCount,
        int waitingCount,
        int blockedCount,
        long timestamp,
        boolean available
) {

    /**
     * Creates an unavailable snapshot for use when JMX thread data cannot be collected.
     *
     * @return a snapshot where {@code available} is {@code false} and all values are {@code 0}
     */
    public static ThreadSnapshot unavailable() {
        return new ThreadSnapshot(0, 0, 0, 0L, 0, 0, System.currentTimeMillis(), false);
    }
}
