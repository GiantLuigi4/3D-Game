package API.utils;

import javafx.scene.image.Image;

import java.util.HashMap;

import static game.GameInstance.generateImage;

public class ImageLookup {
	public static final HashMap<String, Image> images=new HashMap<>();
	
	public static void loadImages() {
		Image img=generateImage("assets\\game\\images\\blocks\\sand.txtimg");
		Image img2=generateImage("assets\\game\\images\\blocks\\stone.txtimg");
		Image img3=generateImage("assets\\game\\images\\blocks\\scorched stone.txtimg");
		Image img4=generateImage("assets\\game\\images\\blocks\\sandstone.txtimg");
		Image img5=generateImage("assets\\game\\images\\blocks\\fine sand.txtimg");
		Image img6=generateImage("assets\\game\\images\\blocks\\debug.txtimg");
		images.put("game:sand", img);
		images.put("game:stone", img2);
		images.put("game:scorched_stone", img3);
		images.put("game:sandstone", img4);
		images.put("game:fine_sand", img5);
		images.put("game:debug", img6);
	}
}
