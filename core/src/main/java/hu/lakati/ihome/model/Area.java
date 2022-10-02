package hu.lakati.ihome.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor
@Getter
@Jacksonized @Builder
@JsonInclude(NON_NULL)
public class Area {
	String name;
	private final List<Gadget> gadgets;
	private final List<Area> areas;
}
