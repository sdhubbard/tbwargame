package game.tiles;

import game.BO.terrain.Terrain;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

public abstract class TerrainTile {
	
	protected Context context;
	protected Terrain terrain;
	
	public static BitmapFactory.Options GetBitmapOptions() {
		BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
		
		bitmapFactoryOptions.inPreferQualityOverSpeed = true;
		bitmapFactoryOptions.inScaled = false;
		
		return bitmapFactoryOptions;
	}
	
	public TerrainTile(Context context, Terrain terrain) {
		this.context = context;
		this.terrain = terrain;
	}
	
	protected abstract void InitalizeImage();
	
	public abstract Bitmap GetImage();
}
