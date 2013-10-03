package game.BO.unit;

import android.graphics.Bitmap;

public class Tank extends Unit {
	public Tank() {
		visualRange = 4;
		unitSpeed = 6;
		locomotion = LOCOMOTION.TRACKS;
	}
}
