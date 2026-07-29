package com.jasmine.ui.dashboard;

import com.jasmine.constants.AppConstants;
import com.jasmine.model.MonitoringSnapshot;
import com.jasmine.util.FormatUtil;
import javafx.application.Platform;
import javafx.animation.ScaleTransition;
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
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Controller for the main dashboard view.
 *
 * <p><strong>Sprint 2 Overhaul:</strong> The dashboard has been rewritten to resemble
 * a professional engineering tool with high information density, responsive grid layouts,
 * and a compact aesthetic.
 *
 * @since 1.0
 */
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    private int activeNavIndex = 0;
    private VBox navigationContainer;

    // ── Live Header / Status Elements ──
    private Label headerTimeLabel;
    private Label headerStatusText;
    private Label footerStatusLabel;
    private Label footerCpuStatus;
    private Label footerMemStatus;
    private Label footerGcStatus;

    // ── CPU Card Labels ──
    private final Label cpuProcessLabel = new Label("0.0%");
    private final Label cpuSystemLabel = new Label("0.0%");
    private final Label cpuLogicalLabel = new Label("0");
    private final Label cpuStatusLabel = new Label("Unknown");

    // ── Heap Card Labels ──
    private final Label heapUsedLabel = new Label("0 MB");
    private final Label heapFreeLabel = new Label("0 MB");
    private final Label heapCommLabel = new Label("0 MB");
    private final Label heapMaxLabel = new Label("0 MB");
    private final Label heapPercentLabel = new Label("0%");

    // ── Thread Card Labels ──
    private final Label threadLiveLabel = new Label("0");
    private final Label threadPeakLabel = new Label("0");
    private final Label threadDaemonLabel = new Label("0");
    private final Label threadWaitLabel = new Label("0");
    private final Label threadBlockLabel = new Label("0");

    // ── GC Card Labels ──
    private final Label gcNameLabel = new Label("Unknown");
    private final Label gcCountLabel = new Label("0");
    private final Label gcTimeLabel = new Label("0 ms");
    private final Label gcLastLabel = new Label("0 ms");
    private final Label gcStatusLabel = new Label("Unknown");

    // ── Runtime Card Labels ──
    private final Label rtJvmLabel = new Label("Unknown");
    private final Label rtVendorLabel = new Label("Unknown");
    private final Label rtPidLabel = new Label("0");
    private final Label rtUptimeLabel = new Label("00:00:00");
    private final Label rtJavaHomeLabel = new Label("Unknown");

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public BorderPane buildView() {
        BorderPane root = new BorderPane();
        root.setLeft(buildSidebar());

        BorderPane contentArea = new BorderPane();
        contentArea.getStyleClass().add("content-area");

        contentArea.setTop(buildToolbar());
        contentArea.setCenter(buildDashboardGrid());
        contentArea.setBottom(buildStatusBar());

        root.setCenter(contentArea);
        return root;
    }

    public void update(MonitoringSnapshot snapshot) {
        // Update header clock
        headerTimeLabel.setText(timeFormat.format(new Date(snapshot.collectedAt())));
        headerStatusText.setText("Recording");

        // CPU Update
        if (snapshot.cpu().available()) {
            double pCpu = snapshot.cpu().processCpuPercent();
            double sCpu = snapshot.cpu().systemCpuPercent();
            cpuProcessLabel.setText(FormatUtil.formatPercent(pCpu));
            cpuSystemLabel.setText(FormatUtil.formatPercent(sCpu));
            cpuLogicalLabel.setText(String.valueOf(snapshot.cpu().availableProcessors()));
            
            if (pCpu > 80.0) {
                cpuStatusLabel.setText("HIGH");
                cpuStatusLabel.getStyleClass().setAll("card-property-value-warning");
                footerCpuStatus.setText("HIGH");
                footerCpuStatus.getStyleClass().setAll("status-bar-dot", "status-bar-dot-warning");
            } else {
                cpuStatusLabel.setText("NORMAL");
                cpuStatusLabel.getStyleClass().setAll("card-property-value-success");
                footerCpuStatus.setText("NORMAL");
                footerCpuStatus.getStyleClass().setAll("status-bar-dot", "status-bar-dot-success");
            }
        }

        // Heap Update
        if (snapshot.memory().available()) {
            long used = snapshot.memory().heapUsed();
            long max = snapshot.memory().heapMax();
            long comm = snapshot.memory().heapCommitted();
            long free = comm - used; // Free memory within the committed block

            heapUsedLabel.setText(FormatUtil.formatBytes(used));
            heapFreeLabel.setText(FormatUtil.formatBytes(free));
            heapCommLabel.setText(FormatUtil.formatBytes(comm));
            heapMaxLabel.setText(max > 0 ? FormatUtil.formatBytes(max) : "Unknown");
            
            double util = snapshot.memory().heapUtilizationPercent();
            heapPercentLabel.setText(FormatUtil.formatPercent(util));

            if (util > 85.0) {
                heapPercentLabel.getStyleClass().setAll("card-property-value-warning");
                footerMemStatus.setText("HIGH");
                footerMemStatus.getStyleClass().setAll("status-bar-dot", "status-bar-dot-warning");
            } else {
                heapPercentLabel.getStyleClass().setAll("card-property-value-success");
                footerMemStatus.setText("NORMAL");
                footerMemStatus.getStyleClass().setAll("status-bar-dot", "status-bar-dot-success");
            }
        }

        // Thread Update
        if (snapshot.threads().available()) {
            threadLiveLabel.setText(FormatUtil.formatCount(snapshot.threads().threadCount()));
            threadPeakLabel.setText(FormatUtil.formatCount(snapshot.threads().peakThreadCount()));
            threadDaemonLabel.setText(FormatUtil.formatCount(snapshot.threads().daemonThreadCount()));
            threadWaitLabel.setText(FormatUtil.formatCount(snapshot.threads().waitingCount()));
            threadBlockLabel.setText(FormatUtil.formatCount(snapshot.threads().blockedCount()));
            
            if (snapshot.threads().blockedCount() > 50) {
                threadBlockLabel.getStyleClass().setAll("card-property-value-warning");
            } else {
                threadBlockLabel.getStyleClass().setAll("card-property-value");
            }
        }

        // GC Update
        if (snapshot.gc().available()) {
            gcNameLabel.setText(snapshot.gc().collectorNames().length() > 20 
                    ? snapshot.gc().collectorNames().substring(0, 20) + "..." 
                    : snapshot.gc().collectorNames());
            gcCountLabel.setText(FormatUtil.formatCount(snapshot.gc().totalCollections()));
            gcTimeLabel.setText(snapshot.gc().totalCollectionTimeMs() + " ms");
            gcLastLabel.setText(snapshot.gc().lastCollectionDurationMs() + " ms");
            
            if (snapshot.gc().lastCollectionDurationMs() > 250) {
                gcStatusLabel.setText("LONG PAUSE");
                gcStatusLabel.getStyleClass().setAll("card-property-value-warning");
                footerGcStatus.setText("LAG");
                footerGcStatus.getStyleClass().setAll("status-bar-dot", "status-bar-dot-warning");
            } else {
                gcStatusLabel.setText("HEALTHY");
                gcStatusLabel.getStyleClass().setAll("card-property-value-success");
                footerGcStatus.setText("HEALTHY");
                footerGcStatus.getStyleClass().setAll("status-bar-dot", "status-bar-dot-success");
            }
        }

        // Runtime Update
        if (snapshot.runtime().available()) {
            rtJvmLabel.setText(snapshot.runtime().jvmName() + " (" + snapshot.runtime().jvmVersion() + ")");
            rtVendorLabel.setText(snapshot.runtime().jvmVendor());
            rtPidLabel.setText(String.valueOf(snapshot.runtime().pid()));
            rtUptimeLabel.setText(FormatUtil.formatDuration(snapshot.runtime().uptimeMs()));
            
            String javaHome = snapshot.runtime().javaHome();
            rtJavaHomeLabel.setText(javaHome.length() > 30 ? "..." + javaHome.substring(javaHome.length() - 27) : javaHome);
        }
    }

    private VBox buildSidebar() {
        VBox sidebar = new VBox();
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(AppConstants.SIDEBAR_WIDTH);
        sidebar.setMinWidth(AppConstants.SIDEBAR_WIDTH);
        sidebar.setMaxWidth(AppConstants.SIDEBAR_WIDTH);

        VBox header = new VBox(2);
        header.getStyleClass().add("sidebar-header");
        Label title = new Label(AppConstants.APP_NAME);
        title.getStyleClass().add("sidebar-title");
        Label subtitle = new Label("RUNTIME ANALYSIS");
        subtitle.getStyleClass().add("sidebar-subtitle");
        header.getChildren().addAll(title, subtitle);
        sidebar.getChildren().add(header);

        ScrollPane navScroll = new ScrollPane();
        navScroll.setFitToWidth(true);
        navScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        navScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox.setVgrow(navScroll, Priority.ALWAYS);

        navigationContainer = new VBox();
        navigationContainer.setSpacing(0);

        navigationContainer.getChildren().add(buildSectionLabel("OVERVIEW"));
        navigationContainer.getChildren().add(buildNavItem("\u2302", "Dashboard", 0, true));

        navigationContainer.getChildren().add(buildSectionLabel("MONITORING"));
        navigationContainer.getChildren().add(buildNavItem("\u26A1", "Runtime Monitor", 1, false));
        navigationContainer.getChildren().add(buildNavItem("\uD83D\uDCCA", "Heap Analyzer", 2, false));
        navigationContainer.getChildren().add(buildNavItem("\u267B", "Garbage Collector", 3, false));
        navigationContainer.getChildren().add(buildNavItem("\uD83D\uDD04", "Thread Analyzer", 4, false));

        navigationContainer.getChildren().add(buildSectionLabel("ANALYSIS"));
        navigationContainer.getChildren().add(buildNavItem("\u23F1", "Benchmark Lab", 5, false));
        navigationContainer.getChildren().add(buildNavItem("\uD83E\uDDEA", "Experiments", 6, false));
        navigationContainer.getChildren().add(buildNavItem("\uD83D\uDCC4", "Reports", 7, false));

        navigationContainer.getChildren().add(buildSectionLabel("SYSTEM"));
        navigationContainer.getChildren().add(buildNavItem("\u2699", "Settings", 8, false));

        navScroll.setContent(navigationContainer);
        sidebar.getChildren().add(navScroll);

        return sidebar;
    }

    private Label buildSectionLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("sidebar-section-label");
        return label;
    }

    private HBox buildNavItem(String icon, String text, int index, boolean isActive) {
        HBox item = new HBox(10);
        item.getStyleClass().add("nav-item");
        if (isActive) item.getStyleClass().add("nav-item-active");
        
        Label iconLabel = new Label(icon);
        iconLabel.getStyleClass().add("nav-icon");
        item.getChildren().addAll(iconLabel, new Label(text));
        
        item.setOnMouseClicked(e -> handleNavClick(index));
        if (!isActive && index != 0) Tooltip.install(item, new Tooltip("Coming in Future Sprints"));
        return item;
    }

    private void handleNavClick(int index) {
        if (index == activeNavIndex) return;
        updateNavActiveState(activeNavIndex, false);
        updateNavActiveState(index, true);
        activeNavIndex = index;
    }

    private void updateNavActiveState(int index, boolean isActive) {
        int navItemCount = 0;
        for (Node node : navigationContainer.getChildren()) {
            if (node instanceof HBox navItem) {
                if (navItemCount == index) {
                    if (isActive) navItem.getStyleClass().add("nav-item-active");
                    else navItem.getStyleClass().remove("nav-item-active");
                    return;
                }
                navItemCount++;
            }
        }
    }

    private HBox buildToolbar() {
        HBox toolbar = new HBox();
        toolbar.getStyleClass().add("toolbar");
        toolbar.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label(AppConstants.APP_FULL_NAME);
        title.getStyleClass().add("toolbar-title");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        headerStatusText = new Label("Waiting for data...");
        headerStatusText.getStyleClass().add("toolbar-value");
        
        Label sep1 = new Label(" | "); sep1.getStyleClass().add("toolbar-separator");
        
        Label osLabel = new Label(System.getProperty("os.name"));
        osLabel.getStyleClass().add("toolbar-value");
        
        Label sep2 = new Label(" | "); sep2.getStyleClass().add("toolbar-separator");
        
        headerTimeLabel = new Label("00:00:00");
        headerTimeLabel.getStyleClass().add("toolbar-value");

        toolbar.getChildren().addAll(title, spacer, headerStatusText, sep1, osLabel, sep2, headerTimeLabel);
        return toolbar;
    }

    private ScrollPane buildDashboardGrid() {
        GridPane grid = new GridPane();
        grid.getStyleClass().add("dashboard-grid");

        // 3-column layout provides excellent density for widescreen displays
        for (int i = 0; i < 3; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(33.33);
            grid.getColumnConstraints().add(col);
        }

        grid.add(buildCompactCard("\u26A1", "CPU Utilization", "metric-card-accent-blue",
                buildRow("Process CPU", cpuProcessLabel),
                buildRow("System CPU", cpuSystemLabel),
                buildRow("Logical Processors", cpuLogicalLabel),
                buildRow("CPU Status", cpuStatusLabel)), 0, 0);

        grid.add(buildCompactCard("\uD83D\uDCCA", "Heap Memory", "metric-card-accent-green",
                buildRow("Used Heap", heapUsedLabel),
                buildRow("Free Heap", heapFreeLabel),
                buildRow("Committed Heap", heapCommLabel),
                buildRow("Maximum Heap", heapMaxLabel),
                buildRow("Heap Percentage", heapPercentLabel)), 1, 0);

        grid.add(buildCompactCard("\uD83D\uDD04", "Thread Pool", "metric-card-accent-blue",
                buildRow("Live Threads", threadLiveLabel),
                buildRow("Peak Threads", threadPeakLabel),
                buildRow("Daemon Threads", threadDaemonLabel),
                buildRow("Waiting Threads", threadWaitLabel),
                buildRow("Blocked Threads", threadBlockLabel)), 2, 0);

        grid.add(buildCompactCard("\u267B", "Garbage Collection", "metric-card-accent-orange",
                buildRow("Collector Name", gcNameLabel),
                buildRow("Collection Count", gcCountLabel),
                buildRow("Collection Time", gcTimeLabel),
                buildRow("Last Collection", gcLastLabel),
                buildRow("GC Status", gcStatusLabel)), 0, 1, 2, 1);

        grid.add(buildCompactCard("\u23F1", "Runtime Information", "metric-card-accent-blue",
                buildRow("JVM Version", rtJvmLabel),
                buildRow("JVM Vendor", rtVendorLabel),
                buildRow("Process ID", rtPidLabel),
                buildRow("Uptime", rtUptimeLabel),
                buildRow("Java Home", rtJavaHomeLabel)), 2, 1);

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return scrollPane;
    }

    private VBox buildCompactCard(String icon, String title, String accentClass, Node... rows) {
        VBox card = new VBox();
        card.getStyleClass().addAll("metric-card", accentClass);
        GridPane.setHgrow(card, Priority.ALWAYS);
        GridPane.setVgrow(card, Priority.ALWAYS);
        card.setMaxWidth(Double.MAX_VALUE);
        card.setMaxHeight(Double.MAX_VALUE);

        HBox header = new HBox(6);
        header.getStyleClass().add("metric-card-header");
        header.setAlignment(Pos.CENTER_LEFT);
        Label iconLabel = new Label(icon);
        iconLabel.getStyleClass().add("metric-card-icon");
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("metric-card-title");
        header.getChildren().addAll(iconLabel, titleLabel);

        card.getChildren().add(header);
        card.getChildren().addAll(rows);

        // JDS Micro-interaction: Scale on hover
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(150), card);
        scaleIn.setToX(1.03);
        scaleIn.setToY(1.03);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(150), card);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);

        card.setOnMouseEntered(e -> {
            scaleOut.stop();
            scaleIn.playFromStart();
            card.toFront(); // Bring floating card to front
        });
        card.setOnMouseExited(e -> {
            scaleIn.stop();
            scaleOut.playFromStart();
        });

        return card;
    }

    private HBox buildRow(String key, Label valueLabel) {
        HBox row = new HBox();
        row.getStyleClass().add("card-property-row");
        row.setAlignment(Pos.CENTER_LEFT);
        
        Label keyLabel = new Label(key);
        keyLabel.getStyleClass().add("card-property-key");
        
        valueLabel.getStyleClass().add("card-property-value");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        row.getChildren().addAll(keyLabel, spacer, valueLabel);
        return row;
    }

    private HBox buildStatusBar() {
        HBox bar = new HBox();
        bar.getStyleClass().add("status-bar");
        bar.setAlignment(Pos.CENTER_LEFT);

        Label connected = new Label("Connected to Local JVM");
        connected.getStyleClass().add("status-bar-value");
        
        Label sep1 = new Label("|"); sep1.getStyleClass().add("status-bar-separator");
        
        Label refresh = new Label("Refresh: 1000ms");
        refresh.getStyleClass().add("status-bar-label");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        footerCpuStatus = new Label("WAITING"); footerCpuStatus.getStyleClass().add("status-bar-dot");
        footerMemStatus = new Label("WAITING"); footerMemStatus.getStyleClass().add("status-bar-dot");
        footerGcStatus = new Label("WAITING"); footerGcStatus.getStyleClass().add("status-bar-dot");

        bar.getChildren().addAll(
                connected, sep1, refresh, spacer,
                new Label("CPU:"), footerCpuStatus,
                new Label("MEM:"), footerMemStatus,
                new Label("GC:"), footerGcStatus,
                new Label("  Java " + System.getProperty("java.version"))
        );
        return bar;
    }
}
