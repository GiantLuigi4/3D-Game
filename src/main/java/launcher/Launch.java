package launcher;

import game.GameInstance;

public class Launch {
	public static void main(String[] args) {
		try {
			GameInstance.launch(GameInstance.class,args);
		} catch (Throwable err) {
			err.printStackTrace();
			Runtime.getRuntime().exit(130);
		}
	}
}
