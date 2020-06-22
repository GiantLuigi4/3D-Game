package game;

public class BlockPos {
	public final int x;
	public final int y;
	public final int z;
	
	public BlockPos(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public String toString() {
		return "BlockPos{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				'}';
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.toString().equals(obj.toString())&&obj.getClass().equals(this.getClass());
	}
	
	@Override
	public int hashCode() {
//		return x<<y<<z;
		return 0;
	}
}
