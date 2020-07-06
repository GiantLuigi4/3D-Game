package API.event;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class EventBus {
	private final ArrayList<ListenerList<?>> listenerLists = new ArrayList<>();
	
	public <T extends Event> void addConsumer(IEventListener<T> consumer) {
		AtomicBoolean foundListener= new AtomicBoolean(false);
		listenerLists.forEach((l)->{
			if (l.type.equals(consumer.getTarget())) {
				l.addConsumer(consumer);
				foundListener.set(true);
			}
		});
		if (!foundListener.get()) {
			listenerLists.add(new ListenerList<T>(consumer.getTarget()));
			listenerLists.get(listenerLists.size()-1).addConsumer(consumer);
		}
	}
	
	public void post(Event e) {
		listenerLists.forEach((l)->{if(l.checkEvent(e))l.post(e);});
	}
}
