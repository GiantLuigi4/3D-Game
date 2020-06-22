package API.model.elements;

import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Material;

//https://www.youtube.com/watch?v=KzUfRqS75sI
public class Box extends ModelElement {
	javafx.scene.shape.Box box;
	
	private EventHandler<MouseEvent> mouseEntered;
	
	public Box(float width,float height,float depth) {
		box=new javafx.scene.shape.Box(width,height,depth);
	}
	
	public Box(float size) {
		this(size,size,size);
	}
	
	public void setMouseEnteredEvent(EventHandler<MouseEvent> event) {
		box.setOnMouseEntered(event);
		mouseEntered=event;
	}
	
	public EventHandler<MouseEvent> getMouseEntered() {
		return mouseEntered;
	}
	
	public void setTranslate(double x, double y, double z) {
		box.setTranslateX(x);
		box.setTranslateZ(z);
		box.setTranslateY(y);
	}
	
	public void setTranslateX(double x) {
		box.setTranslateX(x);
	}
	
	public void setTranslateY(double y) {
		box.setTranslateY(y);
	}
	
	public void setTranslateZ(double z) {
		box.setTranslateZ(z);
	}
	
	public void setRotationPivot(double x,double y,double z) {
		box.setRotationAxis(new Point3D(x,y,z));
	}
	
	public void setRotate(double value) {
		box.setRotate(value);
	}
	
	public void setMaterial(Material mat) {
		box.setMaterial(mat);
	}
	
	@Override
	public Node[] getNodes() {
		return new Node[]{box};
	}
}
