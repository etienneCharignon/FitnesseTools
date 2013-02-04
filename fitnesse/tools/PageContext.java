package fitnesse.tools;

import static fitnesse.tools.FitnesseUtils.getIndex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import fr.....domain.IdObject;
import fr.....utils.CoreContext;

public class PageContext {

	private static final Class<IdObject> ANONYMOUS_SELECTED_KEY = IdObject.class;
	private static final String LIST_KEY = "List";
	public static Map<String, Object> context = new HashMap<String, Object>();
	private static Class<? extends IdObject> lastListClass;

	public void initialisation() {
		context.clear();
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("Admin", "test"));
		CoreContext.mockNow(null);
	}

	public static boolean existInContextMap(Class<?> clazz, String mapKey) {
		return getMap(clazz).containsKey(mapKey);
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(Class<T> clazz, String mapKey) {
		Object registeredObject = PageContext.get(clazz);
		if (registeredObject != null && !(registeredObject instanceof Map<?, ?>)) {
			return (T) registeredObject;
		}
		return (T) getMap(clazz).get(mapKey);
	}

	public static <T extends IdObject> void addToList(Class<T> clazz, T bean) {
		lastListClass = clazz;
		getList(clazz).add(bean);
	}

	public static <T extends IdObject> void clearList(Class<T> clazz) {
		getList(clazz).clear();
	}

	public static <T extends IdObject> T getFromList(Class<T> clazz, String position) {
		return getList(clazz).get(getIndex(position));
	}

	@SuppressWarnings("unchecked")
	public static <T extends IdObject> T getSelectedObject() {
		return (T) get(ANONYMOUS_SELECTED_KEY);
	}

	public static <T extends IdObject> void setSelectedObjectFromList(String position) {
		setSelectedObject(getFromList(lastListClass, position));
	}

	public static <T extends IdObject> void setSelectedObject(T object) {
		register(ANONYMOUS_SELECTED_KEY, object);
	}

	@SuppressWarnings("unchecked")
	public static <T> Map<String, T> getMap(Class<?> clazz) {
		Object registeredObject = get(clazz);
		if (registeredObject == null || !(registeredObject instanceof Map<?, ?>)) {
			registeredObject = new HashMap<String, T>();
			register(clazz, registeredObject);
		}
		return (Map<String, T>) registeredObject;
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(Class<T> clazz) {
		return (T) context.get(clazz.getName());
	}

	public static <T> void register(Class<?> clazz, Object object) {
		context.put(clazz.getName(), object);
	}

	public static <T> List<T> getList(Class<T> clazz) {
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) context.get(LIST_KEY + clazz.getName());
		if (list == null) {
			list = new ArrayList<T>();
			context.put(LIST_KEY + clazz.getName(), list);
		}
		return list;
	}

	// ID Helper methods
	public static <T extends IdObject> Long getId(Class<T> clazz) {
		return get(clazz).getId();
	}

	public static <T extends IdObject> Long getId(Class<T> clazz, String mapKey) {
		T object = get(clazz, mapKey);
		if (object == null) {
			return null;
		}
		return object.getId();
	}

	public static <T extends IdObject> Long getIdFromList(Class<T> clazz, String position) {
		return getFromList(clazz, position).getId();
	}

	public static Long getSelectedId() {
		return getSelectedObject().getId();
	}

}
