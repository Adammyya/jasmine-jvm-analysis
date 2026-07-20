/**
 * Domain entities representing the core business objects of JASMINE.
 *
 * <p>This package defines the canonical domain model — the rich, behavioural objects
 * that embody the problem domain of JVM performance analysis. Unlike the lightweight
 * {@link com.jasmine.dto} records used at layer boundaries, domain entities may carry
 * mutable state, enforce invariants, and expose domain-specific behaviour. Examples
 * of entities modelled here include:
 *
 * <ul>
 *   <li><strong>Experiment</strong> — a configured performance experiment with its
 *       parameters, lifecycle state, and associated benchmark runs</li>
 *   <li><strong>BenchmarkResult</strong> — the outcome of a single benchmark execution,
 *       including timing data, memory snapshots, and GC statistics</li>
 *   <li><strong>MonitoringSnapshot</strong> — a point-in-time capture of JVM health
 *       metrics (heap usage, thread count, CPU load, GC pauses)</li>
 *   <li><strong>Recommendation</strong> — a JVM tuning suggestion derived from
 *       analysis of collected metrics</li>
 * </ul>
 *
 * <p>Entities in this package are persistence-agnostic; they have no knowledge of SQL
 * or JDBC. The {@link com.jasmine.repository} layer is responsible for mapping between
 * these objects and their database representation.
 *
 * @since 1.0
 */
package com.jasmine.model;
