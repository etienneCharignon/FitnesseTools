package fitnesse.tools;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

public class QueryResultBuilder {

    private List<List<List<String>>> result;
    private List<String> columns;

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

    public void addRow(String... cells) {
        List<List<String>> row = new ArrayList<List<String>>();
        for (int i = 0; i < columns.size(); i++) {
            row.add(asList(columns.get(i), cells[i]));
        }
        result.add(row);
    }

    public void addRow(List<String> cells) {
        addRow(cells.toArray(new String[cells.size()]));
    }

    public List<List<List<String>>> toQuery() {
        return result;
    }
}
