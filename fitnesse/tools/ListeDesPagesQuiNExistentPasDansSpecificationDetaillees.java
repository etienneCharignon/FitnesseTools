package fitnesse.tools;

import java.util.List;

public class ListeDesPagesQuiNExistentPasDansSpecificationDetaillees {
	public List<List<List<String>>> query() throws Exception {
		QueryResultBuilder builder = new QueryResultBuilder("Pages");

		List<String> specPages = QualiteNonRegHelper.listSpecDetaillesPages();
		List<String> nonRegPages = QualiteNonRegHelper.listNonRegPages();

		for (String page : QualiteNonRegHelper.getDifferences(specPages, nonRegPages)) {
			builder.addRow(page);
		}
		return builder.toQuery();
	}

}
