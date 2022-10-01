package hu.lakati.ihome.hw.kodepic.net.board;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardType {

    private final String name;
    private final Map<String, Port> ports;
    
    public BoardType(String name, Port[] ports) {
        this(name, Arrays.asList(ports).stream().collect(Collectors.toMap(Port::getId, Function.identity())));
    }
    
}
