package tech.grasshopper.reporter.tests;

import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Row.RowBuilder;
import org.vandeseer.easytable.structure.cell.AbstractCell;
import org.vandeseer.easytable.structure.cell.TextCell;

import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.model.Test;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import tech.grasshopper.pdf.structure.cell.TextLabelCell;
import tech.grasshopper.reporter.annotation.FileAnnotationStore;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class RestAssuredTestLogsDisplay extends TestLogsDisplay {

	protected FileAnnotationStore fileAnnotations;

	private static final float LOGS_DETAILS_WIDTH = 450f;

	protected float[] columnsWidth() {
		return new float[] { LOGS_STATUS_WIDTH, LOGS_DETAILS_WIDTH - (test.getLevel() * TestDetails.LEVEL_X_INDENT) };
	}

	protected void tableHeaders(RowBuilder rowBuilder) {
		rowBuilder.add(TextCell.builder().text("Status").build()).add(TextCell.builder().text("Log Details").build());
	}

	protected void createLogRows() {
		test.getLogs().forEach(l -> {
			AbstractCell detailsCell = createLogDisplayCell(l, test);

			Row row = Row.builder().padding(PADDING).font(reportFont.getRegularFont())
					.fontSize(LOGS_TABLE_CONTENT_FONT_SIZE).add(TextLabelCell.builder().text(l.getStatus().toString())
							.labelColor(config.statusColor(l.getStatus())).build())
					.add(detailsCell).build();

			tableBuilder.addRow(row);
		});
	}

	protected void logRow(RowBuilder rowBuilder, AbstractCell detailsCell, Log l) {
		rowBuilder.add(TextLabelCell.builder().text(l.getStatus().toString())
				.labelColor(config.statusColor(l.getStatus())).build()).add(detailsCell);
	}

	protected AbstractCell createLogDisplayCell(Log log, Test test) {
		return RestAssuredLogDetailsCollector.builder().config(config).reportFont(reportFont)
				.width(LOGS_DETAILS_WIDTH - (test.getLevel() * TestDetails.LEVEL_X_INDENT))
				.fileAnnotations(fileAnnotations).build().createLogDetailCell(log);
	}
}
