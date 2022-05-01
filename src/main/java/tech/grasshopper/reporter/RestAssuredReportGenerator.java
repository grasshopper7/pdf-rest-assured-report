package tech.grasshopper.reporter;

import java.io.File;

import com.aventstack.extentreports.model.Report;

import tech.grasshopper.reporter.annotation.FileAnnotationStore;
import tech.grasshopper.reporter.annotation.processor.FileAnnotationProcessor;
import tech.grasshopper.reporter.config.ExtentPDFReporterConfig;
import tech.grasshopper.reporter.dashboard.RestAssuredDashboard;
import tech.grasshopper.reporter.tests.RestAssuredTestDetails;

public class RestAssuredReportGenerator extends ReportGenerator {

	protected FileAnnotationStore fileAnnotations;

	public RestAssuredReportGenerator(Report report, ExtentPDFReporterConfig config, File file) {
		super(report, config, file);
		this.fileAnnotations = new FileAnnotationStore();

		// No zoomed media display required
		config.setDisplayExpandedMedia(false);
	}

	protected void createDashboardSection() {
		RestAssuredDashboard.builder().document(document).reportFont(reportFont).report(report).config(config)
				.destinations(destinations).build().createSection();
	}

	protected void createTestDetailsSection() {
		RestAssuredTestDetails.builder().document(document).report(report).reportFont(reportFont).config(config)
				.destinations(destinations).fileAnnotations(fileAnnotations).annotations(annotations)
				.pageHeader(pageHeader).build().createSection();
	}

	protected void processAnnotations() {
		super.processAnnotations();
		FileAnnotationProcessor.builder().document(document).reportFile(reportFile).annotations(fileAnnotations).build()
				.processAnnotations();
	}
}
