package API.model.lighting;

import game.BlockPos;
import org.fxyz3d.geometry.Vector3D;
import org.fxyz3d.utils.geom.Vec3d;

public class LightNode {
	public double x;
	public double y;
	public double z;
	
	public double r;
	public double g;
	public double b;
	
	public double intensity;
	
	public LightNode(double x, double y, double z, double r, double g, double b, double intensity) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.r = r;
		this.g = g;
		this.b = b;
		this.intensity = intensity;
	}
	
	public LightNode(BlockPos pos,double r, double g, double b, double intensity) {
		this.x=pos.x;
		this.y=pos.y;
		this.z=pos.z;
		this.r = r;
		this.g = g;
		this.b = b;
		this.intensity = intensity;
	}
	
	public double getDistanceTo(Vector3D pos) {
		return pos.distance(new Vector3D(x,y,z));
	}
}
