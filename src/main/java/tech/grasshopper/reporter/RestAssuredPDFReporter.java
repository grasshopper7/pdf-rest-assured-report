package tech.grasshopper.reporter;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.aventstack.extentreports.model.Report;
import com.aventstack.extentreports.observer.ReportObserver;
import com.aventstack.extentreports.observer.entity.ReportEntity;
import com.aventstack.extentreports.reporter.ReporterConfigurable;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import tech.grasshopper.reporter.config.ExtentPDFReporterConfig;

public class RestAssuredPDFReporter extends ExtentPDFReporter
		implements ReportObserver<ReportEntity>, ReporterConfigurable {

	private static final Logger logger = Logger.getLogger(RestAssuredPDFReporter.class.getName());
	private static final String REPORTER_NAME = "rapdf";
	private static final String FILE_NAME = "ra-report.pdf";

	private Disposable disposable;
	private Report report;

	private ExtentPDFReporterConfig config = ExtentPDFReporterConfig.builder().reporter(this).build();

	public RestAssuredPDFReporter(String path) {
		super(new File(path));
	}

	public RestAssuredPDFReporter(File f) {
		super(f);
	}

	public Observer<ReportEntity> getReportObserver() {
		return new Observer<ReportEntity>() {

			public void onSubscribe(Disposable d) {
				start(d);
			}

			public void onNext(ReportEntity value) {
				flush(value);
			}

			public void onError(Throwable e) {
			}

			public void onComplete() {
			}
		};
	}

	private void start(Disposable d) {
		disposable = d;
	}

	private void flush(ReportEntity value) {
		try {
			report = value.getReport();
			final String filePath = getFileNameAsExt(FILE_NAME, new String[] { ".pdf" });

			RestAssuredReportGenerator reportGenerator = new RestAssuredReportGenerator(report, config,
					new File(filePath));
			reportGenerator.generate();
		} catch (Exception e) {
			disposable.dispose();
			logger.log(Level.SEVERE, "An exception occurred", e);
		}
	}
}
