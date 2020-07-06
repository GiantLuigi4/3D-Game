package API.utils;

public class MathHelper {
	public static double Lerp(double pct,double start,double stop) {
		return ((start*pct)+(stop*(1-pct)));
	}
}
