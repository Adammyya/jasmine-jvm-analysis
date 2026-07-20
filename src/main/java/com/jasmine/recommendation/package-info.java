/**
 * Rule-based recommendation engine for JVM tuning suggestions in JASMINE.
 *
 * <p>This package implements an expert-system-style engine that evaluates collected
 * monitoring and benchmark data against a curated set of tuning rules and produces
 * actionable JVM configuration recommendations. The engine operates in three phases:
 *
 * <ol>
 *   <li><strong>Fact Assembly</strong> — gathering analysed metrics from the
 *       {@link com.jasmine.analytics} package, including GC pause distributions,
 *       heap utilization trends, thread contention levels, and allocation rates</li>
 *   <li><strong>Rule Evaluation</strong> — matching assembled facts against a
 *       prioritized rule set. Rules are expressed as predicate–action pairs
 *       (e.g., "if p99 GC pause &gt; 200 ms and collector is G1, suggest tuning
 *       {@code -XX:MaxGCPauseMillis}")</li>
 *   <li><strong>Recommendation Synthesis</strong> — de-duplicating, ranking, and
 *       formatting triggered rules into user-facing recommendations with severity
 *       levels, rationale text, and suggested JVM flag values</li>
 * </ol>
 *
 * <p>The rule set is designed for extensibility: new rules can be added without
 * modifying the evaluation engine. Recommendations are returned as immutable DTOs
 * and displayed in the {@link com.jasmine.ui.dashboard} views or included in
 * generated reports.
 *
 * @since 1.0
 */
package com.jasmine.recommendation;
