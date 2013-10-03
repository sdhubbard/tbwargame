package game.BO.terrain;

import android.graphics.Color;

public class Road extends Terrain {
	//public static final int MAP_COLOR = 0x000000;
	public static int MAP_COLOR = Color.rgb(0, 0, 0); //R:0 G:125 B:0	
	
	public Road() {
		terrainType = "Road";
		tracksPenalty = 0;
		defenseModifier = 0;
		viewPenalty = 0;
	}
}
