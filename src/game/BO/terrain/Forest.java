package game.BO.terrain;

import android.content.res.ColorStateList;
import android.graphics.Color;

public class Forest extends Terrain {
	//public static final int MAP_COLOR = 0x007D00; //R:0 G:125 B:0		
	public static int MAP_COLOR = Color.rgb(0, 125, 0); //R:0 G:125 B:0		
	
	
	public Forest() {
		terrainType = "Default";
		tracksPenalty = 2;
		defenseModifier = 3;
		viewPenalty = 3;
	}
}
