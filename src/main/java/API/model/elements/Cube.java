package API.model.elements;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.shape.CullFace;
import org.fxyz3d.shapes.primitives.CubeMesh;

public class Cube extends ModelElement {
	private final CubeMesh mesh;
	public Cube() {
		mesh=new CubeMesh();
		mesh.setCullFace(CullFace.NONE);
	}
	public Cube(double Scale) {
		mesh=new CubeMesh(Scale);
		mesh.setCullFace(CullFace.NONE);
	}
	
	public void setTranslate(double x,double y,double z) {
		mesh.setTranslateX(x);
		mesh.setTranslateY(y);
		mesh.setTranslateZ(z);
	}
	
	public void setTranslateX(double x) {
		mesh.setTranslateX(x);
	}
	
	public void setTranslateY(double y) {
		mesh.setTranslateY(y);
	}
	
	public void setTranslateZ(double z) {
		mesh.setTranslateZ(z);
	}
	
	public void setRotationPivot(double x,double y,double z) {
		mesh.setRotationAxis(new Point3D(x,y,z));
	}
	
	public void setRotate(double value) {
		mesh.setRotate(value);
	}
	
	@Override
	public Node[] getNodes() {
		return new Node[]{mesh};
	}
}
