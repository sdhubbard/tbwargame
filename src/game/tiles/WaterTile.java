package game.tiles;

import game.BO.terrain.Road;
import game.BO.terrain.Terrain;
import game.BO.terrain.Water;
import game.TBSG.R;
import android.R.bool;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Bitmap.Config;

public class WaterTile extends TerrainTile {
	private static boolean imagesLoaded = false;
	private static Bitmap bottomTWater = null;
	private static Bitmap leftTWater = null;
	private static Bitmap rightTWater = null;
	private static Bitmap topTWater = null;
	private static Bitmap waterBottomEnd = null;
	private static Bitmap waterCross = null;
	private static Bitmap waterHorizontal = null;
	private static Bitmap waterVertical = null;
	private static Bitmap waterLeftEnd = null;
	private static Bitmap waterRightEnd = null;
	private static Bitmap waterLowerLeft = null;
	private static Bitmap waterLowerRight = null;
	private static Bitmap waterUpperLeft = null;
	private static Bitmap waterUpperRight = null;
	private static Bitmap waterTopEnd = null;
	
	private static final int waterBaseColor = 0x3568FD;
	private static final int dynamicWaterWidth = 4;
	private static final int dynamicWaterHeight = 2;
	
	private static final int grassBaseColor = 0x00FF00;
	private static final int dynamicGrassWidth = 2;
	private static final int dynamicGrassHeight = 2;
	
	private final int[] sourceWaterColors = new int[]{0x0000FF,0x0000FF,0x0000FF,0xAAAAFF,0xCCCCFF};
	private final int[] sourceGrassColors = new int[]{0x6EAF2D,0x87CE40, 0xBAE392 };
	private Bitmap currentBitmap = null;
	
	public WaterTile(Context context, Terrain terrain) {
		super(context, terrain);
		InitalizeImage();
	}
	
	@Override
	public Bitmap GetImage() {
		// TODO Auto-generated method stub
		return currentBitmap;
	}

	@Override
	protected void InitalizeImage() {
		// TODO Auto-generated method stub
		if(!imagesLoaded) {
			Resources res = context.getResources();

			imagesLoaded = true;
			waterCross = BitmapFactory.decodeResource(res, R.drawable.water_cross, GetBitmapOptions());
			bottomTWater = BitmapFactory.decodeResource(res, R.drawable.bottom_t_water, GetBitmapOptions());
			leftTWater = BitmapFactory.decodeResource(res, R.drawable.left_t_water, GetBitmapOptions());
			rightTWater = BitmapFactory.decodeResource(res, R.drawable.right_t_water, GetBitmapOptions());
			topTWater = BitmapFactory.decodeResource(res, R.drawable.top_t_water, GetBitmapOptions());
			waterHorizontal = BitmapFactory.decodeResource(res, R.drawable.water_horizontal, GetBitmapOptions());
			waterVertical = BitmapFactory.decodeResource(res, R.drawable.water_vertical, GetBitmapOptions());
			waterUpperLeft = BitmapFactory.decodeResource(res, R.drawable.water_upper_left, GetBitmapOptions());
			waterUpperRight = BitmapFactory.decodeResource(res, R.drawable.water_upper_right, GetBitmapOptions());
			waterLowerLeft = BitmapFactory.decodeResource(res, R.drawable.water_lower_left, GetBitmapOptions());
			waterLowerRight = BitmapFactory.decodeResource(res, R.drawable.water_lower_right, GetBitmapOptions());
			waterTopEnd = BitmapFactory.decodeResource(res, R.drawable.water_top_end, GetBitmapOptions());
			waterBottomEnd = BitmapFactory.decodeResource(res, R.drawable.water_bottom_end, GetBitmapOptions());
			waterLeftEnd = BitmapFactory.decodeResource(res, R.drawable.water_left_end, GetBitmapOptions());
			waterRightEnd = BitmapFactory.decodeResource(res, R.drawable.water_right_end, GetBitmapOptions());
		}
		
		this.currentBitmap = resolveImage();
	}

