package fitnesse.tools;

import java.util.List;

public class ListeDesPagesQuiNeSontPasEnNonRegression {

	public List<List<List<String>>> query() throws Exception {
		QueryResultBuilder builder = new QueryResultBuilder("Pages");

		List<String> specPages = QualiteNonRegHelper.listSpecDetaillesPages();
		List<String> nonRegPages = QualiteNonRegHelper.listNonRegPages();

		for (String page : QualiteNonRegHelper.getDifferences(nonRegPages, specPages)) {
			builder.addRow("." + QualiteNonRegHelper.SPECS + "." + page);
		}

		return builder.toQuery();
	}

}
