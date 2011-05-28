package sample;

import sample.models.UserImpl;
import core.ChangeListener;

public class App2 {

	public static void main(String[] args) {
		UserImpl user = new UserImpl();
		user.setName("ivan");
		user.setAge(24);

		System.out.println("name: " + user.getName());
		System.out.println("age: " + user.getAge());

		// Add notifications
		user.addListener(new ChangeListener() {

			public void notify(Class<?> modelClass, String propertyName,
					Object oldValue, Object newValue) {

				System.out.println(String.format(
						"Changle value %s.%s from '%s' to '%s'",
						modelClass.getSimpleName(), propertyName, oldValue,
						newValue));
			}
		});

		user.setName("victor");
		user.setAge(31);

		System.out.println("name: " + user.getName());
		System.out.println("age: " + user.getAge());
	}
}