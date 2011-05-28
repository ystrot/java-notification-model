package sample;

import sample.models.User;
import core.ChangeListener;
import core.ModelManager;

public class App {

	public static void main(String[] args) {
		
		ModelManager mm = new ModelManager();
		
		User user = mm.newInstance(User.class);
		
		user.setName("ivan");
		user.setAge(24);
		
		System.out.println("name: "+ user.getName());
		System.out.println("age: "+ user.getAge());

		
		//Add notifications
		mm.addListener(user, new ChangeListener() {
			
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

		System.out.println("name: "+ user.getName());
		System.out.println("age: "+ user.getAge());
	}
}

