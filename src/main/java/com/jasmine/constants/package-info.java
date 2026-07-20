/**
 * Application-wide constants and configuration defaults for JASMINE.
 *
 * <p>This package serves as the single source of truth for literal values, default
 * settings, and symbolic names used across the application. Centralizing constants
 * here prevents magic numbers and duplicated strings from scattering through the
 * codebase. Categories of constants include:
 *
 * <ul>
 *   <li><strong>Application Metadata</strong> — application name, version string,
 *       copyright notice, and build identifiers</li>
 *   <li><strong>Default Configuration</strong> — fallback values for polling
 *       intervals, benchmark iteration counts, heap-size thresholds, and
 *       database file paths used when no explicit configuration is provided</li>
 *   <li><strong>JVM Flag Catalogs</strong> — well-known JVM flag names and their
 *       safe default values referenced by the recommendation engine</li>
 *   <li><strong>UI Constants</strong> — default window dimensions, minimum stage
 *       sizes, animation durations, and CSS class-name strings</li>
 *   <li><strong>Format Strings</strong> — date/time patterns, number-format
 *       templates, and unit suffixes (MB, ms, %) used for display formatting</li>
 * </ul>
 *
 * <p>All fields in this package are {@code public static final} and grouped into
 * logically named classes. No instantiation or mutable state exists here.
 *
 * @since 1.0
 */
package com.jasmine.constants;
