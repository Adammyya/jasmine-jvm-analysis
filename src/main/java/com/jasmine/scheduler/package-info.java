/**
 * Scheduled and periodic task management for JASMINE.
 *
 * <p>This package manages time-driven operations — periodic polling, delayed
 * execution, and recurring data-collection jobs — that keep the application's
 * monitoring and analytics pipelines fed with fresh data. It wraps the
 * {@link java.util.concurrent.ScheduledExecutorService} infrastructure and provides
 * a higher-level API tailored to JASMINE's needs:
 *
 * <ul>
 *   <li><strong>Monitoring Polls</strong> — scheduling fixed-rate JMX metric
 *       collection at user-configured intervals (e.g., every 500 ms or 5 s)</li>
 *   <li><strong>Benchmark Timers</strong> — managing warm-up durations, measurement
 *       windows, and cool-down delays during benchmark execution</li>
 *   <li><strong>Periodic Persistence</strong> — batching and flushing accumulated
 *       monitoring snapshots to the database at regular intervals to balance
 *       write throughput and data freshness</li>
 *   <li><strong>Graceful Lifecycle</strong> — ensuring all scheduled tasks are
 *       cancelled and executor threads are drained during application shutdown,
 *       coordinated by the {@link com.jasmine.app} package</li>
 * </ul>
 *
 * <p>All tasks are named and logged to aid diagnostics. Uncaught exceptions within
 * scheduled tasks are captured and published to the {@link com.jasmine.event} bus
 * rather than silently swallowed by the executor framework.
 *
 * @since 1.0
 */
package com.jasmine.scheduler;
