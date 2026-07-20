/**
 * Business logic layer for JASMINE, implementing the Service Layer pattern.
 *
 * <p>This package encapsulates the core application logic that sits between the
 * controller/UI tier and the data-access tier. Services are the authoritative owners
 * of business rules and orchestrate cross-cutting workflows such as:
 *
 * <ul>
 *   <li>Running and managing benchmark experiments end-to-end — parameter validation,
 *       execution coordination, result persistence, and notification</li>
 *   <li>Aggregating real-time JVM monitoring data from the {@link com.jasmine.monitor}
 *       package and feeding it to analytics and recommendation engines</li>
 *   <li>Coordinating report generation by gathering data from repositories, applying
 *       statistical transformations, and delegating formatting to the
 *       {@link com.jasmine.report} package</li>
 *   <li>Enforcing transactional boundaries and ensuring data consistency across
 *       multiple repository calls</li>
 * </ul>
 *
 * <p>Services are designed as stateless singletons with constructor-injected
 * dependencies. They accept and return {@link com.jasmine.dto} records rather than
 * mutable domain entities, preserving immutability at API boundaries. All checked
 * exceptions are translated into the typed hierarchy defined in
 * {@link com.jasmine.exception}.
 *
 * @since 1.0
 */
package com.jasmine.service;
