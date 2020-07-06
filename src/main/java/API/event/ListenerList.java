package API.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class ListenerList<T extends Event> {
	private final ArrayList<IEventListener<T>> consumers=new ArrayList<>();
	
	public final Class<T> type;
	
	public ListenerList(Class<T> type) {
		this.type = type;
	}
	
	public <F extends Event> void addConsumer(IEventListener<F> consumer) {
		if (this.checkEvent(consumer.getTarget()))
			consumers.add((IEventListener<T>)consumer);
	}
	
	public void post(Event e) {
		if (this.checkEventClass(e.getClass()))
			consumers.forEach((c)->c.accept((T)e));
	}
	
	public boolean checkEventClass(Class<? extends Event> e) {
		return e.equals(type);
	}
	
	public boolean checkEvent(Event e) {
		return
				this.type.isNestmateOf(e.getClass())||
						e.getClass().isNestmateOf(this.type)||
						e.getClass().equals(this.type);
	}
	
	public boolean checkEvent(Class<? extends Event> e) {
		return
				this.type.isNestmateOf(e.getClass())||
						e.getClass().isNestmateOf(this.type)||
						e.getClass().equals(this.type);
	}
}
