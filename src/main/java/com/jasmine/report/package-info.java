/**
 * Report generation for JASMINE — PDF, HTML, and plain-text output.
 *
 * <p>This package is responsible for transforming analysed experiment and monitoring
 * data into polished, distributable reports. It implements a strategy-based design
 * where each output format is handled by a dedicated renderer, all conforming to a
 * common {@code ReportRenderer} interface. Supported formats include:
 *
 * <ul>
 *   <li><strong>PDF</strong> — publication-quality reports with embedded charts,
 *       tables, and styled typography suitable for archival and stakeholder
 *       distribution</li>
 *   <li><strong>HTML</strong> — self-contained HTML documents with inline CSS and
 *       base64-encoded chart images, ideal for browser viewing and email
 *       attachments</li>
 *   <li><strong>Plain Text</strong> — ASCII-formatted summaries for terminal output,
 *       log ingestion, or quick clipboard sharing</li>
 * </ul>
 *
 * <p>The report generation pipeline:
 *
 * <ol>
 *   <li>Services gather data and pass a populated {@code ReportData} DTO to the
 *       generator</li>
 *   <li>The generator invokes {@link com.jasmine.chart} builders to render chart
 *       images</li>
 *   <li>The selected renderer assembles the final document and writes it to disk
 *       or returns it as a byte stream</li>
 * </ol>
 *
 * @since 1.0
 */
package com.jasmine.report;
