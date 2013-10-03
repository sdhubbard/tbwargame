package game.BO.terrain;

import android.graphics.Color;

public class Mountain extends Terrain {
	public static final int MAP_COLOR = Color.rgb(255, 0, 0);
	
	public Mountain() {
		terrainType = "Mountain";
		tracksPenalty = -1;
		defenseModifier = 4;
		viewPenalty = 3;
	}
}
