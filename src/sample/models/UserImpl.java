package sample.models;

import java.util.ArrayList;
import java.util.List;

import core.ChangeListener;

public class UserImpl implements User {

	public String getName() {
		return name;
	}

	public void setName(String value) {
		notify("name", this.name, value);
		this.name = value;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int value) {
		notify("age", this.age, value);
		this.age = value;
	}

	public void addListener(ChangeListener l) {
		listeners.add(l);
		listenersArray = listeners.toArray(new ChangeListener[0]);
	}

	public void removeListener(ChangeListener listener) {
		throw new UnsupportedOperationException();
	}

	private void notify(String propName, Object oldValue, Object newValue) {
		for (int i = 0; i < listenersArray.length; ++i) {
			listenersArray[i].notify(UserImpl.class, propName, oldValue,
					newValue);
		}
	}

	private String name;
	private int age;

	private final List<ChangeListener> listeners = new ArrayList<ChangeListener>();
	private ChangeListener[] listenersArray;

}