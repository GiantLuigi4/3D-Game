package API.event;

import java.util.function.Consumer;

public interface IEventListener<T extends Event> extends Consumer<T> {
	String getCreatorClass();
	Class<T> getTarget();
}
