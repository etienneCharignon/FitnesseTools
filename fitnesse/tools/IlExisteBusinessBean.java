package fitnesse.tools;

import fr.....dao.DataAccessObject;
import fr.....domain.IdObject;

public abstract class IlExisteBusinessBean<B extends IdObject> extends TransactionalDecisionTable {
	private final ContextMapManager mapManager = new ContextMapManager();

	public abstract String getMapKey();

	protected abstract IdObject createBean() throws Exception;

	@Override
	public void executeInTransation(DataAccessObject dao) throws Exception {

		@SuppressWarnings("unchecked")
		Class<IdObject> beanClass = (Class<IdObject>) ReflexionUtils.getBeanClass(getClass());

		String mapKey = getMapKey();
		if (mapKey == null || !PageContext.existInContextMap(beanClass, mapKey)) {
			IdObject bean = createBean();
			mapManager.addToMap(beanClass, mapKey, bean);
			PageContext.addToList(beanClass, bean);
			dao.save(bean);
		}
	}
}