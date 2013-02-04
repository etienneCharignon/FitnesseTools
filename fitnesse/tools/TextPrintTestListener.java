package fitnesse.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.TimeMeasurement;
import fitnesse.responders.run.CompositeExecutionLog;
import fitnesse.responders.run.ResultsListener;
import fitnesse.responders.run.TestPage;
import fitnesse.responders.run.TestSummary;
import fitnesse.responders.run.TestSystem;

public class TextPrintTestListener implements ResultsListener {
	private final List<String> wikiPageList = new ArrayList<String>();

	@Override
	public void allTestingComplete(TimeMeasurement totalTimeMeasurement) {
		System.out.println("\n--complete: " + totalTimeMeasurement.elapsedSeconds() + " seconds--");
		for (String wikiPagePath : wikiPageList) {
			System.out.println(wikiPagePath);
		}
	}

	@Override
	public void announceNumberTestsToRun(int testsToRun) {
	}

	@Override
	public void errorOccured() {
	}

	@Override
	public void newTestStarted(TestPage test, TimeMeasurement timeMeasurement) {
	}

	@Override
	public void setExecutionLogAndTrackingId(String stopResponderId, CompositeExecutionLog log) {
	}

	@Override
	public void testOutputChunk(String output) {
	}

	@Override
	public void testSystemStarted(TestSystem testSystem, String testSystemName, String testRunner) {
	}

	@Override
	public void testComplete(TestPage test, TestSummary testSummary, TimeMeasurement timeMeasurement)
			throws IOException {
		String hasError = testSummary.exceptions > 0 || testSummary.wrong > 0 ? "X" : ".";

		String wikiPage = hasError + " R:" + testSummary.right + "\tW:" + testSummary.wrong + "\tI:"
				+ testSummary.ignores + "\tE:" + testSummary.exceptions + "\t" + timeMeasurement.elapsedSeconds()
				+ " seconds\t" + test.getName();
		wikiPageList.add(wikiPage);
		System.out.println(wikiPage);
	}
}