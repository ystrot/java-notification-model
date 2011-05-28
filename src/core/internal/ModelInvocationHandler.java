package core.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javolution.util.FastMap;
import core.ChangeListener;
import core.ModelManager.Metadata;

public class ModelInvocationHandler implements InvocationHandler {

	private final Map<String, Object> attributes;
	private final Object stub = new Object();
	
	private final List<ChangeListener> listeners = new ArrayList<ChangeListener>();
	private ChangeListener[] listenersArray;
	private final Class<?> modelClass;
	private final Metadata metadata;
	
	public ModelInvocationHandler(Class<?> modelClass, Metadata metadata) {
		attributes = new FastMap<String, Object>(metadata.propsCount);
//		attributes = new HashMap<String, Object>(metadata.propsCount);
		this.modelClass = modelClass;
		this.metadata = metadata;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		
		MethofIndo info = metadata.methods.get(method.getName());
		
		switch (info.type) {
		case 1:
			Object newValue = args[0];
			Object oldValue = attributes.put(info.name, newValue);
			for (int i=0;i<listenersArray.length;++i) {
				listenersArray[i].notify(modelClass, info.name, oldValue, newValue);
			}
			return null;
		case 2:
			return attributes.get(info.name);
		case 0:
			return method.invoke(stub, args);
		default:
			return null;
		} 
	}

	public void addListener(ChangeListener l) {
		listeners.add(l);
		listenersArray = listeners.toArray(new ChangeListener[0]);
	}
}
