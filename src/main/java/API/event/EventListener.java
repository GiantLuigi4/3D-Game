package API.event;

import java.util.function.Consumer;

public class EventListener<T extends Event> implements IEventListener<T> {
	private final String creatorClass;
	private final Consumer<T> consumer;
	private final Class<T> target;
	
	public EventListener(Consumer<T> consumer, Class<T> target,Class creator) {
		creatorClass=creator.toString();
		this.consumer = consumer;
		this.target = target;
	}
	
	@Override
	public String getCreatorClass() {
		return creatorClass;
	}
	
	@Override
	public Class<T> getTarget() {
		return target;
	}
	
	@Override
	public void accept(T t) {
		consumer.accept(t);
	}
}
