package fitnesse.tools;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflexionUtils {

	static Type getBeanClass(Class<?> clazz) {
		// la ligne suivante récupère le type parametré sur la classe parente ou sur la classe d'encore au dessus si la
		// classe parente n'est pas parametrée
		ParameterizedType parameterizedType = (ParameterizedType) (clazz.getGenericSuperclass() instanceof ParameterizedType ? clazz
				.getGenericSuperclass() : clazz.getSuperclass().getGenericSuperclass());
		return parameterizedType.getActualTypeArguments()[0];
	}

}
