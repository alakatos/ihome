package hu.lakati.ihome.model;

public interface IHomeContext {
	String getCurrentPath();
	Gadget findGadget(String path);
}
