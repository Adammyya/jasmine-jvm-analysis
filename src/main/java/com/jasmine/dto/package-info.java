/**
 * Immutable Data Transfer Objects for cross-layer data exchange in JASMINE.
 *
 * <p>This package contains Java {@code record} types that serve as the primary data
 * carriers between architectural layers — controllers, services, repositories, and
 * external report generators. Using records guarantees:
 *
 * <ul>
 *   <li><strong>Immutability</strong> — once constructed, a DTO's state cannot be
 *       modified, eliminating a broad class of concurrency and aliasing bugs</li>
 *   <li><strong>Value Semantics</strong> — {@code equals}, {@code hashCode}, and
 *       {@code toString} are automatically derived from component values, making
 *       DTOs safe for use in collections and logging</li>
 *   <li><strong>Compact Syntax</strong> — record declarations minimize boilerplate
 *       while remaining fully transparent to serialization frameworks</li>
 * </ul>
 *
 * <p>DTOs in this package deliberately carry no behaviour beyond compact constructors
 * that validate preconditions. They are not annotated with persistence metadata and
 * are independent of any UI toolkit, ensuring they can be shared freely across
 * modules.
 *
 * <p>Naming convention: DTOs are suffixed with {@code Dto} (e.g.,
 * {@code BenchmarkResultDto}, {@code ExperimentSummaryDto}) to distinguish them from
 * the richer domain entities in {@link com.jasmine.model}.
 *
 * @since 1.0
 */
package com.jasmine.dto;
