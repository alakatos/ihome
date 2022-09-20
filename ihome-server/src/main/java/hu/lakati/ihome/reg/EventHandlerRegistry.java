package hu.lakati.ihome.reg;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import hu.lakati.ihome.model.Gadget;
import hu.lakati.ihome.model.IHomeContext;

public class EventHandlerRegistry {
	private Map<String, Class<Gadget>> map = new HashMap<>();
	
	public void add(String typeName, String implClassName) throws ClassNotFoundException {
		add(typeName, (Class<Gadget>)Class.forName(implClassName));
	}
	
	public void add(String typeName, Gadget handler) {
		add(typeName, (Class<Gadget>)handler.getClass());
	}
	public void add(String typeName, Class<Gadget> implClass) {
		map.putIfAbsent(typeName, implClass);
	}
	
	Gadget newHandler(String typeName, Properties configurationProperties, IHomeContext context) throws InstantiationException, IllegalAccessException {
		if (!map.containsKey(typeName)) {
			throw new IllegalArgumentException("Type " + typeName + " not registered");
		}
		Gadget handler = map.get(typeName).newInstance();
		handler.configure(configurationProperties, context);
		return handler;
	}
}
