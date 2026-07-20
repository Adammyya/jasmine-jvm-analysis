package com.jasmine.exception;

/**
 * Exception thrown when a JMX monitoring operation fails or is unavailable.
 *
 * <p>This exception is used in two scenarios:
 * <ol>
 *     <li><strong>Hard failure:</strong> The JMX MXBean could not be acquired from
 *         {@code ManagementFactory} (e.g., platform does not support the bean).</li>
 *     <li><strong>Soft failure:</strong> The MXBean was acquired but returned a sentinel
 *         value indicating the metric is unavailable on this platform (e.g.,
 *         {@code getProcessCpuLoad()} returning {@code -1.0} on certain JVMs).</li>
 * </ol>
 *
 * <p>Monitors that encounter recoverable soft failures should <em>not</em> throw this
 * exception — they should return a snapshot with {@code available = false}. Reserve
 * this exception for truly exceptional conditions (e.g., null MXBean from the factory).
 *
 * <p><strong>Error Code Conventions:</strong>
 * <ul>
 *     <li>{@code MONITOR_CPU_UNAVAILABLE} — CPU load metric unavailable</li>
 *     <li>{@code MONITOR_MEMORY_UNAVAILABLE} — Memory MXBean unavailable</li>
 *     <li>{@code MONITOR_THREAD_UNAVAILABLE} — Thread MXBean unavailable</li>
 *     <li>{@code MONITOR_GC_UNAVAILABLE} — GC MXBeans unavailable</li>
 *     <li>{@code MONITOR_RUNTIME_UNAVAILABLE} — Runtime MXBean unavailable</li>
 * </ul>
 *
 * @since 2.0
 */
public class MonitoringException extends JasmineException {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new monitoring exception with an error code and message.
     *
     * @param errorCode machine-readable error classification (e.g., {@code "MONITOR_CPU_UNAVAILABLE"})
     * @param message   human-readable description of the failure
     */
    public MonitoringException(String errorCode, String message) {
        super(errorCode, message);
    }

    /**
     * Creates a new monitoring exception with an error code, message, and cause.
     *
     * @param errorCode machine-readable error classification
     * @param message   human-readable description of the failure
     * @param cause     the underlying exception that triggered this failure
     */
    public MonitoringException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
