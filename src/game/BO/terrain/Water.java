package game.BO.terrain;

import android.graphics.Color;

public class Water extends Terrain {
	//public static final int MAP_COLOR = 0x0000FF;
	public static int MAP_COLOR = Color.rgb(0, 0, 255); //R:0 G:0 B:255
	
	public Water() {
		terrainType = "Water";
		tracksPenalty = -1;
		defenseModifier = 0;
		viewPenalty = 0;
	}	
	
}
