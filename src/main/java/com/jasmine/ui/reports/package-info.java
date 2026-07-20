/**
 * Report viewing and export views for JASMINE.
 *
 * <p>This package contains the JavaFX views that allow users to preview, configure,
 * and export generated reports. It acts as the presentation front-end for the
 * {@link com.jasmine.report} generation engine. Key views include:
 *
 * <ul>
 *   <li><strong>Report Browser</strong> — a list of previously generated reports
 *       with metadata (title, creation date, format, file size) and quick-open
 *       actions</li>
 *   <li><strong>Report Preview</strong> — an in-application preview pane rendering
 *       HTML reports via {@link javafx.scene.web.WebView} or displaying PDF
 *       summaries, allowing users to review content before exporting</li>
 *   <li><strong>Export Configuration</strong> — a dialog for selecting output
 *       format (PDF, HTML, plain text), choosing which sections to include
 *       (summary, charts, raw data, recommendations), and specifying the
 *       destination file path</li>
 *   <li><strong>Report Generation Progress</strong> — a progress indicator shown
 *       during report rendering, with cancel support for long-running exports</li>
 * </ul>
 *
 * <p>Views in this package communicate with the service layer for data retrieval
 * and delegate rendering to the {@link com.jasmine.report} package.
 *
 * @since 1.0
 */
package com.jasmine.ui.reports;
