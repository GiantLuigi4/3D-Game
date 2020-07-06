package game;

public class Item {
	public final Block block;
	
	public Item(Block block) {
		this.block = block;
	}
	
	public Block constructBlock(BlockPos pos) {
		try {
			return block.getClass().getConstructor(BlockPos.class,String.class).newInstance(pos,block.id);
		} catch (Throwable err) {
			return null;
		}
	}
}
