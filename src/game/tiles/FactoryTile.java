package game.tiles;

import game.BO.terrain.Road;
import game.BO.terrain.Terrain;
import game.TBSG.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;

public class FactoryTile extends TerrainTile {
	private static boolean imagesLoaded = false;
	private static Bitmap factoryRightT = null;
	private static Bitmap factoryTopT = null;
	private static Bitmap factoryBottomT = null;
	private static Bitmap factoryLeftT = null;
	private static Bitmap factoryCross = null;
	private static Bitmap factoryHorizontal = null;
	private static Bitmap factoryVertical = null;
	private static Bitmap factoryUpperRight = null;
	private static Bitmap factoryUpperLeft = null;
	private static Bitmap factoryLowerRight = null;
	private static Bitmap factoryLowerLeft = null;
/*	private static Bitmap factoryTopEnd = null;
	private static Bitmap roadBottomEnd = null;
	private static Bitmap roadLeftEnd = null;
	private static Bitmap roadRightEnd = null;*/
	
	private Bitmap currentBitmap = null;
	
	public FactoryTile(Context context, Terrain terrain) {
		super(context, terrain);
		InitalizeImage();
	}
	
	@Override
	public Bitmap GetImage() {
		// TODO Auto-generated method stub
		return currentBitmap;
	}

	private Bitmap resolveImage() {
		if(terrain.GetAboveTerrain() instanceof Road &&
				terrain.GetBelowTerrain() instanceof Road &&
				terrain.GetLeftTerrain() instanceof Road &&
				terrain.GetRightTerrain() instanceof Road) {
			return factoryCross;
		} else if(terrain.GetAboveTerrain() instanceof Road &&
				terrain.GetBelowTerrain() instanceof Road) {
			if(terrain.GetRightTerrain() instanceof Road) {
				return factoryRightT;
			} else if (terrain.GetLeftTerrain() instanceof Road) {
				return factoryLeftT;
			} else {
				return factoryVertical;
			}
		} else if(terrain.GetLeftTerrain() instanceof Road &&
				terrain.GetRightTerrain() instanceof Road) {
			if(terrain.GetAboveTerrain() instanceof Road) {
				return factoryBottomT;
			} else if(terrain.GetBelowTerrain() instanceof Road) {
				return factoryTopT;
			} else {
				return factoryHorizontal;
			}
		} else if(terrain.GetLeftTerrain() instanceof Road &&
				terrain.GetBelowTerrain() instanceof Road) {
			return factoryUpperRight;
		} else if(terrain.GetLeftTerrain() instanceof Road &&
				terrain.GetAboveTerrain() instanceof Road) {
			return factoryLowerRight;
		} else if(terrain.GetRightTerrain() instanceof Road &&
				terrain.GetAboveTerrain() instanceof Road) {
			return factoryLowerLeft;
		} else if(terrain.GetRightTerrain() instanceof Road &&
				terrain.GetBelowTerrain() instanceof Road) {
			return factoryUpperLeft;
		} else if(terrain.GetBelowTerrain() instanceof Road) {
			return factoryVertical;
		} else if(terrain.GetAboveTerrain() instanceof Road) {
			return factoryVertical;
		} else if(terrain.GetLeftTerrain() instanceof Road) {
			return factoryHorizontal;
		} else {
			return factoryHorizontal;
		}
	}
	
	@Override
	protected void InitalizeImage() {
		// TODO Auto-generated method stub
		if(!imagesLoaded) {
			Resources res = context.getResources();
			
			factoryRightT = BitmapFactory.decodeResource(res, R.drawable.factory_right_t, GetBitmapOptions());
			factoryLeftT = BitmapFactory.decodeResource(res, R.drawable.factory_left_t, GetBitmapOptions());
			factoryTopT = BitmapFactory.decodeResource(res, R.drawable.factory_top_t, GetBitmapOptions());
			factoryBottomT = BitmapFactory.decodeResource(res, R.drawable.factory_bottom_t, GetBitmapOptions());
			factoryCross = BitmapFactory.decodeResource(res, R.drawable.factory_cross, GetBitmapOptions());
			factoryHorizontal = BitmapFactory.decodeResource(res, R.drawable.factory_horizontal, GetBitmapOptions());
			factoryVertical = BitmapFactory.decodeResource(res, R.drawable.factory_vertical, GetBitmapOptions());
			factoryUpperLeft = BitmapFactory.decodeResource(res, R.drawable.factory_upper_left, GetBitmapOptions());
			factoryUpperRight = BitmapFactory.decodeResource(res, R.drawable.factory_upper_right, GetBitmapOptions());
			factoryLowerLeft = BitmapFactory.decodeResource(res, R.drawable.factory_lower_left, GetBitmapOptions());
			factoryLowerRight = BitmapFactory.decodeResource(res, R.drawable.factory_lower_right, GetBitmapOptions());
		}
		
		this.currentBitmap = resolveImage();
	}

}
