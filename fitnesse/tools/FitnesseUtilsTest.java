package fitnesse.tools;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;

import java.util.Collections;

import org.junit.Test;

public class FitnesseUtilsTest {
	public static final char UNBREAKABLE_SPACE = 0x00A0;

	@Test
	public void fitnesseToString() throws Exception {
		assertThat(FitnesseUtils.toString(Collections.<String> emptyList())).isEqualTo("");
		assertThat(FitnesseUtils.toString(asList(""))).isEqualTo("");
		assertThat(FitnesseUtils.toString(asList("1"))).isEqualTo("1");
		assertThat(FitnesseUtils.toString(asList("1", "2"))).isEqualTo("1, 2");
		assertThat(FitnesseUtils.toString(asList("2", "1"))).isEqualTo("1, 2");
	}

	@Test
	public void escapeFitnesse() throws Exception {
		assertThat(FitnesseUtils.escapeFitnesse("<")).isEqualTo("&lt;");
		assertThat(FitnesseUtils.escapeFitnesse(">")).isEqualTo("&gt;");
		assertThat(FitnesseUtils.escapeFitnesse("1" + UNBREAKABLE_SPACE + "000")).isEqualTo("1 000");
	}
}
