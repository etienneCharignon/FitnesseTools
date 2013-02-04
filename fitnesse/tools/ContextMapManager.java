package fitnesse.tools;

import java.util.Map;

import fr.....domain.IdObject;

public class ContextMapManager {

	private boolean hasMap = false;

	protected void clearMap(Class<IdObject> beanClass) {
		if (hasMap) {
			PageContext.getMap(beanClass).clear();
		}
	}

	protected void addToMap(Class<IdObject> beanClass, String mapKey, IdObject bean) {
		if (mapKey == null) {
			return;
		}
		hasMap = true;
		Map<String, IdObject> map = PageContext.getMap(beanClass);
		if (!map.containsKey(mapKey)) {
			map.put(mapKey, bean);
		}
	}

}