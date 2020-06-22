package API.model.elements;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class DynamicImage extends ModelElement {
	private Canvas canvas=new Canvas();
	
	public DynamicImage() {}
	
	public void setPixel(int x,int y,int r,int g,int b) {
		canvas.getGraphicsContext2D().getPixelWriter().setColor(x,y,new Color(r,g,b,255));
	}
	public void setPixel(int x,int y,int r,int g,int b,int a) {
		canvas.getGraphicsContext2D().getPixelWriter().setColor(x,y,new Color(r,g,b,a));
	}
	
	@Override
	public Node[] getNodes() {
		return new Node[]{canvas};
	}
}
