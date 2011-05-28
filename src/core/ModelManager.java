package core;

import static java.util.Arrays.asList;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import core.internal.MethofIndo;
import core.internal.ModelInvocationHandler;

public class ModelManager {

	public <T> T newInstance(Class<T> modelClass) {
		Object instance = Proxy.newProxyInstance(modelClass.getClassLoader(),
				new Class[] { modelClass }, new ModelInvocationHandler(modelClass, getMetadata(modelClass)));
		return modelClass.cast(instance);
	}

	public void addListener(Object instance, ChangeListener listener) {
		((ModelInvocationHandler) Proxy.getInvocationHandler(instance))
			.addListener(listener);
	}

	public void removeListener(Object instance, ChangeListener listener) {
//		getListeners(instance).remove(listener);
	}

	private Collection<ChangeListener> getListeners(Object instance) {
//		return ((ModelInvocationHandler) Proxy.getInvocationHandler(instance))
//				.getListeners();
		throw new UnsupportedOperationException();
	}
	
	
	private static final Set<Method> OBJECT_METHODS = new HashSet<Method>(asList(Object.class.getMethods()));
	
	private final Map<Class<?>, Metadata> metaMap = new HashMap<Class<?>, Metadata>();
	private Metadata getMetadata(Class<?> modelClass) {
		
		Metadata metadata = metaMap.get(modelClass);
		
		if (metadata == null) {
			metadata = new Metadata();
			BeanInfo beanInfo;
			try {
				beanInfo = Introspector.getBeanInfo(modelClass);
			} catch (IntrospectionException e) {
				throw new RuntimeException(e);
			}
			for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
				Method rm = pd.getReadMethod();
				Method wr = pd.getWriteMethod();
//				metadata.readMethods.put(rm, pd);
//				metadata.writeMethods.put(wr, pd);
				metadata.methods.put(wr.getName(), new MethofIndo(1, pd.getName()));
				metadata.methods.put(rm.getName(), new MethofIndo(2, pd.getName()));
				metadata.propsCount ++;
			}
			for (Method m : OBJECT_METHODS) {
				metadata.methods.put(m.getName(), new MethofIndo(0, null));
			}
			metaMap.put(modelClass, metadata);
		}
		return metadata;
	}

	public static class Metadata {
//		public final Map<Method, PropertyDescriptor> readMethods = new HashMap<Method, PropertyDescriptor>();
//		public final Map<Method, PropertyDescriptor> writeMethods = new HashMap<Method, PropertyDescriptor>();
		
		public final Map<String, MethofIndo> methods = new HashMap<String, MethofIndo>();
		public int propsCount;
		
	}
}
