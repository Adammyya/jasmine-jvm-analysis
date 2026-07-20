/**
 * Application-internal event bus for decoupled publish-subscribe communication
 * in JASMINE.
 *
 * <p>This package implements a lightweight, in-process event system that allows
 * components to communicate without direct references to one another. It follows
 * the Observer / Publish-Subscribe pattern and is the primary mechanism for:
 *
 * <ul>
 *   <li><strong>Monitoring Updates</strong> — the {@link com.jasmine.monitor} package
 *       publishes metric snapshots that the UI and analytics layers consume without
 *       tight coupling</li>
 *   <li><strong>Benchmark Lifecycle Events</strong> — benchmark start, progress,
 *       completion, and failure events propagated to controllers for real-time
 *       status display</li>
 *   <li><strong>Configuration Changes</strong> — settings modifications broadcast
 *       to interested listeners so that active components can adapt without
 *       restart</li>
 *   <li><strong>Error Notifications</strong> — critical failures published as events
 *       so that multiple handlers (logging, UI alerts, telemetry) can react
 *       independently</li>
 * </ul>
 *
 * <p>The event bus supports both synchronous dispatch (for same-thread UI updates)
 * and asynchronous dispatch (for background processing). Events are modelled as
 * sealed record hierarchies to enable exhaustive {@code switch} handling with
 * pattern matching.
 *
 * <p>Thread safety is guaranteed: subscriptions and publications may occur from any
 * thread, with the bus ensuring proper synchronization and, where necessary,
 * dispatching to the JavaFX Application Thread.
 *
 * @since 1.0
 */
package com.jasmine.event;
