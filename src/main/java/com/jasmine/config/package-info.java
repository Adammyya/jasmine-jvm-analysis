/**
 * Application configuration management for JASMINE.
 *
 * <p>This package centralizes all configuration concerns, including loading, parsing,
 * validating, and exposing application properties to the rest of the system. Configuration
 * sources are resolved in a layered fashion:
 *
 * <ol>
 *   <li>Built-in defaults compiled into the application</li>
 *   <li>External {@code application.properties} or YAML files on the classpath or
 *       user-specified file-system path</li>
 *   <li>System properties and environment variables, which override file-based values</li>
 * </ol>
 *
 * <p>Key responsibilities include:
 *
 * <ul>
 *   <li>Providing strongly-typed accessors for database paths, polling intervals,
 *       JMX connection parameters, and UI preferences</li>
 *   <li>Validating configuration constraints at startup so that misconfiguration
 *       fails fast with clear diagnostics</li>
 *   <li>Supporting hot-reload for a controlled subset of properties without
 *       requiring an application restart</li>
 * </ul>
 *
 * <p>Other packages depend on this package but never on its internals; configuration
 * is exposed through read-only interfaces.
 *
 * @since 1.0
 */
package com.jasmine.config;
