package sample;

import sample.models.User;
import sample.models.UserImpl;
import core.ChangeListener;
import core.ModelManager;

public class Performance {

	public static void main(String[] args) {
		testCreation();
		testExecution();
	}

	private static void testExecution() {
		User user = null;
		// reflection
		user = createWithReflection();
		long ref = testExecution("Reflection", user);
		// static
		user = createWithStatic();
		long st = testExecution("Static", user);
		System.out.println(("в " + ((double)ref) / st) + " раз");
	}

	private static void testCreation() {
		// reflection
		long t1 = System.nanoTime();
		for (int i = 0; i < 100000; i++) {
			createWithReflection();
		}
		long t2 = System.nanoTime();
		System.out.println("Reflection: " + (t2 - t1) / 1000000 + "ms");
		// static
		long t3 = System.nanoTime();
		for (int i = 0; i < 100000; i++) {
			createWithStatic();
		}
		long t4 = System.nanoTime();
		System.out.println("Static: " + (t4 - t3) / 1000000 + "ms");
	}

	private static long testExecution(String method, User user) {
		long t1 = System.nanoTime();
		for (int i = 0; i < 1000000; i++) {
			user.setName("ivan");
			user.setAge(21);
			user.getName();
			user.getAge();
		}
		long t2 = System.nanoTime();
		long l = (t2 - t1) / 1000000;
		System.out.println(method + ": " + l + "ms");
		return l;
	}

	private static User createWithReflection() {
		ModelManager mm = new ModelManager();
		User user = mm.newInstance(User.class);
		mm.addListener(user, listener);
		return user;
	}

	private static User createWithStatic() {
		UserImpl user = new UserImpl();
		user.addListener(listener);
		return user;
	}

	private static ChangeListener listener = new ChangeListener() {
		public void notify(Class<?> modelClass, String propertyName,
				Object oldValue, Object newValue) {
			//System.getSecurityManager();
		}
	};

}