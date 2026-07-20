/**
 * Application settings and preferences views for JASMINE.
 *
 * <p>This package provides the JavaFX views for configuring user preferences and
 * application-wide settings. Changes made through these views are persisted via
 * the {@link com.jasmine.config} package and take effect immediately or after
 * confirmation, depending on the setting category. Sections include:
 *
 * <ul>
 *   <li><strong>General</strong> — application language, date/time format preferences,
 *       default file-export directory, and startup behaviour</li>
 *   <li><strong>Monitoring</strong> — polling interval, metric retention duration,
 *       and alert thresholds for heap, CPU, and GC pause warnings</li>
 *   <li><strong>Benchmark Defaults</strong> — default iteration counts, warm-up
 *       durations, and timeout values applied to new experiments</li>
 *   <li><strong>Database</strong> — SQLite file path, vacuum scheduling, and
 *       data-retention policies</li>
 *   <li><strong>Appearance</strong> — theme selection (light / dark / system),
 *       font-size scaling, and chart colour-palette preferences</li>
 *   <li><strong>Logging</strong> — runtime log-level adjustment, log-file location,
 *       and rotation configuration</li>
 * </ul>
 *
 * <p>Settings views use two-way data binding to reflect the current configuration
 * state and validate user input through the {@link com.jasmine.validation} package
 * before committing changes.
 *
 * @since 1.0
 */
package com.jasmine.ui.settings;
