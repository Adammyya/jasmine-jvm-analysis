/**
 * Database connection management, schema migration, and lifecycle for JASMINE.
 *
 * <p>This package owns the physical database infrastructure. It is the single point
 * of contact for obtaining JDBC {@link java.sql.Connection} instances and for
 * managing the SQLite database file lifecycle. Responsibilities include:
 *
 * <ul>
 *   <li><strong>Connection Pooling</strong> — maintaining a lightweight connection
 *       pool (or single-connection holder for SQLite's serialized mode) and ensuring
 *       connections are properly configured with pragmas such as
 *       {@code journal_mode=WAL} and {@code foreign_keys=ON}</li>
 *   <li><strong>Schema Migration</strong> — applying versioned DDL scripts at
 *       startup to create or upgrade tables, indexes, and views in a repeatable,
 *       idempotent fashion</li>
 *   <li><strong>Lifecycle Management</strong> — initializing the database on first
 *       launch, verifying integrity on subsequent starts, and closing all resources
 *       during application shutdown</li>
 *   <li><strong>Transaction Support</strong> — providing utilities for explicit
 *       transaction demarcation ({@code BEGIN}, {@code COMMIT}, {@code ROLLBACK})
 *       used by the service layer</li>
 * </ul>
 *
 * <p>The {@link com.jasmine.repository} layer depends on this package for connections
 * but never manages connection lifecycle itself.
 *
 * @since 1.0
 */
package com.jasmine.database;
