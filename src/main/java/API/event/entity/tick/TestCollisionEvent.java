package API.event.entity.tick;

import API.event.entity.EntityEvent;
import game.Entity;

public class TestCollisionEvent extends EntityEvent {
	public boolean collided=false;
	public boolean moddedDetection=false;
	public double collidedX=0;
	public double collidedY=0;
	public double collidedZ=0;
	public final double x;
	public final double y;
	public final double z;
	
	public TestCollisionEvent(Entity entity,boolean collided,double x,double y,double z) {
		this(entity,x,y,z);
		this.collided=collided;
	}
	public TestCollisionEvent(Entity entity,double x,double y,double z) {
		super(false,entity);
		this.x=x;
		this.y=y;
		this.z=z;
	}
}
