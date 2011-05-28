package core;

public interface ChangeListener {

	void notify(Class<?> modelClass, String propertyName, Object oldValue,
			Object newValue);

}
