package tech.grasshopper.reporter.tests.markup;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Row.RowBuilder;
import org.vandeseer.easytable.structure.cell.AbstractCell;
import org.vandeseer.easytable.structure.cell.TextCell;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import tech.grasshopper.pdf.annotation.FileAnnotation;
import tech.grasshopper.pdf.structure.cell.TextFileLinkCell;
import tech.grasshopper.reporter.annotation.FileAnnotationStore;
import tech.grasshopper.reporter.optimizer.TextSanitizer;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class RestAssuredTableMarkup extends TableMarkup {

	protected FileAnnotationStore fileAnnotations;

	protected float[] columnWidthData(int columnCount) {
		return new float[] { 75f, 345f };
	}

	protected Row createTableCellData(Elements cells) {
		RowBuilder rowBuilder = Row.builder();
		TextSanitizer textSanitizer = TextSanitizer.builder().font(logFont).build();

		for (Element cell : cells) {
			AbstractCell cellContent = null;

			try {
				Elements fileElements = cell.select("span");

				List<FileAnnotation> annotations = new ArrayList<>();
				fileElements.forEach(d -> {
					if (!d.attr("type").isEmpty() && !d.attr("data").isEmpty())
						annotations.add(FileAnnotation.builder().text(d.attr("type")).link(d.attr("data")).build());
				});

				if (!annotations.isEmpty()) {
					cellContent = TextFileLinkCell.builder().text(cell.text()).annotations(annotations)
							.textColor(textColor).build();
					fileAnnotations.addFileAnnotations(annotations);
				} else
					cellContent = TextCell.builder().text(textSanitizer.sanitizeText(cell.text())).textColor(textColor)
							.lineSpacing(MULTILINE_SPACING).build();
			} catch (Exception e) {
				cellContent = errorDisplay("Error!");
				logger.log(Level.SEVERE, "Unable to get text for cell, default to error message.");
			}

			rowBuilder.add(cellContent);
		}
		return rowBuilder.build();
	}
}
