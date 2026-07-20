/**
 * Application navigation system for JASMINE — sidebar and view routing.
 *
 * <p>This package implements the navigation infrastructure that controls which
 * feature view is displayed in the main content area at any given time. It
 * provides a decoupled routing mechanism so that navigation requests can
 * originate from any component (sidebar items, breadcrumb links, quick-action
 * buttons) without hard-wiring view dependencies. Key components include:
 *
 * <ul>
 *   <li><strong>NavigationManager</strong> — the central router that maps route
 *       identifiers to view factories, manages a navigation history stack for
 *       back/forward traversal, and triggers content-area transitions</li>
 *   <li><strong>Sidebar</strong> — a vertical navigation rail with icon-and-label
 *       menu items for each top-level feature area (Dashboard, Benchmarks,
 *       Experiments, Reports, Settings), highlighting the active route</li>
 *   <li><strong>Breadcrumb Bar</strong> — a horizontal breadcrumb trail reflecting
 *       the current navigation depth, enabling quick jumps to ancestor views</li>
 *   <li><strong>Route Definitions</strong> — an enumeration or registry of all
 *       navigable routes, each associated with its FXML resource and controller
 *       class</li>
 * </ul>
 *
 * <p>Navigation transitions may optionally animate (slide, fade) for visual
 * continuity. The navigation system integrates with the
 * {@link com.jasmine.ui.layouts} package to swap content within the main layout's
 * content pane.
 *
 * @since 1.0
 */
package com.jasmine.ui.navigation;
