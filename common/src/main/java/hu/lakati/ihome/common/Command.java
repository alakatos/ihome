package hu.lakati.ihome.common;

import java.util.Date;

public interface Command {

    Date getCreateDate();
    /** The id of the target given in endpointId@deviceId format */
    String getTargetId(); 
    
}
