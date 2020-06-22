package API.utils;

import javafx.geometry.Point3D;

import java.beans.ConstructorProperties;

public class Color {
	int value;
	
	public Color(int r, int g, int b) {
		this(r, g, b, 255);
	}
	
	@ConstructorProperties({"red", "green", "blue", "alpha"})
	public Color(int r, int g, int b, int a) {
		this.value = (a & 255) << 24 | (r & 255) << 16 | (g & 255) << 8 | (b & 255) << 0;
	}
	
	public Color(int rgb) {
		this.value = -16777216 | rgb;
	}
	
	public Color(int rgba, boolean hasalpha) {
		if (hasalpha) {
			this.value = rgba;
		} else {
			this.value = -16777216 | rgba;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj.toString().equals(this.toString())&&obj.getClass()==this.getClass();
	}
	
	public String toString() {
		String var10000 = this.getClass().getName();
		return var10000 +
			"[" +
				"r=" + this.getRed() +
				",g=" + this.getGreen() +
				",b=" + this.getBlue() +
				",a=" + this.getAlpha() +
			"]";
	}
	
	public Color(float r, float g, float b) {
		this((int)((double)(r * 255.0F) + 0.5D), (int)((double)(g * 255.0F) + 0.5D), (int)((double)(b * 255.0F) + 0.5D));
	}
	
	public Color(float r, float g, float b, float a) {
		this((int)((double)(r * 255.0F) + 0.5D), (int)((double)(g * 255.0F) + 0.5D), (int)((double)(b * 255.0F) + 0.5D), (int)((double)(a * 255.0F) + 0.5D));
	}
	
	public int getRed() {
		return this.getRGB() >> 16 & 255;
	}
	
	public int getGreen() {
		return this.getRGB() >> 8 & 255;
	}
	
	public int getBlue() {
		return this.getRGB() >> 0 & 255;
	}
	
	public int getAlpha() {
		return this.getRGB() >> 24 & 255;
	}
	
	public int getRGB() {
		return this.value;
	}
	
	public Point3D toPoint3D() {
		return new Point3D(this.getRed(),this.getGreen(),this.getBlue());
	}
}
