package game;

import API.model.Model;
import API.model.elements.Cube;

public class Block {
	public final BlockPos pos;
	public final String id;
	
	public Block(int x, int y, int z,String id) {
		this.pos=new BlockPos(x,y,z);
		this.id=id;
	}
	
	public Block(BlockPos pos,String id) {
		this.pos = pos;
		this.id=id;
	}
	
	public Model getModel(World world,BlockPos pos) {
		if (
				world.getBlock(new BlockPos(pos.x+1,pos.y,pos.z))==null||
				world.getBlock(new BlockPos(pos.x,pos.y+1,pos.z))==null||
				world.getBlock(new BlockPos(pos.x,pos.y,pos.z+1))==null||
				world.getBlock(new BlockPos(pos.x,pos.y,pos.z-1))==null||
				world.getBlock(new BlockPos(pos.x,pos.y-1,pos.z))==null||
				world.getBlock(new BlockPos(pos.x-1,pos.y,pos.z))==null
		)
		return new Model(new Cube(1));
		return null;
	}
}
