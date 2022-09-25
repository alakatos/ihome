package hu.lakati.ihome.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserEvent {
	long created;
	Object value;
}
