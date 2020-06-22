package game;

import API.utils.Color;
import API.utils.ImageLookup;
import API.model.Model;
import API.model.elements.Box;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.paint.PhongMaterial;
import org.fxyz3d.utils.geom.Vec3d;

import java.util.Random;

public class CubeBlock extends Block {
	private final Model cube;
	
	Point3D hitVec=null;
	Point3D placeVec=null;
	
	public CubeBlock(int x, int y, int z, String id) {
		this(new BlockPos(x,y,z), id);
	}
	
	public CubeBlock(BlockPos pos, String id) {
		super(pos, id);
		Box bx=new Box(1,1,1);
		bx.setMouseEnteredEvent((e)->{
			double xoff=Math.cos(Math.toRadians(-GameInstance.cameraTransform.ry.getAngle()+90));
			double yoff=Math.cos(Math.toRadians(GameInstance.cameraTransform.rx.getAngle()+90));
			double yoff2=Math.sin(Math.toRadians(GameInstance.cameraTransform.rx.getAngle()+90));
			double zoff=Math.sin(Math.toRadians(-GameInstance.cameraTransform.ry.getAngle()+90));
			Point3D intersect=e.getPickResult().getIntersectedPoint();
			int xPos=(int)e.getPickResult().getIntersectedNode().getTranslateX();
			int yPos=(int)e.getPickResult().getIntersectedNode().getTranslateY();
			int zPos=(int)e.getPickResult().getIntersectedNode().getTranslateZ();
			double dist=e.getPickResult().getIntersectedDistance();
			Point2D texCoords=e.getPickResult().getIntersectedTexCoord();
			System.out.println(e.getPickResult().getIntersectedFace());
			double xsearch=GameInstance.player.getX()+(xoff*(Math.abs(yoff2)))*(dist);
			double ysearch=(GameInstance.player.getY()+yoff*(dist))-GameInstance.player.getHeight()/2f+0.5f;
			double zsearch=GameInstance.player.getZ()+(zoff*(Math.abs(yoff2)))*(dist);
//			boolean negativeX=!(xsearch>0);
//			boolean negativeZ=!(zsearch>0);
//			xsearch+=(negativeX?-0.5:0.5);
//			zsearch+=(negativeZ?-0.5:0.5);
			BlockPos pos1=new BlockPos((int)xPos,-(int)yPos,(int)zPos);
			GameInstance.world.setBlock(pos1,new CubeBlock(pos1,"game:scorched_stone"));
		});
		bx.setTranslate(pos.x,-pos.y,pos.z);
		PhongMaterial material=new PhongMaterial();
		material.setDiffuseColor(GameInstance.getOrCreateColor(new Color(255,255,255,255)));
		material.setDiffuseMap(ImageLookup.images.get(id));
		material.setSelfIlluminationMap(ImageLookup.images.getOrDefault(id+"_glow",material.getSelfIlluminationMap()));
		bx.setMaterial(material);
		cube=new Model(bx);
	}
	
	@Override
	public Model getModel(World world, BlockPos pos) {
		if (
				world.getBlock(new BlockPos(pos.x+1,pos.y,pos.z))==null||
				world.getBlock(new BlockPos(pos.x,pos.y+1,pos.z))==null||
				world.getBlock(new BlockPos(pos.x,pos.y,pos.z+1))==null||
				world.getBlock(new BlockPos(pos.x,pos.y,pos.z-1))==null||
				world.getBlock(new BlockPos(pos.x,pos.y-1,pos.z))==null||
				world.getBlock(new BlockPos(pos.x-1,pos.y,pos.z))==null
		) {
			((Box)cube.getAllElements().get(0)).setTranslateY(-pos.y);
			return cube;
		}
		return null;
	}
}
