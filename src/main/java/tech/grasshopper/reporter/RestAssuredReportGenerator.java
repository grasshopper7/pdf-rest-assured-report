package tech.grasshopper.reporter;

import java.io.File;

import com.aventstack.extentreports.model.Report;

import tech.grasshopper.reporter.config.ExtentPDFReporterConfig;
import tech.grasshopper.reporter.dashboard.RestAssuredDashboard;

public class RestAssuredReportGenerator extends ReportGenerator {

	public RestAssuredReportGenerator(Report report, ExtentPDFReporterConfig config, File file) {
		super(report, config, file);

		// No zoomed media display required
		config.setDisplayExpandedMedia(false);
	}

	protected void createDashboardSection() {
		RestAssuredDashboard.builder().document(getDocument()).reportFont(getReportFont()).report(getReport())
				.config(getConfig()).destinations(getDestinations()).build().createSection();
	}
}
