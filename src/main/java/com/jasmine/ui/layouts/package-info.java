/**
 * Layout templates and structural containers for JASMINE views.
 *
 * <p>This package defines the high-level page structures and layout scaffolding
 * into which feature-specific views are composed. Layouts handle the spatial
 * arrangement of navigation, content, and auxiliary panels without containing
 * business-specific UI elements. Available layouts include:
 *
 * <ul>
 *   <li><strong>MainLayout</strong> — the application shell comprising a sidebar
 *       navigation rail, a top header bar with breadcrumbs and global actions,
 *       and a central content area where feature views are swapped in</li>
 *   <li><strong>SplitContentLayout</strong> — a master-detail arrangement with a
 *       resizable divider, used for list-detail views such as experiment browsing
 *       and report previewing</li>
 *   <li><strong>CardGridLayout</strong> — a responsive grid container that
 *       arranges child nodes (typically {@link com.jasmine.ui.components}
 *       metric cards) in a flow-wrap pattern, adapting column count to the
 *       available window width</li>
 *   <li><strong>FormLayout</strong> — a standardized two-column label-field layout
 *       used for settings panels and configuration wizards, ensuring uniform
 *       alignment and spacing</li>
 * </ul>
 *
 * <p>Layouts are purely structural — they carry no business logic and no direct
 * dependencies on the service or data layers. They integrate with the
 * {@link com.jasmine.ui.navigation} package for content-area routing.
 *
 * @since 1.0
 */
package com.jasmine.ui.layouts;
