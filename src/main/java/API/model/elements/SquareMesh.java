package API.model.elements;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class SquareMesh extends MeshView {
	public SquareMesh(float size) {
		super();
		this.setMesh(createMesh(size));
	}
	
	private TriangleMesh createMesh(float size) {
		TriangleMesh m = new TriangleMesh();
		
		float hw = size / 2,
				hh = hw,
				hd = hh;
		
		//create points
		m.getPoints().addAll(
				0, hh, hd,
				0, hh, -hd,
				0, -hh, hd,
				0, -hh, -hd,
				0, hh, hd,
				0, hh, -hd,
				0, -hh, hd,
				0, -hh, -hd
		);
		float x0 = 0.0f, x1 = 1.0f / 4.0f, x2 = 2.0f / 4.0f, x3 =  3.0f / 4.0f, x4 = 1.0f;
		float y0 = 0.0f, y1 = 1.0f /3.0f, y2 = 2.0f / 3.0f, y3 = 1.0f;
		
		
		
		m.getTexCoords().addAll(
				(x1 + getImagePadding()), (y0 + getImagePadding()), //0,1
				(x2 - getImagePadding()), (y0 + getImagePadding()), //2,3
				(x0)                    , (y1 + getImagePadding()), //4,5
				(x1 + getImagePadding()), (y1 + getImagePadding()), //6,7
				(x2 - getImagePadding()), (y1 + getImagePadding()), //8,9
				(x3),                     (y1 + getImagePadding()), //10,11
				(x4),                     (y1 + getImagePadding()),  //12,13
				(x0),                     (y2 - getImagePadding()), //14,15
				(x1 + getImagePadding()), (y2 - getImagePadding()), //16,17
				(x2 - getImagePadding()), (y2 - getImagePadding()), //18,19
				(x3),                     (y2 - getImagePadding()), //20,21
				(x4),                     (y2 - getImagePadding()), //22,23
				(x1 + getImagePadding()), (y3 - getImagePadding()), //24,25
				(x2),                     (y3 - getImagePadding())  //26,27
		
		);
		
		
		m.getFaces().addAll(
				0, 10, 2, 5, 1, 9,
				2, 5, 3, 4, 1, 9,
				
				4, 7, 5, 8, 6, 2,
				6, 2, 5, 8, 7, 3,
				
				0, 13, 1, 9, 4, 12,
				4, 12, 1, 9, 5, 8,
				
				2, 1, 6, 0, 3, 4,
				3, 4, 6, 0, 7, 3,
				
				0, 10, 4, 11, 2, 5,
				2, 5, 4, 11, 6, 6,
				
				1, 9, 3, 4, 5, 8,
				5, 8, 3, 4, 7, 3
		);
		
		return m;
	}
	
	public SquareMesh(double x1,double y1,double x2,double y2,double x3,double y3,double x4,double y4) {
		super();
		this.setMesh(createMesh(x1,y1,x2,y2,x3,y3,x4,y4));
	}
	
	public SquareMesh(double x1,double y1,double z1,double x2,double y2,double z2,double x3,double y3,double z3,double x4,double y4,double z4) {
		super();
		this.setMesh(createMesh(x1,y1,z1,x2,y2,z2,x3,y3,z3,x4,y4,z4));
	}
	
	private TriangleMesh createMesh(double x1,double y1,double z1,double x2,double y2,double z2,double x3,double y3,double z3,double x4,double y4,double z4) {
		TriangleMesh m = new TriangleMesh();
		
		//create points
		m.getPoints().addAll(
				(float)z1, (float)x1, (float)y1,
				(float)z2, (float)x2, (float)y2,
				(float)z3, (float)x3, (float)y3,
				(float)z4, (float)x4, (float)y4,
				(float)z1, (float)x1, (float)y1,
				(float)z2, (float)x2, (float)y2,
				(float)z3, (float)x3, (float)y3,
				(float)z4, (float)x4, (float)y4
		);
		float x0 = 0.0f, x11 = 1.0f / 4.0f, x22 = 2.0f / 4.0f, x33 =  3.0f / 4.0f, x44 = 1.0f;
		float y0 = 0.0f, y11 = 1.0f /3.0f, y22 = 2.0f / 3.0f, y33 = 1.0f;
		
		
		
		m.getTexCoords().addAll(
				(x11 + getImagePadding()), (y0 + getImagePadding()), //0,1
				(x22 - getImagePadding()), (y0 + getImagePadding()), //2,3
				(x0)                    , (y11 + getImagePadding()), //4,5
				(x11 + getImagePadding()), (y11 + getImagePadding()), //6,7
				(x22 - getImagePadding()), (y11 + getImagePadding()), //8,9
				(x33),                     (y11 + getImagePadding()), //10,11
				(x44),                     (y11 + getImagePadding()),  //12,13
				(x0),                     (y22 - getImagePadding()), //14,15
				(x11 + getImagePadding()), (y22 - getImagePadding()), //16,17
				(x22 - getImagePadding()), (y22 - getImagePadding()), //18,19
				(x33),                     (y22 - getImagePadding()), //20,21
				(x44),                     (y22 - getImagePadding()), //22,23
				(x11 + getImagePadding()), (y33 - getImagePadding()), //24,25
				(x22),                     (y33 - getImagePadding())  //26,27
		
		);
		
		
		m.getFaces().addAll(
				0, 10, 2, 5, 1, 9,
				2, 5, 3, 4, 1, 9,
				
				4, 7, 5, 8, 6, 2,
				6, 2, 5, 8, 7, 3,
				
				0, 13, 1, 9, 4, 12,
				4, 12, 1, 9, 5, 8,
				
				2, 1, 6, 0, 3, 4,
				3, 4, 6, 0, 7, 3/*,
				
				0, 10, 4, 11, 2, 5,
				2, 5, 4, 11, 6, 6,
				
				1, 9, 3, 4, 5, 8,
				5, 8, 3, 4, 7, 3*/
		);
		
		return m;
	}
	
	private TriangleMesh createMesh(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		TriangleMesh m = new TriangleMesh();
		
		//create points
		m.getPoints().addAll(
				0, (float)x1, (float)y1,
				0, (float)x2, (float)y2,
				0, (float)x3, (float)y3,
				0, (float)x4, (float)y4,
				0, (float)x1, (float)y1,
				0, (float)x2, (float)y2,
				0, (float)x3, (float)y3,
				0, (float)x4, (float)y4
		);
		float x0 = 0.0f, x11 = 1.0f / 4.0f, x22 = 2.0f / 4.0f, x33 =  3.0f / 4.0f, x44 = 1.0f;
		float y0 = 0.0f, y11 = 1.0f /3.0f, y22 = 2.0f / 3.0f, y33 = 1.0f;
		
		
		
		m.getTexCoords().addAll(
				(x11 + getImagePadding()), (y0 + getImagePadding()), //0,1
				(x22 - getImagePadding()), (y0 + getImagePadding()), //2,3
				(x0)                    , (y11 + getImagePadding()), //4,5
				(x11 + getImagePadding()), (y11 + getImagePadding()), //6,7
				(x22 - getImagePadding()), (y11 + getImagePadding()), //8,9
				(x33),                     (y11 + getImagePadding()), //10,11
				(x44),                     (y11 + getImagePadding()),  //12,13
				(x0),                     (y22 - getImagePadding()), //14,15
				(x11 + getImagePadding()), (y22 - getImagePadding()), //16,17
				(x22 - getImagePadding()), (y22 - getImagePadding()), //18,19
				(x33),                     (y22 - getImagePadding()), //20,21
				(x44),                     (y22 - getImagePadding()), //22,23
				(x11 + getImagePadding()), (y33 - getImagePadding()), //24,25
				(x22),                     (y33 - getImagePadding())  //26,27

		);
		
		
		m.getFaces().addAll(
				0, 10, 2, 5, 1, 9,
				2, 5, 3, 4, 1, 9,
				
				4, 7, 5, 8, 6, 2,
				6, 2, 5, 8, 7, 3,
				
				0, 13, 1, 9, 4, 12,
				4, 12, 1, 9, 5, 8,
				
				2, 1, 6, 0, 3, 4,
				3, 4, 6, 0, 7, 3/*,
				
				0, 10, 4, 11, 2, 5,
				2, 5, 4, 11, 6, 6,
				
				1, 9, 3, 4, 5, 8,
				5, 8, 3, 4, 7, 3*/
		);
		
		return m;
	}
	
	public float getImagePadding() {
		return imagePadding.get();
	}
	
	private final FloatProperty imagePadding = new SimpleFloatProperty(0.0015f);
}
