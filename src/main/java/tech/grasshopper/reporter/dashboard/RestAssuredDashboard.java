package tech.grasshopper.reporter.dashboard;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.experimental.SuperBuilder;
import tech.grasshopper.reporter.dashboard.header.DashboardHeaderDisplay;
import tech.grasshopper.reporter.dashboard.statistics.DashboardStatisticsDisplay;

@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class RestAssuredDashboard extends Dashboard {

	@Override
	@SneakyThrows
	public void createSection() {

		createPage();

		try (final PDPageContentStream content = new PDPageContentStream(document, page, AppendMode.APPEND, true)) {

			DashboardHeaderDisplay.builder().config(config).content(content).reportFont(reportFont).build().display();

			DashboardStatisticsDisplay.builder().config(config).content(content).report(report).reportFont(reportFont)
					.build().display();

			RestAssuredDashboardDonutChartDisplay.builder().document(document).config(config).content(content)
					.report(report).reportFont(reportFont).build().display();

			RestAssuredDashboardChartLegendDisplay.builder().document(document).config(config).content(content)
					.report(report).reportFont(reportFont).build().display();
		}

		createDestination();
	}
}
