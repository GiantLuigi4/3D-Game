package game;

import API.model.elements.NodeList;
import API.utils.Color;
import API.utils.ImageLookup;
import API.model.Model;
import API.model.elements.Box;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.paint.PhongMaterial;

import java.util.ArrayList;

public class CubeBlock extends Block {
	private final Model cube;
	
	Point3D hitVec=null;
	Point3D placeVec=null;
	
	private final PhongMaterial material=new PhongMaterial();
	
	public CubeBlock(int x, int y, int z, String id) {
		this(new BlockPos(x,y,z), id);
	}
	
	public CubeBlock(BlockPos pos, String id) {
		super(pos, id);
		Box bx=new Box(1,1,1);
		bx.setTranslate(pos.x,-pos.y,pos.z);
		material.setDiffuseColor(GameInstance.getOrCreateColor(new Color(255,255,255,255)));
		material.setDiffuseMap(ImageLookup.images.get(id));
		material.setSelfIlluminationMap(ImageLookup.images.getOrDefault(id+"_glow",material.getSelfIlluminationMap()));
		material.setSpecularMap(ImageLookup.images.getOrDefault(id+"_specular",material.getSelfIlluminationMap()));
		material.setBumpMap(ImageLookup.images.getOrDefault(id+"_bumpmap",material.getSelfIlluminationMap()));
		bx.setMaterial(material);
		cube=new Model(bx,new NodeList(getAdditionalModelParts()));
	}
	
	public ArrayList<Node> getAdditionalModelParts() {
		return null;
	}
	
	@Override
	public Model getModel(World world, BlockPos pos) {
		if (
				world!=null&&(
				world.getBlock(new BlockPos(pos.x+1,pos.y,pos.z))==null||
				world.getBlock(new BlockPos(pos.x,pos.y+1,pos.z))==null||
				world.getBlock(new BlockPos(pos.x,pos.y,pos.z+1))==null||
				world.getBlock(new BlockPos(pos.x,pos.y,pos.z-1))==null||
				world.getBlock(new BlockPos(pos.x,pos.y-1,pos.z))==null||
				world.getBlock(new BlockPos(pos.x-1,pos.y,pos.z))==null
		)) {
			return cube;
		} else if (world==null) {
			return cube;
		}
		return null;
	}
}
