package hu.lakati.ihome.hw.kodepic.net;

/**
 * Always update the tPortTypes type declaration in the file: hu/alvicom/ehome/engine/commonTypes.xsd.include 
 * according to the changes of this enumeration type.
 * @author lakatosa
 *
 */
public enum PortType {
    BINARY_HI,  //230VIN
    AD,         //AD
    BINARY_LO,  //DI
    
    DIMMER,       //LAMP
    RELAY,      //RELAY
    
    DA,         //AD
    
    //IMPLEMENTED FOR TEST 
    LAMPPRG,    //NOT REAL PORT TYPES but packet types
    DIMMSTATUS; // -
    
    public boolean isBinary() {
        return this == BINARY_HI || this == BINARY_LO || this == RELAY;
    }
    
    public IOMode getIOMode() {
        switch (this) {
            case AD:
            case BINARY_HI:
            case BINARY_LO:
            case DIMMSTATUS:
                return IOMode.I;
                
            case DIMMER:
            case RELAY:
            case DA:
            case LAMPPRG:
                return IOMode.O;
                
                
            default:
                throw new IllegalStateException("I/O mode of " + this + " is not registered");
        }
    }
}