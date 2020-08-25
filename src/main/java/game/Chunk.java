package game;

import API.model.Model;
import API.utils.MathHelper;
import API.utils.StringyHashMap;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import org.fxyz3d.geometry.Vector3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class Chunk {
	private final Block[] blocks=new Block[size*size*size];
	public final ChunkPos pos;
	public final World world;
	protected static final int size=32;
	
	public Chunk(ChunkPos pos,World world) {
		this.pos = pos;
		this.world = world;
		Arrays.fill(blocks, null);
	}
	
	public Block getBlock(BlockPos pos) {
		BlockPos pos1=methodToName(pos);
		return blocks[getIndexFromPos(pos1)];
	}
	
	public void setBlock(BlockPos pos, Block block) {
		BlockPos pos1=methodToName(pos);
		if (blocks[getIndexFromPos(pos1)]!=null) {
			blocks[getIndexFromPos(pos1)].onRemove(world);
			if (block!=null) {
				blocks[getIndexFromPos(pos1)]=block;
			}
		} else {
			if (block!=null) {
				blocks[getIndexFromPos(pos1)]=block;
			}
		}
		if (block==null) blocks[getIndexFromPos(pos1)]=null;
		if (block!=null) block.onPlace(world);
	}
	
	//Get a pos in the chunk from a pos in the world
	public BlockPos methodToName(BlockPos pos1) {
		return new BlockPos(Math.abs(pos1.x)%size,Math.abs(pos1.y)%size,Math.abs(pos1.z)%size);
	}
	
	private int getIndexFromPos(BlockPos pos) {
		return (pos.x)+(pos.y*size)+(pos.z*size*size);
	}
	
	public Model bakeModel() {
		Model mdl=new Model();
		Arrays.asList(blocks).forEach((b)-> {
			if (b != null) {
				Model mdl2 = b.getModel(world, b.pos);
				if (mdl2 != null) {
					mdl2.getAllElements().forEach((e) -> {
						for (Node nd : e.getNodes()) {
							if (nd instanceof javafx.scene.shape.Box) {
								Material mat = ((javafx.scene.shape.Box) nd).getMaterial();
								if (mat instanceof PhongMaterial) {
									AtomicReference<Double> red = new AtomicReference<>((double) 0);
									AtomicReference<Double> green = new AtomicReference<>((double) 0);
									AtomicReference<Double> blue = new AtomicReference<>((double) 0);
									world.lights.forEach((l) -> {
										double intense = (l.intensity * 15);
										double brightness = Math.max(0, (((Math.abs(Math.max(l.intensity, Math.abs(l.getDistanceTo(new Vector3D(b.pos.x, b.pos.y, b.pos.z)))))) / intense)));
										red.set(Math.max(red.get(), MathHelper.Lerp(brightness, red.get(), l.r)));
										green.set(Math.max(green.get(), MathHelper.Lerp(brightness, green.get(), l.g)));
										blue.set(Math.max(blue.get(), MathHelper.Lerp(brightness, blue.get(), l.b)));
									});
									((PhongMaterial) mat).setDiffuseColor(Color.color(
											Math.max(0.2, Math.min(1, red.get())),
											Math.max(0.2, Math.min(1, green.get())),
											Math.max(0.2, Math.min(1, blue.get()))
									));
								}
							}
						}
					});
					mdl.addElements(mdl2.getAllElements());
				}
			}
		});
		return mdl;
	}
}
