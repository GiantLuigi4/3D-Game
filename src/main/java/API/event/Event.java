package API.event;

public abstract class Event {
	private boolean isCanceled=false;
	private final boolean isCancelable;
	
	public boolean isCanceled() {
		return isCanceled;
	}
	
	public boolean isCancelable() {
		return isCancelable;
	}
	
	public Event(boolean isCancelable) {
		this.isCancelable = isCancelable;
	}
	
	public void setCanceled(boolean value) {
		if (isCancelable) {
			isCanceled=value;
		} else {
			throw new RuntimeException(new UnsupportedOperationException("Cannot cancel noncancelable event:"+this.getClass()));
		}
	}
}
