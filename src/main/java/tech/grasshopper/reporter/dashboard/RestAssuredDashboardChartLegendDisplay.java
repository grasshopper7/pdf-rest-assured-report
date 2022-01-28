package tech.grasshopper.reporter.dashboard;

import static com.aventstack.extentreports.Status.FAIL;
import static com.aventstack.extentreports.Status.INFO;
import static com.aventstack.extentreports.Status.PASS;
import static com.aventstack.extentreports.Status.SKIP;
import static com.aventstack.extentreports.Status.WARNING;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;

import com.aventstack.extentreports.Status;

import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import tech.grasshopper.reporter.dashboard.legend.DashboardChartLegend;
import tech.grasshopper.reporter.structure.Display;

@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class RestAssuredDashboardChartLegendDisplay extends Display {

	private static final int TOP_LEGEND_Y_LOCATION = 130;
	private static final int CHART_LEGEND_X_PADDING = 25;

	private static final int CHART_FIRST_X_LOCATION = 130;
	private static final int CHART_SECOND_X_LOCATION = 490;

	private static final Map<Status, Color> statusColor = new LinkedHashMap<>();

	@Override
	public void display() {
		statusColor.put(PASS, config.getPassColor());
		statusColor.put(FAIL, config.getFailColor());
		statusColor.put(SKIP, config.getSkipColor());
		statusColor.put(WARNING, config.getWarnColor());
		statusColor.put(INFO, config.getInfoColor());

		createFirstChartLegend();
		createSecondChartLegend();
	}

	private void createChartLegend(float xLocation, Map<Status, Long> data) {
		DashboardChartLegend.builder().content(content).xlocation(xLocation).ylocation(TOP_LEGEND_Y_LOCATION)
				.statusColor(statusColor).statusData(data).keyFont(reportFont.getItalicFont())
				.valueFont(reportFont.getBoldFont()).build().display();
	}

	private void createFirstChartLegend() {
		createChartLegend(CHART_FIRST_X_LOCATION + CHART_LEGEND_X_PADDING, report.getStats().getParent());
	}

	private void createSecondChartLegend() {
		createChartLegend(CHART_SECOND_X_LOCATION + CHART_LEGEND_X_PADDING, report.getStats().getChild());
	}
}
