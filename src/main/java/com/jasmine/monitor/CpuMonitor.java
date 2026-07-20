package com.jasmine.monitor;

import com.jasmine.exception.MonitoringException;
import com.jasmine.model.CpuSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

/**
 * JMX adapter for collecting CPU utilization metrics.
 *
 * <p>This monitor interacts with {@link java.lang.management.OperatingSystemMXBean}.
 * Note that standard Java only exposes system load average. To get process and
 * system CPU load percentages, we must cast to the Sun/Oracle-specific extension:
 * {@code com.sun.management.OperatingSystemMXBean}.
 *
 * <p>This cast is safe on almost all modern JVMs (HotSpot, OpenJ9), but the monitor
 * degrades gracefully if the cast fails.
 *
 * @since 2.0
 */
public class CpuMonitor {

    private static final Logger logger = LoggerFactory.getLogger(CpuMonitor.class);
    
    private final OperatingSystemMXBean osBean;
    private com.sun.management.OperatingSystemMXBean sunOsBean;
    
    /**
     * Initializes the CPU monitor and attempts to bind to the Sun-specific MXBean extension.
     *
     * @throws MonitoringException if the base OperatingSystemMXBean cannot be acquired
     */
    public CpuMonitor() {
        this.osBean = ManagementFactory.getOperatingSystemMXBean();
        if (this.osBean == null) {
            throw new MonitoringException("MONITOR_CPU_UNAVAILABLE", "OperatingSystemMXBean is null");
        }
        
        // Attempt to cast to the Sun extension for detailed CPU metrics
        if (this.osBean instanceof com.sun.management.OperatingSystemMXBean) {
            this.sunOsBean = (com.sun.management.OperatingSystemMXBean) this.osBean;
            logger.debug("Successfully bound to com.sun.management.OperatingSystemMXBean");
        } else {
            logger.warn("JVM does not support com.sun.management.OperatingSystemMXBean. CPU loads will be unavailable.");
            this.sunOsBean = null;
        }
    }
    
    /**
     * Collects a point-in-time snapshot of CPU metrics.
     *
     * @return a new {@link CpuSnapshot}, potentially marked unavailable if data cannot be read
     */
    public CpuSnapshot collect() {
        try {
            double processCpuLoad = CpuSnapshot.UNAVAILABLE;
            double systemCpuLoad = CpuSnapshot.UNAVAILABLE;
            int availableProcessors = osBean.getAvailableProcessors();
            
            if (sunOsBean != null) {
                // Returns -1.0 if the metric is not available on this platform (e.g. some Windows versions)
                processCpuLoad = sunOsBean.getProcessCpuLoad();
                systemCpuLoad = sunOsBean.getCpuLoad(); // getSystemCpuLoad() was deprecated in Java 14, getCpuLoad() is used now
            }
            
            boolean isAvailable = (processCpuLoad != CpuSnapshot.UNAVAILABLE || systemCpuLoad != CpuSnapshot.UNAVAILABLE);
            
            return new CpuSnapshot(
                    processCpuLoad,
                    systemCpuLoad,
                    availableProcessors,
                    System.currentTimeMillis(),
                    isAvailable
            );
        } catch (Exception e) {
            logger.warn("Failed to collect CPU snapshot", e);
            return CpuSnapshot.unavailable();
        }
    }
}
