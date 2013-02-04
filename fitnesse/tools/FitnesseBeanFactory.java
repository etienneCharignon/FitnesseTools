package fitnesse.tools;

import java.util.Locale;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FitnesseBeanFactory {
	private static BeanFactory beanFactory;

	@SuppressWarnings("unchecked")
	public static <T> T getFitnesseBean(String name) {
		if (beanFactory == null) {
			init();
		}
		return (T) beanFactory.getBean(name);
	}

	public static <T> T getFitnesseBean(Class<T> name) {
		if (beanFactory == null) {
			init();
		}
		return beanFactory.getBean(name);
	}

	private static void init() {
		Locale.setDefault(Locale.ENGLISH);
		String[] configLocations = { "/fitnesse-context.xml" };
		beanFactory = new ClassPathXmlApplicationContext(configLocations);
	}
}
