package game.tiles;

import game.BO.unit.Unit;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public abstract class UnitTile {
	protected Context context;
	protected Unit unit;
	
	public static BitmapFactory.Options GetBitmapOptions() {
		BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
		
		bitmapFactoryOptions.inPreferQualityOverSpeed = true;
		bitmapFactoryOptions.inScaled = false;
		
		return bitmapFactoryOptions;
	}
	
	public UnitTile(Context context, Unit unit) {
		this.context = context;
		this.unit = unit;
	}
	
	public abstract Bitmap GetImage();
	
	public abstract Bitmap GetUpImage();
	
	public abstract Bitmap GetDownImage();
	
	public abstract Bitmap GetLeftImage();
	
	public abstract Bitmap GetRightImage();
	
	public Unit GetUnit() {
		return unit;
	}
}
