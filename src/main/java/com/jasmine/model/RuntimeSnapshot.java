package com.jasmine.model;

/**
 * Immutable snapshot of JVM runtime environment information collected from JMX.
 *
 * <p>Unlike metrics such as CPU or memory which change constantly, much of the
 * runtime information (JVM name, version, input arguments) is static for the
 * lifetime of the JVM. However, {@code uptimeMs} is dynamic and must be re-read.
 *
 * <p>Sprint 2 additions: PID (via {@code ProcessHandle}), JVM vendor, Java home
 * directory, and operating system name provide the contextual metadata needed
 * for the Runtime Information card.
 *
 * @param uptimeMs       uptime of the Java virtual machine in milliseconds
 * @param jvmName        the Java virtual machine implementation name
 * @param jvmVersion     the Java virtual machine implementation version
 * @param jvmVendor      the Java virtual machine vendor (e.g., "Oracle Corporation")
 * @param pid            the OS process ID of this JVM instance
 * @param javaHome       path to the Java installation directory
 * @param osName         operating system name (e.g., "Windows 11")
 * @param startTimeMs    start time of the Java virtual machine in epoch milliseconds
 * @param inputArguments summary or full string of JVM input arguments (flags)
 * @param timestamp      collection time in epoch milliseconds
 * @param available      {@code true} if the snapshot contains valid data
 * @since 2.0
 */
public record RuntimeSnapshot(
        long uptimeMs,
        String jvmName,
        String jvmVersion,
        String jvmVendor,
        long pid,
        String javaHome,
        String osName,
        long startTimeMs,
        String inputArguments,
        long timestamp,
        boolean available
) {

    /**
     * Creates an unavailable snapshot for use when JMX runtime data cannot be collected.
     *
     * @return a snapshot where {@code available} is {@code false}
     */
    public static RuntimeSnapshot unavailable() {
        return new RuntimeSnapshot(
                0L, "Unknown JVM", "Unknown", "Unknown", -1L, "Unknown", "Unknown",
                0L, "N/A", System.currentTimeMillis(), false
        );
    }
}
