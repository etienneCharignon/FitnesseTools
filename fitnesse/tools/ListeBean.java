package fitnesse.tools;

import java.util.Collection;
import java.util.List;

import fr.....domain.IdObject;

public abstract class ListeBean<B extends IdObject> implements ListeBeanRowMapper<B> {

	private final ContextMapManager mapManager = new ContextMapManager();

	protected abstract Collection<B> getRows() throws Exception;

	// If not overwritten, no map is created.
	protected String getMapKey(@SuppressWarnings("unused") B bean) {
		return null;
	}

	public List<List<List<String>>> query() throws Exception {
		@SuppressWarnings("unchecked")
		Class<IdObject> beanClass = (Class<IdObject>) ReflexionUtils.getBeanClass(getClass());

		QueryResultBuilder queryBuilder = new QueryResultBuilder(getColumnNames());

		PageContext.clearList(beanClass);
		mapManager.clearMap(beanClass);
		for (B bean : getRows()) {
			mapManager.addToMap(beanClass, getMapKey(bean), bean);
			queryBuilder.addRow(toRow(bean));
			PageContext.addToList(beanClass, bean);
		}

		return queryBuilder.toQuery();
	}

}