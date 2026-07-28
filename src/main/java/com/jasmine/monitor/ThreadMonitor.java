package com.jasmine.monitor;

import com.jasmine.exception.MonitoringException;
import com.jasmine.model.ThreadSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * JMX adapter for collecting JVM thread metrics.
 *
 * <p>This monitor interacts with {@link java.lang.management.ThreadMXBean} to
 * retrieve thread counts (live, daemon, peak, total started) and thread state
 * distribution (waiting, blocked).
 *
 * <p><strong>Sprint 2 Enhancement:</strong> Uses {@code dumpAllThreads(false, false)}
 * to iterate thread states without acquiring lock/monitor information (the two
 * {@code false} arguments). This is significantly cheaper than a full thread dump
 * and provides the state counts needed for the dashboard.
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
     * Collects a point-in-time snapshot of Thread metrics including state distribution.
     *
     * @return a new {@link ThreadSnapshot}, potentially marked unavailable if data cannot be read
     */
    public ThreadSnapshot collect() {
        try {
            int waitingCount = 0;
            int blockedCount = 0;
            
            // Collect thread state distribution.
            // dumpAllThreads(false, false) skips lock info and synchronizer info,
            // making it much cheaper than a full diagnostic dump.
            ThreadInfo[] threadInfos = threadBean.dumpAllThreads(false, false);
            for (ThreadInfo info : threadInfos) {
                switch (info.getThreadState()) {
                    case WAITING, TIMED_WAITING -> waitingCount++;
                    case BLOCKED -> blockedCount++;
                    default -> { /* RUNNABLE, NEW, TERMINATED — not counted here */ }
                }
            }
            
            return new ThreadSnapshot(
                    threadBean.getThreadCount(),
                    threadBean.getDaemonThreadCount(),
                    threadBean.getPeakThreadCount(),
                    threadBean.getTotalStartedThreadCount(),
                    waitingCount,
                    blockedCount,
                    System.currentTimeMillis(),
                    true
            );
        } catch (Exception e) {
            logger.warn("Failed to collect Thread snapshot", e);
            return ThreadSnapshot.unavailable();
        }
    }
}
