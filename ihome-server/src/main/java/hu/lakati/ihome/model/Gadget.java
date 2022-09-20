package hu.lakati.ihome.model;

import java.util.Properties;

public interface Gadget {
	void configure(Properties configProperties, IHomeContext ctx);
	void handle(UserEvent userEvent, IHomeContext ctx);
}
