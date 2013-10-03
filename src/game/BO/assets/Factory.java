package game.BO.assets;

import android.graphics.Color;
import game.BO.terrain.Road;
import game.BO.terrain.Terrain;

public class Factory extends Terrain {
	public static final int MAP_COLOR = Color.rgb(127,127,127);
	
	public Factory() {
		terrainType = "Road";
		tracksPenalty = 0;
		defenseModifier = 0;
		viewPenalty = 0;
	}
}
