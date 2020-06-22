package game;

import java.util.HashMap;

public class World {
	HashMap<BlockPos,Block> blocks=new HashMap<>();
	HashMap<BlockPos,Block> needsRefresh=new HashMap<>();
	
	public void setBlock(BlockPos pos,Block block) {
		if (blocks.containsKey(pos)) {
			blocks.replace(pos,block);
		} else {
			blocks.put(pos,block);
		}
		for (int x=-1;x<=1;x++) {
			for (int y=-1;y<=1;y++) {
				for (int z=-1;z<=1;z++) {
					BlockPos pos1=new BlockPos(pos.x+x,pos.y+y,pos.z+z);
					if (blocks.containsKey(pos1)&&!pos1.equals(pos))
						needsRefresh.put(pos1,this.getBlock(pos1));
					if (pos.equals(pos1))
						needsRefresh.put(pos1,block);
				}
			}
		}
	}
	
	public void removeBlock(BlockPos pos) {
		if (blocks.containsKey(pos)) blocks.remove(pos);
		for (int x=-1;x<=1;x++) {
			for (int y=-1;y<=1;y++) {
				for (int z=-1;z<=1;z++) {
					BlockPos pos1=new BlockPos(pos.x+x,pos.y+y,pos.z+z);
					if (blocks.containsKey(pos1)&&!pos1.equals(pos))
						needsRefresh.put(pos1,this.getBlock(pos1));
					if (pos.equals(pos1))
						needsRefresh.put(pos1,null);
				}
			}
		}
	}
	
	public Block getBlock(BlockPos pos) {
		return blocks.getOrDefault(pos,null);
	}
}
