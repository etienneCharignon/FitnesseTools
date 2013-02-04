package fitnesse.tools;

import java.util.List;


public abstract class ChampValeurQueryFixture {

	protected abstract void fillChampValeurQueryResultBuilder(QueryResultBuilder builder) throws Exception;

	public List<List<List<String>>> query() throws Exception {
		QueryResultBuilder builder = new QueryResultBuilder("champ", "valeur");
		fillChampValeurQueryResultBuilder(builder);

		return builder.toQuery();
	}

}