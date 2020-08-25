package game;

import API.model.lighting.LightNode;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GlowCubeBlock extends CubeBlock {
	private final int intensity;
	
	public GlowCubeBlock(int x, int y, int z, String id) {
		super(x, y, z, id);
		this.intensity=1;
		node=new LightNode(pos,1,1,1,intensity);
	}
	
	public GlowCubeBlock(BlockPos pos, String id) {
		this(pos.x,pos.y,pos.z, id);
	}
	
	private final LightNode node;
	
	@Override
	public void onPlace(World world) {
		world.lights.add(node);
		int refreshRange=(intensity*16);
		for (int x=this.pos.x-refreshRange;x<=this.pos.x+refreshRange;x+=16) {
			for (int y=this.pos.y-refreshRange;y<=this.pos.y+refreshRange;y+=16) {
				for (int z=this.pos.z-refreshRange;z<=this.pos.z+refreshRange;z+=16) {
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
		int refreshRange=(intensity*16);
		for (int x=this.pos.x-refreshRange;x<=this.pos.x+refreshRange;x+=16) {
			for (int y=this.pos.y-refreshRange;y<=this.pos.y+refreshRange;y+=16) {
				for (int z=this.pos.z-refreshRange;z<=this.pos.z+refreshRange;z+=16) {
					if (!world.needsRefresh.contains(new ChunkPos(new BlockPos(x,y,z)))) {
						world.needsRefresh.add(new ChunkPos(new BlockPos(x,y,z)));
					}
				}
			}
		}
	}
}
