package hu.lakati.ihome.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Area {
	private final List<Gadget> gadgets;
	private final List<Area> areas;
}
