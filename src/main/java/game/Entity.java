package game;

public abstract class Entity {
	public abstract double getX();
	public abstract double getY();
	public abstract double getZ();
	public abstract void setX(double x);
	public abstract void setY(double y);
	public abstract void setZ(double z);
	public abstract double getMotionX();
	public abstract double getMotionY();
	public abstract double getMotionZ();
	public abstract void setMotionX(double x);
	public abstract void setMotionY(double y);
	public abstract void setMotionZ(double z);
	public abstract double getDeacceleration();
	public abstract double getFallAcceleration();
	public abstract double getMaxFallSpeed();
	public abstract double getWidth();
	public abstract double getHeight();
	public void travel() {
		this.setX(this.getX()+this.getMotionX());
		double velocY=this.getMotionY();
		if (this.getMotionY()>0) {
			velocY=Math.min(getMaxFallSpeed(),this.getMotionY());
		}
		this.setY(this.getY()+velocY);
		this.setZ(this.getZ()+this.getMotionZ());
		
		this.setMotionX(this.getMotionX()*this.getDeacceleration());
		this.setMotionY(this.getMotionY()+getFallAcceleration());
		this.setMotionZ(this.getMotionZ()*this.getDeacceleration());
	}
}
