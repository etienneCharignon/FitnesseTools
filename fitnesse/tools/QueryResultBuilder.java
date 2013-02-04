package fitnesse.tools;

import static fitnesse.tools.FitnesseUtils.blankIt;
import static fitnesse.tools.FitnesseUtils.escapeFitnesse;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

public class QueryResultBuilder {

	private final List<List<List<String>>> result;
	private final List<String> columns;

	public QueryResultBuilder(String... cols) {
		this(asList(cols));
	}

	public QueryResultBuilder(List<String> cols) {
		result = new ArrayList<List<List<String>>>();
		this.columns = cols;
	}

	public void addColumns(String... cols) {
		columns.addAll(asList(cols));
	}

	public void addRow(Object... cells) {
		addRow(asList(cells));
	}

	public void addRow(List<Object> cells) {
		List<List<String>> row = new ArrayList<List<String>>();
		for (int i = 0; i < columns.size(); i++) {
			row.add(asList(columns.get(i), escapeFitnesse(blankIt(cells.get(i)))));
		}
		result.add(row);
	}

	public List<List<List<String>>> toQuery() {
		return result;
	}
}
