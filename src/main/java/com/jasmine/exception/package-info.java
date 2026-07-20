/**
 * Custom exception hierarchy for typed error handling in JASMINE.
 *
 * <p>This package defines a structured exception taxonomy that replaces generic
 * {@link java.lang.RuntimeException} usage with domain-specific exception types.
 * A well-defined hierarchy enables callers to catch and handle errors at the
 * appropriate granularity and ensures that error messages carry sufficient context
 * for diagnostics. The hierarchy is organized as follows:
 *
 * <ul>
 *   <li><strong>{@code JasmineException}</strong> — the sealed base class for all
 *       application exceptions; may be caught as a catch-all at service boundaries</li>
 *   <li><strong>{@code DatabaseException}</strong> — wraps JDBC and SQLite failures,
 *       carrying the original {@link java.sql.SQLException} as a cause</li>
 *   <li><strong>{@code BenchmarkException}</strong> — signals errors during benchmark
 *       execution such as timeout, resource exhaustion, or workload misconfiguration</li>
 *   <li><strong>{@code MonitoringException}</strong> — JMX access failures,
 *       unavailable MXBeans, or connection losses to monitored JVMs</li>
 *   <li><strong>{@code ConfigurationException}</strong> — invalid or missing
 *       configuration properties detected at startup or during hot-reload</li>
 *   <li><strong>{@code ValidationException}</strong> — user-input validation failures
 *       carrying a list of individual constraint violations</li>
 *   <li><strong>{@code ReportGenerationException}</strong> — failures during report
 *       rendering (I/O errors, template processing issues)</li>
 * </ul>
 *
 * <p>All exceptions in this hierarchy are unchecked (extend {@link RuntimeException})
 * to avoid polluting method signatures, while still providing precise type information
 * for selective handling via pattern matching.
 *
 * @since 1.0
 */
package com.jasmine.exception;
