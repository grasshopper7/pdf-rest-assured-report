package tech.grasshopper.reporter.tests.markup;

import org.jsoup.nodes.Element;
import org.vandeseer.easytable.structure.cell.AbstractCell;

import com.aventstack.extentreports.Status;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import tech.grasshopper.reporter.annotation.FileAnnotationStore;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class RestAssuredTestMarkup extends TestMarkup {
	
	protected FileAnnotationStore fileAnnotations;

	protected AbstractCell createTableMarkup(Status status, Element element) {
		return RestAssuredTableMarkup.builder().element(element).fileAnnotations(fileAnnotations).logFont(reportFont.getRegularFont())
				.textColor(config.statusColor(status)).maxTableColumnCount(2).width(width).build().displayDetails();
	}
}
