package com.jasmine.monitor;

import com.jasmine.exception.MonitoringException;
import com.jasmine.model.RuntimeSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

/**
 * JMX adapter for collecting JVM Runtime metrics.
 *
 * <p>This monitor interacts with {@link java.lang.management.RuntimeMXBean} to
 * retrieve uptime, JVM name/version, and input arguments.
 *
 * @since 2.0
 */
public class RuntimeMonitor {

    private static final Logger logger = LoggerFactory.getLogger(RuntimeMonitor.class);
    
    private final RuntimeMXBean runtimeBean;
    
    // Cache static data
    private final String jvmName;
    private final String jvmVersion;
    private final long startTimeMs;
    private final String inputArguments;
    
    /**
     * Initializes the Runtime monitor.
     *
     * @throws MonitoringException if the RuntimeMXBean cannot be acquired
     */
    public RuntimeMonitor() {
        this.runtimeBean = ManagementFactory.getRuntimeMXBean();
        if (this.runtimeBean == null) {
            throw new MonitoringException("MONITOR_RUNTIME_UNAVAILABLE", "RuntimeMXBean is null");
        }
        
        // Cache data that doesn't change during the JVM lifetime
        this.jvmName = runtimeBean.getVmName();
        this.jvmVersion = runtimeBean.getVmVersion();
        this.startTimeMs = runtimeBean.getStartTime();
        
        List<String> args = runtimeBean.getInputArguments();
        this.inputArguments = args != null && !args.isEmpty() ? String.join(" ", args) : "None";
    }
    
    /**
     * Collects a point-in-time snapshot of Runtime metrics.
     *
     * @return a new {@link RuntimeSnapshot}, potentially marked unavailable if data cannot be read
     */
    public RuntimeSnapshot collect() {
        try {
            return new RuntimeSnapshot(
                    runtimeBean.getUptime(),
                    jvmName,
                    jvmVersion,
                    startTimeMs,
                    inputArguments,
                    System.currentTimeMillis(),
                    true
            );
        } catch (Exception e) {
            logger.warn("Failed to collect Runtime snapshot", e);
            return RuntimeSnapshot.unavailable();
        }
    }
}
