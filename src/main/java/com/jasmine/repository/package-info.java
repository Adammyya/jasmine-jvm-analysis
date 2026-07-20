/**
 * Data access layer for JASMINE, implementing the Repository pattern.
 *
 * <p>Repositories in this package abstract all persistence concerns behind clean,
 * collection-style interfaces. The underlying storage engine is SQLite, accessed
 * through JDBC, but consumers of these repositories are fully shielded from SQL
 * dialects, connection management, and result-set mapping. Key design principles:
 *
 * <ul>
 *   <li><strong>Single Responsibility</strong> — each repository manages exactly one
 *       aggregate root (e.g., {@code BenchmarkResultRepository},
 *       {@code ExperimentRepository})</li>
 *   <li><strong>DTO Mapping</strong> — repositories accept and return immutable
 *       {@link com.jasmine.dto} records, never raw {@link java.sql.ResultSet}
 *       instances</li>
 *   <li><strong>Prepared Statements</strong> — all queries use parameterized statements
 *       to prevent SQL injection</li>
 *   <li><strong>Connection Delegation</strong> — connections are obtained from the
 *       {@link com.jasmine.database} package and are never created or closed
 *       directly by repository code</li>
 * </ul>
 *
 * <p>Repositories are consumed exclusively by the {@link com.jasmine.service} layer;
 * controllers and UI code must never depend on this package directly.
 *
 * @since 1.0
 */
package com.jasmine.repository;
