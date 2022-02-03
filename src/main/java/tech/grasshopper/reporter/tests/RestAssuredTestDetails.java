package tech.grasshopper.reporter.tests;

import java.util.List;

import com.aventstack.extentreports.model.Test;

import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import tech.grasshopper.reporter.annotation.FileAnnotationStore;

@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class RestAssuredTestDetails extends TestDetails {

	protected FileAnnotationStore fileAnnotations;

	protected void displayTestAndLogDetails(List<Test> allTests) {
		for (Test test : allTests) {

			displayTestBasicData(test);
			displayTestLogs(test);
		}
	}

	protected void displayTestLogs(Test test) {
		if (test.hasLog()) {
			RestAssuredTestLogsDisplay testLogsDisplay = RestAssuredTestLogsDisplay.builder().document(document)
					.reportFont(reportFont).config(config).test(test).annotations(annotations)
					.fileAnnotations(fileAnnotations).ylocation(yLocation).build();
			testLogsDisplay.display();

			yLocation = testLogsDisplay.getYlocation();
		}
	}
}
