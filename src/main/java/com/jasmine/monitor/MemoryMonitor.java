package com.jasmine.monitor;

import com.jasmine.exception.MonitoringException;
import com.jasmine.model.MemorySnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * JMX adapter for collecting JVM memory utilization metrics.
 *
 * <p>This monitor interacts with {@link java.lang.management.MemoryMXBean} to
 * retrieve heap and non-heap memory usage.
 *
 * @since 2.0
 */
public class MemoryMonitor {

    private static final Logger logger = LoggerFactory.getLogger(MemoryMonitor.class);
    
    private final MemoryMXBean memoryBean;
    
    /**
     * Initializes the Memory monitor.
     *
     * @throws MonitoringException if the MemoryMXBean cannot be acquired
     */
    public MemoryMonitor() {
        this.memoryBean = ManagementFactory.getMemoryMXBean();
        if (this.memoryBean == null) {
            throw new MonitoringException("MONITOR_MEMORY_UNAVAILABLE", "MemoryMXBean is null");
        }
    }
    
    /**
     * Collects a point-in-time snapshot of Memory metrics.
     *
     * @return a new {@link MemorySnapshot}, potentially marked unavailable if data cannot be read
     */
    public MemorySnapshot collect() {
        try {
            MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
            MemoryUsage nonHeapUsage = memoryBean.getNonHeapMemoryUsage();
            
            return new MemorySnapshot(
                    heapUsage.getUsed(),
                    heapUsage.getCommitted(),
                    heapUsage.getMax(),
                    nonHeapUsage.getUsed(),
                    nonHeapUsage.getMax(),
                    System.currentTimeMillis(),
                    true
            );
        } catch (Exception e) {
            logger.warn("Failed to collect Memory snapshot", e);
            return MemorySnapshot.unavailable();
        }
    }
}
