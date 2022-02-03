package tech.grasshopper.reporter.tests;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.vandeseer.easytable.structure.cell.AbstractCell;
import org.vandeseer.easytable.structure.cell.TextCell;

import com.aventstack.extentreports.model.Log;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import tech.grasshopper.reporter.annotation.FileAnnotationStore;
import tech.grasshopper.reporter.optimizer.TextSanitizer;
import tech.grasshopper.reporter.tests.markup.RestAssuredTestMarkup;
import tech.grasshopper.reporter.tests.markup.TestMarkup;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class RestAssuredLogDetailsCollector extends LogDetailsCollector {

	protected FileAnnotationStore fileAnnotations;

	public AbstractCell createLogDetailCell(Log log) {
		if (log.hasException())
			return createExceptionCell(log);

		return createDetailsMarkupCell(log);
	}

	protected AbstractCell createDetailsMarkupCell(Log log) {
		PDFont LOGS_TABLE_CONTENT_FONT = reportFont.getRegularFont();

		TextSanitizer textSanitizer = TextSanitizer.builder().font(LOGS_TABLE_CONTENT_FONT).build();

		if (TestMarkup.isMarkup(log.getDetails()))
			return RestAssuredTestMarkup.builder().log(log).test(test).reportFont(reportFont).fileAnnotations(fileAnnotations)
					.width(width - (2 * PADDING)).config(config).build().createMarkupCell();

		// This should not be triggered
		return TextCell.builder().text(textSanitizer.sanitizeText(log.getDetails())).font(LOGS_TABLE_CONTENT_FONT)
				.fontSize(LOGS_TABLE_CONTENT_FONT_SIZE).textColor(config.statusColor(log.getStatus())).build();
	}
}
