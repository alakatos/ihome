package hu.lakati.ihome.hw.kodepic.net.protocol;

import hu.lakati.ihome.hw.common.net.ProtocolException;

public class EHomeProtocolException extends ProtocolException {

    public EHomeProtocolException(String message) {
        super(message);
    }

    public EHomeProtocolException(String message, Throwable cause) {
        super(message, cause);
    }

}
