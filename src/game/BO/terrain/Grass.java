package game.BO.terrain;

import android.graphics.Color;

public class Grass extends Terrain {
	//public static final int MAP_COLOR = 0x00FF00;
	public static final int MAP_COLOR = Color.rgb(0, 255, 0);
	
	public Grass() {
		terrainType = "Grass";
		tracksPenalty = 1;
		defenseModifier = 1;
		viewPenalty = 0;
	}
}
