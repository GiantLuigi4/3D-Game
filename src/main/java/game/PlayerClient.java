package game;

public class PlayerClient extends Entity {
	
	private double maxMotionY=0.9f;
	private double jumpStrength=0.15f;
	private double motionX=0;
	private double motionY=0;
	private double motionZ=0;
	
	public boolean onGround=false;
	
	@Override
	public double getX() {
		return GameInstance.camera.getTranslateX();
	}
	
	@Override
	public double getY() {
		return GameInstance.camera.getTranslateY();
	}
	
	@Override
	public double getZ() {
		return GameInstance.camera.getTranslateZ();
	}
	
	@Override
	public void setX(double x) {
		GameInstance.camera.setTranslateX(x);
	}
	
	@Override
	public void setY(double y) {
		GameInstance.camera.setTranslateY(y);
	}
	
	@Override
	public void setZ(double z) {
		GameInstance.camera.setTranslateZ(z);
	}
	
	@Override
	public double getMotionX() {
		return motionX;
	}
	
	@Override
	public double getMotionY() {
		return motionY;
	}
	
	@Override
	public double getMotionZ() {
		return motionZ;
	}
	
	@Override
	public void setMotionX(double x) {
		motionX=x;
	}
	
	@Override
	public void setMotionY(double y) {
		motionY=y;
	}
	
	@Override
	public void setMotionZ(double z) {
		motionZ=z;
	}
	
	public void push(double x,double y,double z) {
		motionX+=x;
		motionY+=y;
		motionZ+=z;
	}
	
	@Override
	public double getWidth() {
		return 0.5f;
	}
	
	@Override
	public double getHeight() {
		return 2;
	}
	
	public void jump() {
		motionY=-jumpStrength;
	}
	
	@Override
	public double getDeacceleration() {
		return this.onGround?0.3:0.99f;
	}
	
	@Override
	public double getFallAcceleration() {
		return 0.01f;
	}
	
	@Override
	public double getMaxFallSpeed() {
		return maxMotionY;
	}
}
