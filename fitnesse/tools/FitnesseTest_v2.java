package fitnesse.tools;

import static fitnesse.junit.TestHelper.PAGE_TYPE_SUITE;
import static fitnesse.junit.TestHelper.PAGE_TYPE_TEST;

import org.junit.Ignore;
import org.junit.Test;

import fitnesse.tools.FitnesseRunner;

public class FitnesseTest {

	@Test
	public void runSuite() throws Exception {
		new FitnesseRunner().assertPage("NonRegression", PAGE_TYPE_SUITE);
	}

	// Utiliser ce test pour debugger une page
	@Ignore
	@Test
	public void runTestPage() throws Exception {
		FitnesseRunner fitnesseRunner = new FitnesseRunner();
		fitnesseRunner.assertPage("SpecificationsDetaillees.ExtraireDonneesGrilleTarifaire", PAGE_TYPE_TEST);
	}

	@Test
	@Ignore
	public void randomMazeGenerator() throws Exception {
		StringBuilder maze = new StringBuilder();
		for (int row = 0; row < 10; row++) {
			for (int column = 0; column < 20; column++) {
				maze.append(Math.random() < 0.5 ? "/" : "\\");
			}
			maze.append("\n");
		}
		System.out.println(maze.toString());
	}
}
