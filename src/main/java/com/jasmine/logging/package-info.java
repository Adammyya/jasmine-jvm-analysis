/**
 * Logging configuration, structured formatting, and log utilities for JASMINE.
 *
 * <p>This package centralizes all logging concerns, providing a consistent logging
 * facade and configuration layer that the entire application depends on. It builds
 * on top of {@link java.util.logging} (or an SLF4J-compatible backend) and adds:
 *
 * <ul>
 *   <li><strong>Structured Log Formatting</strong> — custom {@link java.util.logging.Formatter}
 *       implementations that produce machine-parseable log lines with ISO-8601
 *       timestamps, thread names, logger context, and structured key-value fields</li>
 *   <li><strong>Configuration Bootstrap</strong> — programmatic logger setup executed
 *       early in the application lifecycle (before other packages initialize) to
 *       ensure all startup activity is captured</li>
 *   <li><strong>Log-Level Management</strong> — runtime log-level adjustment APIs
 *       exposed to the {@link com.jasmine.ui.settings} views, allowing users to
 *       increase verbosity for troubleshooting without restarting</li>
 *   <li><strong>File Rotation</strong> — configurable file-handler rotation by size
 *       and count, preventing unbounded log growth on disk</li>
 *   <li><strong>Contextual Utilities</strong> — helper methods for logging elapsed
 *       time, memory deltas, and exception chains in a uniform style</li>
 * </ul>
 *
 * <p>All other packages obtain loggers through this package's factory methods to
 * ensure consistent naming conventions and handler configuration.
 *
 * @since 1.0
 */
package com.jasmine.logging;
