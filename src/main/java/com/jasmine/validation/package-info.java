/**
 * Input validation utilities and constraint checking for JASMINE.
 *
 * <p>This package provides a reusable validation framework that enforces data-integrity
 * constraints before values reach the service or persistence layers. It is invoked by
 * controllers (for user-supplied input) and by service methods (for programmatic
 * arguments), ensuring that invalid data is rejected early with clear, actionable
 * error messages. Key capabilities include:
 *
 * <ul>
 *   <li><strong>Null &amp; Blank Checks</strong> — guarding required fields against
 *       {@code null}, empty strings, and whitespace-only values</li>
 *   <li><strong>Range Validation</strong> — ensuring numeric parameters (iteration
 *       counts, heap sizes, polling intervals) fall within acceptable bounds</li>
 *   <li><strong>Pattern Matching</strong> — regex-based validation for structured
 *       inputs such as JVM flag syntax, file paths, and identifier formats</li>
 *   <li><strong>Cross-Field Constraints</strong> — validating relationships between
 *       multiple fields (e.g., {@code minHeap <= maxHeap})</li>
 *   <li><strong>Composable Validators</strong> — small, single-purpose validator
 *       functions that can be composed into pipelines for complex validation
 *       scenarios</li>
 * </ul>
 *
 * <p>Validation failures are reported by throwing
 * {@link com.jasmine.exception} types that carry structured violation details,
 * enabling controllers to map failures to specific UI form fields.
 *
 * @since 1.0
 */
package com.jasmine.validation;
