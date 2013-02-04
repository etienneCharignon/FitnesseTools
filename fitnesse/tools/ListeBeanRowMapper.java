package fitnesse.tools;

import java.util.List;

public interface ListeBeanRowMapper<T> {

	public abstract String[] getColumnNames();

	public abstract List<Object> toRow(T aggregate);

}