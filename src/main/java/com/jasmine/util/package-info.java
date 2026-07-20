/**
 * General-purpose utility classes for JASMINE.
 *
 * <p>This package contains stateless helper classes that provide commonly needed
 * functionality not specific to any single domain concern. Utilities are organized
 * into focused classes, each addressing a distinct category of operation:
 *
 * <ul>
 *   <li><strong>Formatting</strong> — human-readable formatting of byte counts
 *       (e.g., {@code 1,048,576 → "1.00 MB"}), durations (e.g., {@code 123456 ns →
 *       "123.46 µs"}), percentages, and timestamps</li>
 *   <li><strong>Conversion</strong> — unit conversions between bytes, kilobytes,
 *       megabytes, and gigabytes; nanoseconds to milliseconds; and similar
 *       transforms</li>
 *   <li><strong>String Helpers</strong> — null-safe truncation, padding, slug
 *       generation for file names, and CSV/TSV escaping</li>
 *   <li><strong>Collection Utilities</strong> — partition, sliding-window, and
 *       null-safe collection operations not provided by the JDK</li>
 *   <li><strong>Platform Detection</strong> — OS identification, available-processor
 *       queries, and runtime-version parsing used by the benchmark and monitor
 *       packages</li>
 * </ul>
 *
 * <p>All classes in this package expose only {@code static} methods with no mutable
 * state, making them inherently thread-safe. They carry no dependencies on other
 * JASMINE packages and may be used freely from any layer.
 *
 * @since 1.0
 */
package com.jasmine.util;
