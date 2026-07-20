/**
 * Dialog windows for user interactions in JASMINE.
 *
 * <p>This package provides pre-built, consistently styled dialog windows for
 * common user-interaction patterns. Dialogs are modal by default and block input
 * to the parent stage until dismissed. They are constructed via static factory
 * methods or builder APIs for convenience. Available dialog types include:
 *
 * <ul>
 *   <li><strong>ConfirmationDialog</strong> — a yes/no or OK/cancel prompt used
 *       before destructive operations such as deleting experiments, clearing
 *       monitoring history, or overwriting existing reports</li>
 *   <li><strong>InputDialog</strong> — a single-field or multi-field dialog for
 *       capturing small amounts of user input (e.g., experiment name, export
 *       file path), with inline validation feedback</li>
 *   <li><strong>ErrorDialog</strong> — a formatted error display showing a
 *       user-friendly message, an expandable stack-trace section, and a
 *       "Copy to Clipboard" action for bug reporting</li>
 *   <li><strong>ProgressDialog</strong> — a non-closable dialog with a progress
 *       bar and status text for operations that must complete before the user
 *       can proceed (e.g., database migration, report generation)</li>
 *   <li><strong>AboutDialog</strong> — application metadata, version information,
 *       third-party license attributions, and links to documentation</li>
 * </ul>
 *
 * <p>All dialogs inherit the active theme from {@link com.jasmine.ui.themes} and
 * follow platform conventions for button ordering and keyboard navigation.
 *
 * @since 1.0
 */
package com.jasmine.ui.dialogs;
