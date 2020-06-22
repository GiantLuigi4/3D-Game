package API.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class ListenerList<T extends Event> {
	private final ArrayList<Consumer<T>> consumers=new ArrayList<>();
	
//	private final Class<T> type;
	
	public ListenerList(/*Class<T> type*/) {
//		this.type = type;
	}
	
	public <F extends Event> void addConsumer(Consumer<F> consumer) {
		consumers.add((Consumer<T>)consumer);
	}
	
	public void post(Event e) {
		if (this.checkEvent(e))
			try {
				consumers.forEach((c)->c.accept((T)e));
			} catch (Throwable err) {
				if (!supportedTypes.containsKey(e.getClass())) {
					supportedTypes.put(e.getClass(),false);
				} else {
					supportedTypes.replace(e.getClass(),false);
				}
			}
	}
	
	private final HashMap<Class,Boolean> supportedTypes=new HashMap<>();
	
	public boolean checkEvent(Event e) {
		if (!supportedTypes.containsKey(e.getClass())) {
			boolean supported=false;
			try {
				T a=(T)e;
				supported=true;
			} catch (Throwable err) {}
			supportedTypes.put(e.getClass(),supported);
		}
		return supportedTypes.get(e.getClass());
	}
}
