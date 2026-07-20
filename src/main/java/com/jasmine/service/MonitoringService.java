package com.jasmine.service;

import com.jasmine.exception.MonitoringException;
import com.jasmine.model.*;
import com.jasmine.monitor.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service orchestrator for all JMX monitoring.
 *
 * <p>This service acts as a facade over individual monitor classes. It guarantees
 * that all metrics are collected in a single cohesive pass, creating a unified
 * {@link MonitoringSnapshot} representing the state of the JVM at that instant.
 *
 * <p>By centralizing the monitors here, the UI layer and schedulers only need
 * to depend on one service, drastically reducing coupling.
 *
 * @since 2.0
 */
public class MonitoringService {

    private static final Logger logger = LoggerFactory.getLogger(MonitoringService.class);
    
    private CpuMonitor cpuMonitor;
    private MemoryMonitor memoryMonitor;
    private ThreadMonitor threadMonitor;
    private GarbageCollectorMonitor gcMonitor;
    private RuntimeMonitor runtimeMonitor;
    
    private boolean initialized = false;
    private boolean available = false;

    /**
     * Initializes the monitoring service and all underlying JMX monitors.
     *
     * <p>This is a separate method rather than happening in the constructor so that
     * application startup isn't blocked if JMX throws an exception during bean acquisition.
     *
     * @throws MonitoringException if a critical JMX bean cannot be acquired
     */
    public void initialize() {
        if (initialized) return;
        
        logger.info("Initializing MonitoringService...");
        
        try {
            this.cpuMonitor = new CpuMonitor();
            this.memoryMonitor = new MemoryMonitor();
            this.threadMonitor = new ThreadMonitor();
            this.gcMonitor = new GarbageCollectorMonitor();
            this.runtimeMonitor = new RuntimeMonitor();
            
            this.available = true;
            logger.info("MonitoringService initialized successfully.");
        } catch (MonitoringException e) {
            logger.error("Failed to initialize one or more JMX monitors. Monitoring will be degraded or unavailable.", e);
            this.available = false;
            throw e;
        } finally {
            this.initialized = true;
        }
    }
    
    /**
     * Collects an aggregate snapshot of all metrics.
     *
     * <p>If the service is not initialized or unavailable, it returns a snapshot
     * comprised entirely of unavailable placeholder values.
     *
     * @return an aggregate {@link MonitoringSnapshot}
     */
    public MonitoringSnapshot collectSnapshot() {
        if (!initialized || !available) {
            return new MonitoringSnapshot(
                    CpuSnapshot.unavailable(),
                    MemorySnapshot.unavailable(),
                    ThreadSnapshot.unavailable(),
                    GcSnapshot.unavailable(),
                    RuntimeSnapshot.unavailable(),
                    System.currentTimeMillis()
            );
        }
        
        long start = System.nanoTime();
        
        CpuSnapshot cpu = cpuMonitor.collect();
        MemorySnapshot memory = memoryMonitor.collect();
        ThreadSnapshot threads = threadMonitor.collect();
        GcSnapshot gc = gcMonitor.collect();
        RuntimeSnapshot runtime = runtimeMonitor.collect();
        
        long durationMs = (System.nanoTime() - start) / 1_000_000;
        if (durationMs > 50) {
            logger.warn("JMX snapshot collection took {} ms", durationMs);
        }
        
        return new MonitoringSnapshot(cpu, memory, threads, gc, runtime, System.currentTimeMillis());
    }

    /**
     * Indicates whether the service successfully bound to JMX and is able to collect data.
     *
     * @return true if available
     */
    public boolean isAvailable() {
        return available;
    }
}
