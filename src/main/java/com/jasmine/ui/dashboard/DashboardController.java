package com.jasmine.ui.dashboard;

import com.jasmine.constants.AppConstants;
import com.jasmine.model.MonitoringSnapshot;
import com.jasmine.ui.components.GlassPanel;
import com.jasmine.ui.components.OrbitalMetricNode;
import com.jasmine.ui.components.ParticleCanvas;
import com.jasmine.ui.components.RuntimeCore;
import com.jasmine.ui.theme.ThemeManager;
import com.jasmine.util.FormatUtil;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The JDS Deep Space Observatory Controller.
 * 
 * Replaces the traditional CRUD dashboard with a futuristic spatial visualization.
 */
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    private StackPane rootNode;
    
    // Core Visualization Components
    private RuntimeCore runtimeCore;
    private OrbitalMetricNode cpuNode;
    private OrbitalMetricNode memNode;
    private OrbitalMetricNode threadNode;
    private OrbitalMetricNode gcNode;

    // Header values
    private Label uptimeLabel;
    private Label pidLabel;
    
    // Previous state trackers for GC Ripple effect
    private long lastGcCount = -1;

    public DashboardController() {
        buildUi();
    }

    public Node getView() {
        return rootNode;
    }

    private void buildUi() {
        rootNode = new StackPane();
        
        // 1. Background Layer (Deep Space Volumetric Fog)
        ParticleCanvas particleCanvas = new ParticleCanvas();
        
        // 2. Foreground Layout Layer
        BorderPane layout = new BorderPane();
        
        layout.setTop(buildTopNavigation());
        layout.setLeft(buildSidebar());
        layout.setCenter(buildObservatory());
        layout.setRight(buildInsightsPanel());
        layout.setBottom(buildBottomTimeline());
        
        rootNode.getChildren().addAll(particleCanvas, layout);
    }

    private Node buildTopNavigation() {
        HBox topNav = new HBox(24);
        topNav.getStyleClass().add("toolbar");
        topNav.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label(AppConstants.APP_NAME + " // Observatory");
        title.getStyleClass().add("toolbar-title");

        Label pidDesc = new Label("TARGET PID:");
        pidDesc.getStyleClass().add("toolbar-label");
        pidLabel = new Label("--");
        pidLabel.getStyleClass().add("toolbar-value");

        Label uptimeDesc = new Label("UPTIME:");
        uptimeDesc.getStyleClass().add("toolbar-label");
        uptimeLabel = new Label("--");
        uptimeLabel.getStyleClass().add("toolbar-value");

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        // Live recording indicator
        Circle recDot = new Circle(4, ThemeManager.DANGER_RED);
        Label recLabel = new Label("REC");
        recLabel.setStyle("-fx-text-fill: #FF1744; -fx-font-weight: bold; -fx-font-size: 10px;");
        HBox recBox = new HBox(6, recDot, recLabel);
        recBox.setAlignment(Pos.CENTER);

        topNav.getChildren().addAll(
            title, 
            new Label("|"), pidDesc, pidLabel, 
            new Label("|"), uptimeDesc, uptimeLabel, 
            spacer, 
            recBox
        );
        return topNav;
    }

    private Node buildSidebar() {
        VBox sidebar = new VBox();
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(220);

        // Logo Area
        VBox header = new VBox(2);
        header.getStyleClass().add("sidebar-header");
        Label title = new Label("JASMINE");
        title.getStyleClass().add("sidebar-title");
        Label subtitle = new Label("RUNTIME INTELLIGENCE");
        subtitle.getStyleClass().add("sidebar-subtitle");
        header.getChildren().addAll(title, subtitle);

        sidebar.getChildren().add(header);
        
        // Navigation Links
        sidebar.getChildren().add(createNavSectionHeader("OBSERVABILITY"));
        sidebar.getChildren().add(createNavItem("Observatory", true));
        sidebar.getChildren().add(createNavItem("Memory Matrix", false));
        sidebar.getChildren().add(createNavItem("Thread Network", false));
        sidebar.getChildren().add(createNavItem("GC Flow", false));
        
        sidebar.getChildren().add(createNavSectionHeader("ANALYSIS"));
        sidebar.getChildren().add(createNavItem("Experiment Lab", false));
        sidebar.getChildren().add(createNavItem("Timeline Events", false));

        return sidebar;
    }

    private Node createNavSectionHeader(String text) {
        Label lbl = new Label(text);
        lbl.getStyleClass().add("sidebar-section-label");
        return lbl;
    }

    private Node createNavItem(String text, boolean active) {
        HBox box = new HBox(12);
        box.getStyleClass().add("nav-item");
        if (active) box.getStyleClass().add("nav-item-active");
        
        Label icon = new Label("►");
        icon.getStyleClass().add("nav-icon");
        
        Label lbl = new Label(text);
        box.getChildren().addAll(icon, lbl);
        return box;
    }

    private Node buildObservatory() {
        StackPane centerView = new StackPane();
        centerView.setPadding(new Insets(40));

        // Central living visualization
        runtimeCore = new RuntimeCore();

        // Orbiting metric nodes (title, radius, duration, startAngle)
        cpuNode = new OrbitalMetricNode("CPU LOAD", 200, 30, 0);
        memNode = new OrbitalMetricNode("HEAP MEMORY", 240, 40, 90);
        threadNode = new OrbitalMetricNode("LIVE THREADS", 180, 25, 180);
        gcNode = new OrbitalMetricNode("GC COLLECTIONS", 220, 35, 270);

        centerView.getChildren().addAll(runtimeCore, cpuNode, memNode, threadNode, gcNode);
        return centerView;
    }
    
    private Node buildInsightsPanel() {
        VBox rightPanel = new VBox(24);
        rightPanel.setPadding(new Insets(24));
        rightPanel.setPrefWidth(260);
        
        Label title = new Label("RUNTIME INSIGHTS");
        title.setStyle("-fx-font-family: 'Space Grotesk'; -fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-font-weight: 800;");
        
        GlassPanel healthCard = new GlassPanel(8);
        Label hTitle = new Label("HEALTH SUMMARY");
        hTitle.setStyle("-fx-font-family: 'Space Grotesk'; -fx-text-fill: #00E5FF; -fx-font-size: 11px;");
        Label hDesc = new Label("JVM operates within normal baseline parameters. No critical thermal or thread congestion detected.");
        hDesc.setStyle("-fx-text-fill: #A0AABF; -fx-wrap-text: true; -fx-line-spacing: 4px;");
        healthCard.getChildren().addAll(hTitle, hDesc);
        
        GlassPanel actionCard = new GlassPanel(8);
        Label aTitle = new Label("QUICK ACTIONS");
        aTitle.setStyle("-fx-font-family: 'Space Grotesk'; -fx-text-fill: #B388FF; -fx-font-size: 11px;");
        Label aDesc = new Label("> RUN FULL GC\n> THREAD DUMP\n> HEAP DUMP");
        aDesc.setStyle("-fx-font-family: 'JetBrains Mono'; -fx-text-fill: #FFFFFF; -fx-line-spacing: 8px;");
        actionCard.getChildren().addAll(aTitle, aDesc);
        
        rightPanel.getChildren().addAll(title, healthCard, actionCard);
        return rightPanel;
    }
    
    private Node buildBottomTimeline() {
        HBox bottom = new HBox(24);
        bottom.getStyleClass().add("status-bar");
        bottom.setAlignment(Pos.CENTER_LEFT);
        
        Label tTitle = new Label("TIMELINE:");
        tTitle.setStyle("-fx-text-fill: #00E5FF; -fx-font-weight: bold; -fx-font-size: 11px;");
        
        Label event1 = new Label("[14:02:11] Connected to JVM process successfully.");
        event1.setStyle("-fx-font-family: 'JetBrains Mono'; -fx-text-fill: #A0AABF;");
        
        bottom.getChildren().addAll(tTitle, event1);
        return bottom;
    }

    /**
     * Pushes live JMX telemetry into the Observatory rendering engine.
     */
    public void updateData(MonitoringSnapshot snapshot) {
        if (snapshot == null) return;
        
        Platform.runLater(() -> {
            // Update Headers
            pidLabel.setText(String.valueOf(snapshot.getRuntime().getPid()));
            uptimeLabel.setText(FormatUtil.formatDuration(snapshot.getRuntime().getUptimeMs()));

            // Update Nodes
            double cpu = snapshot.getCpu().getProcessCpuLoad() * 100.0;
            cpuNode.updateValue(FormatUtil.formatPercent(cpu), cpu > 80);
            
            long usedMem = snapshot.getMemory().getHeapUsed();
            long maxMem = snapshot.getMemory().getHeapMax();
            double memPct = (maxMem > 0) ? (usedMem * 100.0 / maxMem) : 0;
            memNode.updateValue(FormatUtil.formatBytes(usedMem), memPct > 85);
            
            threadNode.updateValue(String.valueOf(snapshot.getThread().getThreadCount()), false);
            gcNode.updateValue(String.valueOf(snapshot.getGc().getTotalCollectionCount()), false);

            // Update Core geometry
            runtimeCore.updateTelemetry(cpu, memPct);
            
            // Trigger visual ripple on GC event
            long currentGc = snapshot.getGc().getTotalCollectionCount();
            if (lastGcCount != -1 && currentGc > lastGcCount) {
                runtimeCore.triggerGcRipple();
            }
            lastGcCount = currentGc;
        });
    }
}