	private Bitmap resolveImage() {
		
		if(getIsLeftValid() && getIsRightValid() && getIsAboveValid() && getIsBelowValid()) {
			currentBitmap = waterCross.copy(Config.ARGB_8888, true);
		} else if(getIsAboveValid() && getIsBelowValid()) {
			if(getIsRightValid()) {
				currentBitmap = rightTWater.copy(Config.ARGB_8888, true);
			} else if(getIsLeftValid()) {
				currentBitmap = leftTWater.copy(Config.ARGB_8888, true);
			} else {
				currentBitmap = waterVertical.copy(Config.ARGB_8888, true);
			}
		} else if(getIsLeftValid() && getIsRightValid()) {
			if(getIsAboveValid()) {
				currentBitmap = topTWater.copy(Config.ARGB_8888, true);
			} else if(getIsBelowValid()) {
				currentBitmap = bottomTWater.copy(Config.ARGB_8888, true);
			} else {
				currentBitmap = waterHorizontal.copy(Config.ARGB_8888, true);
			}
		} else if(getIsLeftValid() && getIsBelowValid()) {
			currentBitmap = waterUpperRight.copy(Config.ARGB_8888, true);
		} else if(getIsLeftValid() && getIsAboveValid()) {
			currentBitmap = waterLowerRight.copy(Config.ARGB_8888, true);
		} else if(getIsRightValid() && getIsBelowValid()) {
			currentBitmap = waterUpperLeft.copy(Config.ARGB_8888, true);
		} else if(getIsRightValid() && getIsAboveValid()) {
			currentBitmap = waterLowerLeft.copy(Config.ARGB_8888, true);
		} else if(getIsBelowValid()) {
			currentBitmap = waterTopEnd.copy(Config.ARGB_8888, true);
		} else if(getIsAboveValid()) {
			currentBitmap = waterBottomEnd.copy(Config.ARGB_8888, true);
		} else if(getIsLeftValid()) {
			currentBitmap = waterRightEnd.copy(Config.ARGB_8888, true);
		} else {// if(getIsRightValid()) {
			currentBitmap = waterLeftEnd.copy(Config.ARGB_8888, true);
		}
		
		GenerateRandomGrass(currentBitmap);
		
		return currentBitmap;
	}
	
	private void GenerateRandomGrass(Bitmap bitmap) {
		for(int currentX = 0; currentX < (bitmap.getWidth() / dynamicGrassWidth); currentX++) {
			for(int currentY = 0; currentY < (bitmap.getWidth() / dynamicGrassHeight); currentY++) {
				int currentIndex = new Double(Math.floor(Math.random() * 3)).intValue();
				int currentColor = sourceGrassColors[currentIndex];
				
				for(int pixelX = 0; pixelX < dynamicGrassWidth; pixelX++) {
					for(int pixelY = 0; pixelY < dynamicGrassHeight; pixelY++) {
						int drawX = currentX * dynamicGrassWidth + pixelX;
						int drawY = currentY * dynamicGrassHeight + pixelY;
						
						if(bitmap.getPixel(drawX, drawY) == (grassBaseColor + Color.BLACK)) {
							bitmap.setPixel( drawX, drawY, currentColor + Color.BLACK);
						}		
					}
				}
			}
		}
	}
	
	private boolean getIsLeftValid() {
		return (terrain.GetLeftTerrain() instanceof Road && terrain.GetLeftTerrain().GetLeftTerrain() instanceof Water) || terrain.GetLeftTerrain() instanceof Water;
	}
	
	private boolean getIsRightValid() {
		return (terrain.GetRightTerrain() instanceof Road && terrain.GetRightTerrain().GetRightTerrain() instanceof Water) || terrain.GetRightTerrain() instanceof Water; 
	}
	
	private boolean getIsAboveValid() {
		return (terrain.GetAboveTerrain() instanceof Road && terrain.GetAboveTerrain().GetAboveTerrain() instanceof Water) || terrain.GetAboveTerrain() instanceof Water;
	}
	
	private boolean getIsBelowValid() {
		return (terrain.GetBelowTerrain() instanceof Road && terrain.GetBelowTerrain().GetBelowTerrain() instanceof Water) || terrain.GetBelowTerrain() instanceof Water;
	}

}
