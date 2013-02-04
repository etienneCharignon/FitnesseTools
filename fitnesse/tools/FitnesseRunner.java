package fitnesse.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fitnesse.junit.TestHelper;
import fitnesse.responders.run.TestSummary;

public class FitnesseRunner {
	private final TestHelper helper;

	public FitnesseRunner() {
		helper = new TestHelper(new File("../lcwp-fitnesse").getAbsolutePath(),
				new File("./target", "fitnesse").getAbsolutePath(), new TextPrintTestListener());
	}

	public void assertPage(String pageName, String pageType) throws Exception {
		TestSummary summary = helper.run(pageName, pageType, null);
		assertEquals("wrong", 0, summary.wrong);
		assertEquals("exceptions", 0, summary.exceptions);
		assertTrue("at least one test executed", summary.right > 0);
	}

	public void assertPages(String pageType, String... pageNames) throws Exception {
		List<TestSummary> summaries = new ArrayList<TestSummary>();
		for (String pageName : pageNames) {
			summaries.add(helper.run(pageName, pageType, null));
		}

		int wrong = 0;
		int exceptions = 0;
		int right = 0;
		for (TestSummary summary : summaries) {
			wrong += summary.wrong;
			exceptions += summary.exceptions;
			right += summary.right;
		}

		assertEquals("wrong", 0, wrong);
		assertEquals("exceptions", 0, exceptions);
		assertTrue("at least one test executed", right > 0);
	}

}