package hu.lakati.ihome.hw.kodepic.net.board;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a panel with hw input and/or output ports. 
 * @author lakatosa
 *
 */
public class BoardTypes {

  private static Map<String, BoardType> typeMap = new HashMap<>();
   
  private static void addDescriptor(BoardType boardType) {
    typeMap.put(boardType.getName(), boardType);
  }

  static {

    addDescriptor(new BoardType("DIMMER1", new Port[] { 
        
        new Port(PortType.BINARY_HI, 1),
        new Port(PortType.BINARY_HI, 2), 
        new Port(PortType.BINARY_HI, 3), 
        new Port(PortType.BINARY_HI, 4),
        new Port(PortType.BINARY_HI, 5), 
        new Port(PortType.BINARY_HI, 6), 
        new Port(PortType.BINARY_HI, 7),
        new Port(PortType.BINARY_HI, 8), 
        new Port(PortType.BINARY_HI, 9), 
        new Port(PortType.BINARY_HI, 10),
        new Port(PortType.BINARY_HI, 11),
        new Port(PortType.BINARY_HI, 12), 
        
        new Port(PortType.AD, 1),
        new Port(PortType.AD, 2), 
        new Port(PortType.AD, 3), 
        new Port(PortType.AD, 4), //panel thermometer
        
        new Port(PortType.DIMMER, 1), 
        new Port(PortType.DIMMER, 2), 
        new Port(PortType.DIMMER, 3),
        new Port(PortType.DIMMER, 4), 
        new Port(PortType.DIMMER, 5), 
        new Port(PortType.DIMMER, 6),
        new Port(PortType.DIMMER, 7), 
        new Port(PortType.DIMMER, 8) }));
    

    //RELAY1
    addDescriptor(new BoardType("RELAY1", new Port[] { 

        new Port(PortType.BINARY_LO, 1),
        new Port(PortType.BINARY_LO, 2), 

        new Port(PortType.AD, 1),
        new Port(PortType.AD, 2), 
        new Port(PortType.AD, 3), //panel thermometer
        
        new Port(PortType.RELAY, 1), 
        new Port(PortType.RELAY, 2),
        new Port(PortType.RELAY, 3), 
        new Port(PortType.RELAY, 4),
        new Port(PortType.RELAY, 5), 
        new Port(PortType.RELAY, 6), 
        new Port(PortType.RELAY, 7),
        new Port(PortType.RELAY, 8), 
        new Port(PortType.RELAY, 9), 
        new Port(PortType.RELAY, 10),
        new Port(PortType.RELAY, 11), 
        new Port(PortType.RELAY, 12),
        new Port(PortType.RELAY, 13), 
        new Port(PortType.RELAY, 14),
        new Port(PortType.RELAY, 15), 
        new Port(PortType.RELAY, 16), 
        new Port(PortType.RELAY, 17),
        new Port(PortType.RELAY, 18), 
        new Port(PortType.RELAY, 19), 
        new Port(PortType.RELAY, 20),
        new Port(PortType.RELAY, 21), 
        new Port(PortType.RELAY, 22),
        new Port(PortType.RELAY, 23), 
        new Port(PortType.RELAY, 24),
        new Port(PortType.RELAY, 25), 
        new Port(PortType.RELAY, 26), 
        new Port(PortType.RELAY, 27)
      }));

  }

  public static BoardType fromTypeName(final String name) {
    return Optional.ofNullable(typeMap.get(name)).orElseThrow(() -> new IllegalArgumentException("Invalid boeard type: "+name));
  }

}