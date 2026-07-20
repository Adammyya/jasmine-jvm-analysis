package com.jasmine.monitor;

import com.jasmine.exception.MonitoringException;
import com.jasmine.model.ThreadSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * JMX adapter for collecting JVM thread metrics.
 *
 * <p>This monitor interacts with {@link java.lang.management.ThreadMXBean} to
 * retrieve thread counts (live, daemon, peak, total started).
 *
 * @since 2.0
 */
public class ThreadMonitor {

    private static final Logger logger = LoggerFactory.getLogger(ThreadMonitor.class);
    
    private final ThreadMXBean threadBean;
    
    /**
     * Initializes the Thread monitor.
     *
     * @throws MonitoringException if the ThreadMXBean cannot be acquired
     */
    public ThreadMonitor() {
        this.threadBean = ManagementFactory.getThreadMXBean();
        if (this.threadBean == null) {
            throw new MonitoringException("MONITOR_THREAD_UNAVAILABLE", "ThreadMXBean is null");
        }
    }
    
    /**
     * Collects a point-in-time snapshot of Thread metrics.
     *
     * @return a new {@link ThreadSnapshot}, potentially marked unavailable if data cannot be read
     */
    public ThreadSnapshot collect() {
        try {
            return new ThreadSnapshot(
                    threadBean.getThreadCount(),
                    threadBean.getDaemonThreadCount(),
                    threadBean.getPeakThreadCount(),
                    threadBean.getTotalStartedThreadCount(),
                    System.currentTimeMillis(),
                    true
            );
        } catch (Exception e) {
            logger.warn("Failed to collect Thread snapshot", e);
            return ThreadSnapshot.unavailable();
        }
    }
}
