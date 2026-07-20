/**
 * Benchmark engine for controlled JVM performance experiments in JASMINE.
 *
 * <p>This package implements the core benchmark execution framework, providing
 * infrastructure for running repeatable, isolated performance experiments against
 * the JVM. The engine supports multiple benchmark categories:
 *
 * <ul>
 *   <li><strong>Memory Allocation</strong> — measuring allocation throughput, object
 *       creation rates, and GC pressure under configurable allocation patterns</li>
 *   <li><strong>Computation</strong> — CPU-bound workloads such as sorting, hashing,
 *       and mathematical computations to measure raw throughput</li>
 *   <li><strong>Concurrency</strong> — thread contention, lock overhead, and
 *       virtual-thread scalability benchmarks</li>
 *   <li><strong>I/O</strong> — file and network I/O latency and throughput under
 *       varying buffer sizes and access patterns</li>
 * </ul>
 *
 * <p>Each benchmark execution follows a disciplined lifecycle: warm-up iterations to
 * stabilize JIT compilation, timed measurement iterations with configurable repetition
 * counts, and cool-down phases. Results are captured as immutable DTOs and persisted
 * through the {@link com.jasmine.service} layer.
 *
 * <p>The engine is designed for extensibility — new benchmark types can be added by
 * implementing a common {@code Benchmark} interface without modifying the execution
 * harness.
 *
 * @since 1.0
 */
package com.jasmine.benchmark;
