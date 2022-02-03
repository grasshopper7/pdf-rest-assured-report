package tech.grasshopper.reporter.dashboard;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class RestAssuredDashboard extends Dashboard {

	protected void createChartLegend(final PDPageContentStream content) {
		RestAssuredDashboardDonutChartDisplay.builder().document(document).config(config).content(content)
				.report(report).reportFont(reportFont).build().display();
	}

	protected void createChartDonut(final PDPageContentStream content) {
		RestAssuredDashboardChartLegendDisplay.builder().document(document).config(config).content(content)
				.report(report).reportFont(reportFont).build().display();
	}
}
