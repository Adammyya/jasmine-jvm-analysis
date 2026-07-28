package com.jasmine.monitor;

import com.jasmine.exception.MonitoringException;
import com.jasmine.model.GcSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JMX adapter for collecting JVM Garbage Collection metrics.
 *
 * <p>This monitor interacts with multiple {@link java.lang.management.GarbageCollectorMXBean}
 * instances (typically there are multiple collectors active, e.g. Young and Old generation).
 *
 * <p><strong>Sprint 2 Enhancement:</strong> Tracks the "last collection duration"
 * heuristically by comparing the total collection count and total collection time
 * between consecutive snapshots. When the count increases, the delta in total time
 * approximates the most recent collection's duration.
 *
 * @since 2.0
 */
public class GarbageCollectorMonitor {

    private static final Logger logger = LoggerFactory.getLogger(GarbageCollectorMonitor.class);
    
    private final List<GarbageCollectorMXBean> gcBeans;
    
    // State for heuristic last-collection tracking
    private long previousTotalCollections = 0;
    private long previousTotalTimeMs = 0;
    private long lastCollectionDurationMs = 0;
    
    /**
     * Initializes the GC monitor.
     *
     * @throws MonitoringException if no GarbageCollectorMXBeans can be acquired
     */
    public GarbageCollectorMonitor() {
        this.gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        if (this.gcBeans == null || this.gcBeans.isEmpty()) {
            throw new MonitoringException("MONITOR_GC_UNAVAILABLE", "No GarbageCollectorMXBeans found");
        }
    }
    
    /**
     * Collects a point-in-time snapshot of GC metrics, aggregated across all collectors.
     *
     * @return a new {@link GcSnapshot}, potentially marked unavailable if data cannot be read
     */
    public GcSnapshot collect() {
        try {
            long totalCollections = 0;
            long totalCollectionTimeMs = 0;
            
            for (GarbageCollectorMXBean gcBean : gcBeans) {
                long count = gcBean.getCollectionCount();
                if (count != -1) {
                    totalCollections += count;
                }
                
                long time = gcBean.getCollectionTime();
                if (time != -1) {
                    totalCollectionTimeMs += time;
                }
            }
            
            // Heuristic: detect new GC events by comparing counts
            if (totalCollections > previousTotalCollections) {
                lastCollectionDurationMs = totalCollectionTimeMs - previousTotalTimeMs;
                if (lastCollectionDurationMs < 0) {
                    lastCollectionDurationMs = 0; // safety guard
                }
            }
            previousTotalCollections = totalCollections;
            previousTotalTimeMs = totalCollectionTimeMs;
            
            String collectorNames = gcBeans.stream()
                    .map(GarbageCollectorMXBean::getName)
                    .collect(Collectors.joining(", "));
                    
            return new GcSnapshot(
                    totalCollections,
                    totalCollectionTimeMs,
                    lastCollectionDurationMs,
                    collectorNames,
                    System.currentTimeMillis(),
                    true
            );
        } catch (Exception e) {
            logger.warn("Failed to collect GC snapshot", e);
            return GcSnapshot.unavailable();
        }
    }
}
