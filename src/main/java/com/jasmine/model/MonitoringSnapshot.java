package com.jasmine.model;

/**
 * Aggregate immutable snapshot containing all collected metrics for a single point in time.
 *
 * <p>The {@code MonitoringService} collects individual subsystem snapshots
 * (CPU, Memory, Threads, etc.) and packages them into this single aggregate record.
 * This ensures that when the UI updates, it receives a cohesive, atomic view of the
 * system state rather than piecemeal updates.
 *
 * @param cpu         CPU metrics snapshot
 * @param memory      Memory metrics snapshot
 * @param threads     Thread metrics snapshot
 * @param gc          Garbage Collection metrics snapshot
 * @param runtime     Runtime environment snapshot
 * @param collectedAt timestamp when this aggregate snapshot was finalized
 * @since 2.0
 */
public record MonitoringSnapshot(
        CpuSnapshot cpu,
        MemorySnapshot memory,
        ThreadSnapshot threads,
        GcSnapshot gc,
        RuntimeSnapshot runtime,
        long collectedAt
) {
}
