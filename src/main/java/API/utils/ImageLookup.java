package API.utils;

import javafx.scene.image.Image;

import java.util.HashMap;

import static game.GameInstance.generateImage;

public class ImageLookup {
	public static final HashMap<String, Image> images=new HashMap<>();
	
	public static void loadImages() {
		//BLOCKS
		Image img=generateImage("assets\\game\\images\\blocks\\sand.txtimg");
		Image img2=generateImage("assets\\game\\images\\blocks\\stone.txtimg");
		Image img3=generateImage("assets\\game\\images\\blocks\\scorched stone.txtimg");
		Image img4=generateImage("assets\\game\\images\\blocks\\sandstone.txtimg");
		Image img5=generateImage("assets\\game\\images\\blocks\\fine sand.txtimg");
		Image img6=generateImage("assets\\game\\images\\blocks\\debug.txtimg");
		Image img13=generateImage("assets\\game\\images\\blocks\\glow block.txtimg");
		Image img14=generateImage("assets\\game\\images\\blocks\\glow block glow.txtimg");
		Image img15=generateImage("assets\\game\\images\\blocks\\glow block specular.txtimg");
		Image img16=generateImage("assets\\game\\images\\blocks\\glow block bumpmap.txtimg");
		Image img17=generateImage("assets\\game\\images\\blocks\\stone2.txtimg");
		//WORLD
		Image img7=generateImage("assets\\game\\images\\world\\skybox day.txtimg");
		Image img8=generateImage("assets\\game\\images\\world\\skybox night.txtimg");
		//UI
		Image img9=generateImage("assets\\game\\images\\ui\\hotbar.txtimg");
		Image img10=generateImage("assets\\game\\images\\ui\\hotbar end.txtimg");
		Image img11=generateImage("assets\\game\\images\\ui\\inventory bumpmap.txtimg");
		Image img12=generateImage("assets\\game\\images\\ui\\inventory illumination.txtimg");
		images.put("game:sand", img);
		images.put("game:stone", img2);
		images.put("game:stone2", img17);
		images.put("game:scorched_stone", img3);
		images.put("game:sandstone", img4);
		images.put("game:fine_sand", img5);
		images.put("game:debug", img6);
		images.put("game:sky_day", img7);
		images.put("game:sky_night", img8);
		images.put("game:hotbar", img9);
		images.put("game:hotbar_end", img10);
		images.put("game:inventory_specular", img11);
		images.put("game:inventory_illumination", img12);
		images.put("game:glow_block", img13);
		images.put("game:glow_block_specular", img15);
		images.put("game:glow_block_glow", img14);
		images.put("game:glow_block_bumpmap", img16);
	}
}
