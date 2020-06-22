package API.model.elements;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class TexturedCube extends ModelElement {
	private double scale=1;
	
	private double translateX=0;
	private double translateY=0;
	private double translateZ=0;
	
	public TexturedCube() {
		for (int x=0;x<16;x++) {
			for (int y=0;y<16;y++) {
				context1.getPixelWriter().setColor(x, y, col1);
				context2.getPixelWriter().setColor(x, y, col2);
				context3.getPixelWriter().setColor(x, y, col1);
				context4.getPixelWriter().setColor(x, y, col2);
				context5.getPixelWriter().setColor(x, y, col1);
				context6.getPixelWriter().setColor(x, y, col2);
			}
		}
	}
	
	public TexturedCube(double scale) {
		this();
		this.scale = scale;
	}
	
	public void setTranslate(double x, double y, double z) {
		translateX=(x);
		translateY=(y);
		translateZ=(z);
	}
	
	public void setTranslateX(double x) {
		translateX=(x);
	}
	
	public void setTranslateY(double y) {
		translateY=(y);
	}
	
	public void setTranslateZ(double z) {
		translateZ=(z);
	}
	
//	public void setRotationPivot(double x,double y,double z) {
//		mesh.setRotationAxis(new Point3D(x,y,z));
//	}
//
//	public void setRotate(double value) {
//		mesh.setRotate(value);
//	}
	
	private final Canvas canvas1=new Canvas(16,16);
	private final Canvas canvas2=new Canvas(16,16);
	private final Canvas canvas3=new Canvas(16,16);
	private final Canvas canvas4=new Canvas(16,16);
	private final Canvas canvas5=new Canvas(16,16);
	private final Canvas canvas6=new Canvas(16,16);
	
	private final GraphicsContext context1=canvas1.getGraphicsContext2D();
	private final GraphicsContext context2=canvas2.getGraphicsContext2D();
	private final GraphicsContext context3=canvas3.getGraphicsContext2D();
	private final GraphicsContext context4=canvas4.getGraphicsContext2D();
	private final GraphicsContext context5=canvas5.getGraphicsContext2D();
	private final GraphicsContext context6=canvas6.getGraphicsContext2D();
	
	private final Color col1=Color.color(0,1,0,1);
	private final Color col2=Color.color(0,0.5f,0,1);
	
	private final Cube reference=new Cube(1);
	
	@Override
	public Node[] getNodes() {
		canvas1.setTranslateX(translateX-8);
		canvas2.setTranslateX(translateX-8);
		canvas3.setTranslateX(translateX-8-0.5f);
		canvas4.setTranslateX(translateX-8+0.5f);
		canvas5.setTranslateX(translateX-8);
		canvas6.setTranslateX(translateX-8);
		canvas1.setTranslateY(translateY-8);
		canvas2.setTranslateY(translateY-8-0.5f);
		canvas3.setTranslateY(translateY-8);
		canvas4.setTranslateY(translateY-8);
		canvas5.setTranslateY(translateY-8);
		canvas6.setTranslateY(translateY-8+0.5f);
		canvas1.setTranslateZ(translateZ-0.5f);
		canvas2.setTranslateZ(translateZ);
		canvas3.setTranslateZ(translateZ);
		canvas4.setTranslateZ(translateZ);
		canvas5.setTranslateZ(translateZ+0.5f);
		canvas6.setTranslateZ(translateZ);
		canvas1.setScaleX(1/16f);
		canvas2.setScaleX(1/16f);
		canvas3.setScaleX(1/16f);
		canvas4.setScaleX(1/16f);
		canvas5.setScaleX(1/16f);
		canvas6.setScaleX(1/16f);
		canvas1.setScaleY(1/16f);
		canvas2.setScaleY(1/16f);
		canvas3.setScaleY(1/16f);
		canvas4.setScaleY(1/16f);
		canvas5.setScaleY(1/16f);
		canvas6.setScaleY(1/16f);
		canvas1.setScaleZ(1/16f);
		canvas2.setScaleZ(1/16f);
		canvas3.setScaleZ(1/16f);
		canvas4.setScaleZ(1/16f);
		canvas5.setScaleZ(1/16f);
		canvas6.setScaleZ(1/16f);
		canvas2.setRotationAxis(new Point3D(1,0,0));
		canvas3.setRotationAxis(new Point3D(0,1,0));
		canvas4.setRotationAxis(new Point3D(0,1,0));
		canvas6.setRotationAxis(new Point3D(1,0,0));
		canvas2.setRotate(90);
		canvas3.setRotate(90);
		canvas4.setRotate(90);
		canvas5.setRotate(90);
		canvas6.setRotate(90);
		return new Node[]{
				canvas1,
				canvas2,
				canvas3,
				canvas4,
				canvas5,
				canvas6
		};
	}
}
