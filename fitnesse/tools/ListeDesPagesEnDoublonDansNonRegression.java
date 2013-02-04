package fitnesse.tools;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListeDesPagesEnDoublonDansNonRegression {

	public List<List<List<String>>> query() throws Exception {
		QueryResultBuilder builder = new QueryResultBuilder("Pages");

		List<String> nonRegPages = QualiteNonRegHelper.listNonRegPages();

		Set<String> set = new HashSet<String>();
		for (String page : nonRegPages) {
			if (!set.add(page)) {
				builder.addRow(page);
			}
		}
		return builder.toQuery();
	}

}
