package tech.grasshopper.reporter.dashboard;

import java.util.Map;

import com.aventstack.extentreports.Status;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import tech.grasshopper.reporter.dashboard.chart.DashboardChartDonut;
import tech.grasshopper.reporter.dashboard.chart.DashboardChartTitle;
import tech.grasshopper.reporter.structure.Display;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class RestAssuredDashboardDonutChartDisplay extends Display {

	private static final int CHART_TITLE_X_PADDING = 10;
	private static final int CHART_TITLE_Y_LOCATION = 390;
	private static final float CHART_TITLE_FONT_SIZE = 16f;

	private static final int CHART_FIRST_X_LOCATION = 130;
	private static final int CHART_SECOND_X_LOCATION = 490;

	private static final int CHART_DIMENSION = 230;
	private static final int CHART_Y_LOCATION = 150;

	private AnalysisStrategyDisplay strategyDisplay;

	@Override
	public void display() {

		strategyDisplay = AnalysisStrategyDisplay.displaySettings(report);

		createFirstChartTitle();
		createSecondChartTitle();

		createFirstDonutChart();
		createSecondDonutChart();
	}

	private void createTitle(String title, float xLocation) {
		DashboardChartTitle.builder().content(content).font(reportFont.getItalicFont()).fontSize(CHART_TITLE_FONT_SIZE)
				.xlocation(xLocation).ylocation(CHART_TITLE_Y_LOCATION).title(title).build().display();
	}

	private void createFirstChartTitle() {
		createTitle(strategyDisplay.firstLevelText(), CHART_FIRST_X_LOCATION + CHART_TITLE_X_PADDING);
	}

	private void createSecondChartTitle() {
		createTitle(strategyDisplay.secondLevelText(), CHART_SECOND_X_LOCATION + CHART_TITLE_X_PADDING);
	}

	private void createDonutChart(float xLocation, Map<Status, Long> data) {
		DashboardChartDonut.builder().content(content).document(document).config(config).width(CHART_DIMENSION)
				.height(CHART_DIMENSION).xlocation(xLocation).ylocation(CHART_Y_LOCATION).data(data).build().display();
	}

	private void createFirstDonutChart() {
		createDonutChart(CHART_FIRST_X_LOCATION, report.getStats().getParent());
	}

	private void createSecondDonutChart() {
		createDonutChart(CHART_SECOND_X_LOCATION, report.getStats().getChild());
	}
}
