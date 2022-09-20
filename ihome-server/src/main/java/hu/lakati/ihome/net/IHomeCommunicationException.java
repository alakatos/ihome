package hu.lakati.ihome.net;

public class IHomeCommunicationException extends Exception {

	public IHomeCommunicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public IHomeCommunicationException(String message) {
		super(message);
	}

	public IHomeCommunicationException(Throwable cause) {
		super(cause);
	}

}
