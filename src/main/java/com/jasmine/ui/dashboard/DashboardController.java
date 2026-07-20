package com.jasmine.ui.dashboard;

import com.jasmine.constants.AppConstants;
import com.jasmine.model.MonitoringSnapshot;
import com.jasmine.util.FormatUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for the main dashboard view.
 *
 * <p>The dashboard is the primary landing screen of JASMINE, providing a system
 * overview through placeholder metric cards. In Phase 2, these cards are backed by
 * live JMX data via the {@link #update(MonitoringSnapshot)} method.
 *
 * <p><strong>Layout Structure:</strong>
 * <pre>
 * ┌──────────┬────────────────────────────────────────────┐
 * │          │  Page Header (title + status pill)         │
 * │          ├────────────────────────────────────────────┤
 * │ Sidebar  │                                            │
 * │  (nav)   │  Dashboard Grid (metric cards)             │
 * │          │                                            │
 * │          ├────────────────────────────────────────────┤
 * │          │  Footer (version + status)                 │
 * └──────────┴────────────────────────────────────────────┘
 * </pre>
 *
 * <p><strong>Design Decision — Programmatic UI:</strong> This controller builds the
 * UI programmatically rather than using FXML. For the dashboard, which is highly
 * dynamic (cards will be data-driven), programmatic construction provides more
 * control and is easier to maintain than a declarative FXML layout. FXML may be
 * introduced for more static views (settings, dialogs) in later phases.
 *
 * @since 1.0
 */
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    /** Currently selected navigation item index. */
    private int activeNavIndex = 0;

    /** Reference to navigation item containers for active-state management. */
    private VBox navigationContainer;

    // ── Live UI Elements ──
    private final Label cpuValueLabel = new Label("0.0%");
    private final Label heapValueLabel = new Label("0 MB / 0 MB");
    private final Label threadValueLabel = new Label("0");
    private final Label gcValueLabel = new Label("0");
    private final Label uptimeValueLabel = new Label("00:00:00");
    
    // Status elements
    private HBox statusPill;
    private Label statusDot;
    private Label statusText;
    private Label footerStatusDot;
    private Label footerStatusLabel;

    /**
     * Builds and returns the complete dashboard view as a {@link BorderPane}.
     *
     * <p>The returned pane is ready to be placed directly into a {@link javafx.scene.Scene}.
     * It contains the sidebar (left), page header (top of center), metric grid
     * (center of center), and footer (bottom).
     *
     * @return the root layout node for the dashboard
     */
    public BorderPane buildView() {
        logger.debug("Building dashboard view...");

        BorderPane root = new BorderPane();

        // Left: Sidebar navigation
        root.setLeft(buildSidebar());

        // Center: Content area (header + grid + footer)
        BorderPane contentArea = new BorderPane();
        contentArea.getStyleClass().add("content-area");

        contentArea.setTop(buildPageHeader());
        contentArea.setCenter(buildDashboardGrid());
        contentArea.setBottom(buildFooter());

        root.setCenter(contentArea);

        logger.debug("Dashboard view built successfully.");
        return root;
    }

    /**
     * Updates the dashboard with a new monitoring snapshot.
     *
     * <p>This method is called by the MonitoringScheduler on the JavaFX Application Thread.
     *
     * @param snapshot the latest aggregate snapshot
     */
    public void update(MonitoringSnapshot snapshot) {
        // Status Pill
        statusPill.getStyleClass().remove("status-pill-warning");
        if (!statusPill.getStyleClass().contains("status-pill-active")) {
            statusPill.getStyleClass().add("status-pill-active");
        }
        statusText.setText("Monitoring Active");

        // Footer status
        footerStatusLabel.setText("Monitoring...");

        // CPU
        if (snapshot.cpu().available()) {
            cpuValueLabel.setText(FormatUtil.formatPercent(snapshot.cpu().processCpuPercent()));
        } else {
            cpuValueLabel.setText("N/A");
        }

        // Heap Memory
        if (snapshot.memory().available()) {
            String used = FormatUtil.formatBytes(snapshot.memory().heapUsed());
            String max = snapshot.memory().heapMax() > 0 ? FormatUtil.formatBytes(snapshot.memory().heapMax()) : "Unknown";
            heapValueLabel.setText(used + " / " + max);
        } else {
            heapValueLabel.setText("N/A");
        }

        // Threads
        if (snapshot.threads().available()) {
            threadValueLabel.setText(FormatUtil.formatCount(snapshot.threads().threadCount()));
        } else {
            threadValueLabel.setText("N/A");
        }

        // GC
        if (snapshot.gc().available()) {
            gcValueLabel.setText(FormatUtil.formatCount(snapshot.gc().totalCollections()));
        } else {
            gcValueLabel.setText("N/A");
        }

        // Uptime
        if (snapshot.runtime().available()) {
            uptimeValueLabel.setText(FormatUtil.formatDuration(snapshot.runtime().uptimeMs()));
        } else {
            uptimeValueLabel.setText("N/A");
        }
    }

    // ── Sidebar ─────────────────────────────────────────────────────────────

    /**
     * Builds the left sidebar containing the application title and navigation items.
     *
     * <p>Navigation items are grouped into sections:
     * <ul>
     *     <li><strong>Overview:</strong> Dashboard</li>
     *     <li><strong>Monitoring:</strong> Runtime, Heap, GC, Threads, CPU</li>
     *     <li><strong>Analysis:</strong> Benchmarks, Experiments, Recommendations, Reports</li>
     *     <li><strong>System:</strong> Settings</li>
     * </ul>
     *
     * @return the sidebar VBox
     */
    private VBox buildSidebar() {
        VBox sidebar = new VBox();
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(AppConstants.SIDEBAR_WIDTH);
        sidebar.setMinWidth(AppConstants.SIDEBAR_WIDTH);
        sidebar.setMaxWidth(AppConstants.SIDEBAR_WIDTH);

        // Application title
        sidebar.getChildren().add(buildSidebarHeader());

        // Navigation items wrapped in a scrollable container
        ScrollPane navScroll = new ScrollPane();
        navScroll.setFitToWidth(true);
        navScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        navScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox.setVgrow(navScroll, Priority.ALWAYS);

        navigationContainer = new VBox();
        navigationContainer.setSpacing(0);

        // Section: Overview
        navigationContainer.getChildren().add(buildSectionLabel("OVERVIEW"));
        navigationContainer.getChildren().add(buildNavItem("\u2302", "Dashboard", 0, true));

        // Section: Monitoring
        navigationContainer.getChildren().add(buildSectionLabel("MONITORING"));
        navigationContainer.getChildren().add(buildNavItem("\u26A1", "Runtime Monitor", 1, false));
        navigationContainer.getChildren().add(buildNavItem("\uD83D\uDCCA", "Heap Analyzer", 2, false));
        navigationContainer.getChildren().add(buildNavItem("\u267B", "GC Analyzer", 3, false));
        navigationContainer.getChildren().add(buildNavItem("\uD83D\uDD04", "Thread Analyzer", 4, false));
        navigationContainer.getChildren().add(buildNavItem("\u2699", "CPU Analyzer", 5, false));

        // Section: Analysis
        navigationContainer.getChildren().add(buildSectionLabel("ANALYSIS"));
        navigationContainer.getChildren().add(buildNavItem("\u23F1", "Benchmarks", 6, false));
        navigationContainer.getChildren().add(buildNavItem("\uD83E\uDDEA", "Experiments", 7, false));
        navigationContainer.getChildren().add(buildNavItem("\uD83D\uDCA1", "Recommendations", 8, false));
        navigationContainer.getChildren().add(buildNavItem("\uD83D\uDCC4", "Reports", 9, false));

        // Section: System
        navigationContainer.getChildren().add(buildSectionLabel("SYSTEM"));
        navigationContainer.getChildren().add(buildNavItem("\u2699", "Settings", 10, false));

        navScroll.setContent(navigationContainer);
        sidebar.getChildren().add(navScroll);

        // Version label at bottom of sidebar
        Label versionLabel = new Label("v" + AppConstants.APP_VERSION);
        versionLabel.getStyleClass().add("footer-label");
        versionLabel.setPadding(new Insets(12, 20, 12, 20));
        sidebar.getChildren().add(versionLabel);

        return sidebar;
    }

    /**
     * Builds the sidebar header containing the application name and subtitle.
     *
     * @return the header VBox
     */
    private VBox buildSidebarHeader() {
        VBox header = new VBox(2);
        header.getStyleClass().add("sidebar-header");

        Label title = new Label(AppConstants.APP_NAME);
        title.getStyleClass().add("sidebar-title");

        Label subtitle = new Label(AppConstants.APP_SUBTITLE);
        subtitle.getStyleClass().add("sidebar-subtitle");

        header.getChildren().addAll(title, subtitle);
        return header;
    }

    /**
     * Creates a section label for grouping navigation items.
     *
     * @param text the section label text (displayed uppercase)
     * @return the section label node
     */
    private Label buildSectionLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("sidebar-section-label");
        return label;
    }

    /**
     * Creates a single navigation item with icon, label, and click behavior.
     *
     * <p>In Phase 1, only the "Dashboard" item (index 0) is active. All other
     * items display a "Coming Soon" tooltip when hovered and show no action on click.
     *
     * @param icon     Unicode character used as the icon
     * @param text     display text for the navigation item
     * @param index    positional index for tracking active state
     * @param isActive whether this item is currently active
     * @return the navigation item HBox
     */
    private HBox buildNavItem(String icon, String text, int index, boolean isActive) {
        HBox item = new HBox(10);
        item.getStyleClass().add("nav-item");
        item.setAlignment(Pos.CENTER_LEFT);

        if (isActive) {
            item.getStyleClass().add("nav-item-active");
        }

        Label iconLabel = new Label(icon);
        iconLabel.getStyleClass().add("nav-icon");

        Label textLabel = new Label(text);

        item.getChildren().addAll(iconLabel, textLabel);

        // Click handler
        item.setOnMouseClicked(event -> handleNavClick(index));

        // Tooltip for unavailable items
        if (!isActive && index != 0) {
            Tooltip tooltip = new Tooltip(text + " — Coming in Phase 2");
            Tooltip.install(item, tooltip);
        }

        return item;
    }

    /**
     * Handles navigation item click events.
     *
     * <p>Updates the active state styling on the clicked item and removes it
     * from the previously active item. In Phase 1, only the Dashboard (index 0)
     * has content — other items will be implemented in future phases.
     *
     * @param index the index of the clicked navigation item
     */
    private void handleNavClick(int index) {
        if (index == activeNavIndex) {
            return;
        }

        logger.debug("Navigation clicked: index={}", index);

        // Update active states
        updateNavActiveState(activeNavIndex, false);
        updateNavActiveState(index, true);
        activeNavIndex = index;
    }

    /**
     * Updates the active/inactive visual state of a navigation item.
     *
     * @param index    the navigation item index
     * @param isActive whether to set active or inactive
     */
    private void updateNavActiveState(int index, boolean isActive) {
        // Navigation items are interspersed with section labels in the container.
        // We need to find the actual nav items (HBox instances).
        int navItemCount = 0;
        for (Node node : navigationContainer.getChildren()) {
            if (node instanceof HBox navItem) {
                if (navItemCount == index) {
                    if (isActive) {
                        navItem.getStyleClass().add("nav-item-active");
                    } else {
                        navItem.getStyleClass().remove("nav-item-active");
                    }
                    return;
                }
                navItemCount++;
            }
        }
    }

    // ── Page Header ─────────────────────────────────────────────────────────

    /**
     * Builds the page header bar with title, subtitle, and status indicator.
     *
     * @return the header HBox
     */
    private HBox buildPageHeader() {
        HBox header = new HBox();
        header.getStyleClass().add("page-header");
        header.setAlignment(Pos.CENTER_LEFT);

        // Left: Title and subtitle
        VBox titleGroup = new VBox(2);
        Label title = new Label("Dashboard");
        title.getStyleClass().add("page-title");

        Label subtitle = new Label("System Overview");
        subtitle.getStyleClass().add("page-subtitle");

        titleGroup.getChildren().addAll(title, subtitle);

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Right: Status pill
        statusPill = new HBox(6);
        statusPill.getStyleClass().addAll("status-pill", "status-pill-warning");
        statusPill.setAlignment(Pos.CENTER);

        statusDot = new Label("\u25CF");
        statusDot.getStyleClass().add("label");
        statusText = new Label("Monitoring Paused");
        statusText.getStyleClass().add("label");

        statusPill.getChildren().addAll(statusDot, statusText);

        header.getChildren().addAll(titleGroup, spacer, statusPill);
        return header;
    }

    // ── Dashboard Grid ──────────────────────────────────────────────────────

    /**
     * Builds the main content area containing the metric card grid.
     *
     * <p>Uses a {@link GridPane} for responsive layout — cards dynamically
     * scale to fill the available width across a fixed 4-column structure.
     *
     * @return a ScrollPane wrapping the card grid
     */
    private ScrollPane buildDashboardGrid() {
        GridPane grid = new GridPane();
        grid.getStyleClass().add("dashboard-grid");

        // Define 4 equal-width columns (25% each)
        for (int i = 0; i < 4; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(25);
            grid.getColumnConstraints().add(col);
        }

        // Row 0
        grid.add(buildMetricCard("\u26A1", "CPU Usage", cpuValueLabel,
                "Processor utilization", "metric-card-accent-blue"), 0, 0);

        grid.add(buildMetricCard("\uD83D\uDCCA", "Heap Memory", heapValueLabel,
                "Used / Maximum allocated", "metric-card-accent-green"), 1, 0);

        grid.add(buildMetricCard("\uD83D\uDD04", "Active Threads", threadValueLabel,
                "Current thread count", "metric-card-accent-blue"), 2, 0);

        grid.add(buildMetricCard("\u267B", "GC Collections", gcValueLabel,
                "Total garbage collections", "metric-card-accent-orange"), 3, 0);

        // Row 1 (Uptime spans 2 columns to balance 3 cards across 4 columns)
        VBox uptimeCard = buildMetricCard("\u23F1", "Uptime", uptimeValueLabel,
                "Application runtime", "metric-card-accent-blue");
        grid.add(uptimeCard, 0, 1, 2, 1); // col=0, row=1, colspan=2, rowspan=1

        grid.add(buildMetricCard("\uD83E\uDDEA", "Experiments", new Label("0 Completed"),
                "Run benchmark experiments", "metric-card-accent-green"), 2, 1);

        grid.add(buildMetricCard("\uD83D\uDCA1", "Recommendations", new Label("0 Available"),
                "JVM tuning suggestions", "metric-card-accent-orange"), 3, 1);

        // Wrap in ScrollPane for overflow handling
        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        return scrollPane;
    }

    /**
     * Builds a single metric card displaying a placeholder value.
     *
     * <p>Card layout:
     * <pre>
     * ┌──────────────────────────────┐
     * │  [icon]  CARD TITLE          │
     * │                              │
     * │  Large Value                 │
     * │  subtitle context            │
     * └──────────────────────────────┘
     * </pre>
     *
     * @param icon         Unicode icon character
     * @param title        card title (metric name)
     * @param valueLabel   Label instance to display the value
     * @param subtitle     context description beneath the value
     * @param accentClass  CSS class for the top accent border color
     * @return the metric card VBox
     */
    private VBox buildMetricCard(String icon, String title, Label valueLabel,
                                  String subtitle, String accentClass) {
        VBox card = new VBox();
        card.getStyleClass().addAll("metric-card", accentClass);
        
        // Remove fixed widths to allow GridPane dynamic scaling
        GridPane.setHgrow(card, Priority.ALWAYS);
        GridPane.setVgrow(card, Priority.ALWAYS);
        card.setMaxWidth(Double.MAX_VALUE);
        card.setMaxHeight(Double.MAX_VALUE);

        // Header: icon + title
        HBox header = new HBox(8);
        header.getStyleClass().add("metric-card-header");
        header.setAlignment(Pos.CENTER_LEFT);

        Label iconLabel = new Label(icon);
        iconLabel.getStyleClass().add("metric-card-icon");

        Label titleLabel = new Label(title.toUpperCase());
        titleLabel.getStyleClass().add("metric-card-title");

        header.getChildren().addAll(iconLabel, titleLabel);

        // Value
        valueLabel.getStyleClass().add("metric-card-value");

        // Subtitle
        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.getStyleClass().add("metric-card-subtitle");

        card.getChildren().addAll(header, valueLabel, subtitleLabel);
        return card;
    }

    // ── Footer ──────────────────────────────────────────────────────────────

    /**
     * Builds the footer bar with application version, runtime info, and status.
     *
     * @return the footer HBox
     */
    private HBox buildFooter() {
        HBox footer = new HBox();
        footer.getStyleClass().add("footer");
        footer.setAlignment(Pos.CENTER_LEFT);

        Label appLabel = new Label(AppConstants.APP_NAME + " v" + AppConstants.APP_VERSION);
        appLabel.getStyleClass().add("footer-label");

        // Separator dot
        Label separator = new Label("  \u2022  ");
        separator.getStyleClass().add("footer-label");

        Label runtimeLabel = new Label("Java " + System.getProperty("java.version") + "  \u2022  JavaFX 21");
        runtimeLabel.getStyleClass().add("footer-label");

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Status indicator
        footerStatusDot = new Label("\u25CF ");
        footerStatusDot.getStyleClass().add("footer-status");

        footerStatusLabel = new Label("Ready");
        footerStatusLabel.getStyleClass().add("footer-status");

        HBox statusGroup = new HBox(0, footerStatusDot, footerStatusLabel);
        statusGroup.setAlignment(Pos.CENTER_RIGHT);

        footer.getChildren().addAll(appLabel, separator, runtimeLabel, spacer, statusGroup);
        return footer;
    }
}
