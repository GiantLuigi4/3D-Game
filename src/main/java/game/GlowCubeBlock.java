package game;

import API.model.lighting.LightNode;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GlowCubeBlock extends CubeBlock {
	public GlowCubeBlock(int x, int y, int z, String id) {
		super(x, y, z, id);
		node=new LightNode(pos,1,1,1,1);
	}
	
	public GlowCubeBlock(BlockPos pos, String id) {
		this(pos.x,pos.y,pos.z, id);
	}
	
	private final LightNode node;
	
	@Override
	public void onPlace(World world) {
		world.lights.add(node);
		int refreshRange=(1*16);
		for (int x=this.pos.x-refreshRange;x<=this.pos.x+refreshRange;x+=4) {
			for (int y=this.pos.y-refreshRange;y<=this.pos.y+refreshRange;y+=4) {
				for (int z=this.pos.z-refreshRange;z<=this.pos.z+refreshRange;z+=4) {
					if (!world.needsRefresh.contains(new ChunkPos(new BlockPos(x,y,z)))) {
						world.needsRefresh.add(new ChunkPos(new BlockPos(x,y,z)));
					}
				}
			}
		}
	}
	
	@Override
	public void onRemove(World world) {
		world.lights.remove(node);
		int refreshRange=(1*16);
		for (int x=this.pos.x-refreshRange;x<=this.pos.x+refreshRange;x+=4) {
			for (int y=this.pos.y-refreshRange;y<=this.pos.y+refreshRange;y+=4) {
				for (int z=this.pos.z-refreshRange;z<=this.pos.z+refreshRange;z+=4) {
					if (!world.needsRefresh.contains(new ChunkPos(new BlockPos(x,y,z)))) {
						world.needsRefresh.add(new ChunkPos(new BlockPos(x,y,z)));
					}
				}
			}
		}
	}
}
