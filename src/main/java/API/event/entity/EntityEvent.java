package API.event.entity;

import API.event.Event;
import game.Entity;

public abstract class EntityEvent extends Event {
	public final Entity entity;
	public EntityEvent(boolean isCancelable,Entity entity) {
		super(isCancelable);
		this.entity=entity;
	}
}
