package game.tiles;

import game.BO.terrain.Road;
import game.BO.terrain.Terrain;
import game.BO.assets.Factory;
import game.TBSG.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Bitmap.Config;

public class RoadTile extends TerrainTile {
	private static boolean imagesLoaded = false;
	private static Bitmap rightTRoad = null;
	private static Bitmap upTRoad = null;
	private static Bitmap downTRoad = null;
	private static Bitmap leftTRoad = null;
	private static Bitmap crossRoad = null;
	private static Bitmap roadHorizontal = null;
	private static Bitmap roadVertical = null;
	private static Bitmap roadUpperRight = null;
	private static Bitmap roadUpperLeft = null;
	private static Bitmap roadLowerRight = null;
	private static Bitmap roadLowerLeft = null;
	private static Bitmap roadTopEnd = null;
	private static Bitmap roadBottomEnd = null;
	private static Bitmap roadLeftEnd = null;
	private static Bitmap roadRightEnd = null;
	
	private static final int dynamicGrassWidth = 2;
	private static final int dynamicGrassHeight = 2;
	private static final int grassBaseColor = 0x00FF00;
	
	private final int[] sourceColors = new int[]{0x6EAF2D,0x87CE40, 0xBAE392 };
	private Bitmap currentBitmap = null;
	
	public RoadTile(Context context, Terrain terrain) {
		super(context, terrain);
		// TODO Auto-generated constructor stub
		InitalizeImage();
	}

	private boolean AboveTerrainValid() {
		return (terrain.GetAboveTerrain() instanceof Road) || (terrain.GetAboveTerrain() instanceof Factory);
	}
	
	private boolean BelowTerrainValid() {
		return (terrain.GetBelowTerrain() instanceof Road) || (terrain.GetBelowTerrain() instanceof Factory);
	}
	
	private boolean LeftTerrainValid() {
		return (terrain.GetLeftTerrain() instanceof Road) || (terrain.GetLeftTerrain() instanceof Factory);
	}
	
	private boolean RightTerrainValid() {
		return (terrain.GetRightTerrain() instanceof Road) || (terrain.GetRightTerrain() instanceof Factory);
	}
	
	private Bitmap resolveImage() {
		if(AboveTerrainValid() &&
				BelowTerrainValid() &&
				LeftTerrainValid() &&
				RightTerrainValid()) {
			return crossRoad;
		} else if(AboveTerrainValid() &&
				BelowTerrainValid()) {
			if(RightTerrainValid()) {
				return rightTRoad;
			} else if (LeftTerrainValid()) {
				return leftTRoad;
			} else {
				return roadVertical;
			}
		} else if(LeftTerrainValid() &&
				RightTerrainValid()) {
			if(AboveTerrainValid()) {
				return upTRoad;
			} else if(BelowTerrainValid()) {
				return downTRoad;
			} else {
				return roadHorizontal;
			}
		} else if(LeftTerrainValid() &&
				BelowTerrainValid()) {
			Bitmap roadUpperRightCopy = roadUpperRight.copy(Config.ARGB_8888, true);
			
			GenerateRandomGrass(roadUpperRightCopy);
			return roadUpperRightCopy;
		} else if(LeftTerrainValid() &&
				AboveTerrainValid()) {
			Bitmap roadLowerRightCopy = roadLowerRight.copy(Config.ARGB_8888, true);
			
			GenerateRandomGrass(roadLowerRightCopy);
			return roadLowerRightCopy;
		} else if(RightTerrainValid() &&
				AboveTerrainValid()) {
			Bitmap roadLowerLeftCopy = roadLowerLeft.copy(Config.ARGB_8888, true);
			
			GenerateRandomGrass(roadLowerLeftCopy);
			return roadLowerLeftCopy;
		} else if(RightTerrainValid() &&
				BelowTerrainValid()) {
			Bitmap roadUpperLeftCopy = roadUpperLeft.copy(Config.ARGB_8888, true);
			
			GenerateRandomGrass(roadUpperLeftCopy);
			return roadUpperLeftCopy;
		} else if(BelowTerrainValid()) {
			Bitmap roadTopEndCopy = roadTopEnd.copy(Config.ARGB_8888, true);
			
			GenerateRandomGrass(roadTopEndCopy);
			return roadTopEndCopy;
		} else if(AboveTerrainValid()) {
			Bitmap roadBottomEndCopy = roadBottomEnd.copy(Config.ARGB_8888, true);
			
			GenerateRandomGrass(roadBottomEndCopy);
			return roadBottomEndCopy;
		} else if(LeftTerrainValid()) {
			Bitmap roadRightEndCopy = roadRightEnd.copy(Config.ARGB_8888, true);
			
			GenerateRandomGrass(roadRightEndCopy);
			return roadRightEndCopy;
		} else {
			Bitmap roadLeftEndCopy = roadLeftEnd.copy(Config.ARGB_8888, true);
			
			GenerateRandomGrass(roadLeftEndCopy);
			return roadLeftEndCopy;
		}
	}
	
	private void GenerateRandomGrass(Bitmap bitmap) {
		for(int currentX = 0; currentX < (bitmap.getWidth() / dynamicGrassWidth); currentX++) {
			for(int currentY = 0; currentY < (bitmap.getWidth() / dynamicGrassHeight); currentY++) {
				int currentIndex = new Double(Math.floor(Math.random() * 3)).intValue();
				int currentColor = sourceColors[currentIndex];
				
				for(int pixelX = 0; pixelX < dynamicGrassWidth; pixelX++) {
					for(int pixelY = 0; pixelY < dynamicGrassHeight; pixelY++) {
						int drawX = currentX * dynamicGrassWidth + pixelX;
						int drawY = currentY * dynamicGrassHeight + pixelY;						
						int pixelColor = bitmap.getPixel(drawX, drawY) - Color.BLACK;
						
						if(pixelColor == (grassBaseColor )) {
							bitmap.setPixel(drawX, drawY, currentColor + Color.BLACK);
						}		
					}
				}
			}
		}
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
						
			rightTRoad = BitmapFactory.decodeResource(res, R.drawable.right_t_road, GetBitmapOptions());
			upTRoad = BitmapFactory.decodeResource(res, R.drawable.up_t_road, GetBitmapOptions());
			downTRoad = BitmapFactory.decodeResource(res, R.drawable.down_t_road, GetBitmapOptions());
			leftTRoad = BitmapFactory.decodeResource(res, R.drawable.left_t_road, GetBitmapOptions());
			crossRoad = BitmapFactory.decodeResource(res, R.drawable.cross_road, GetBitmapOptions());
			roadHorizontal = BitmapFactory.decodeResource(res, R.drawable.road_horizontal, GetBitmapOptions());
			roadVertical = BitmapFactory.decodeResource(res, R.drawable.road_vertical, GetBitmapOptions());
			roadUpperRight = BitmapFactory.decodeResource(res, R.drawable.road_upper_right, GetBitmapOptions());
			roadUpperLeft = BitmapFactory.decodeResource(res, R.drawable.road_upper_left, GetBitmapOptions());
			roadLowerRight = BitmapFactory.decodeResource(res, R.drawable.road_lower_right, GetBitmapOptions());
			roadLowerLeft = BitmapFactory.decodeResource(res, R.drawable.road_lower_left, GetBitmapOptions());
			roadTopEnd = BitmapFactory.decodeResource(res, R.drawable.road_top_end, GetBitmapOptions());
			roadBottomEnd = BitmapFactory.decodeResource(res, R.drawable.road_bottom_end, GetBitmapOptions());
			roadLeftEnd = BitmapFactory.decodeResource(res, R.drawable.road_left_end, GetBitmapOptions());
			roadRightEnd = BitmapFactory.decodeResource(res, R.drawable.road_right_end, GetBitmapOptions());
			imagesLoaded = true;
		}
		
		this.currentBitmap = resolveImage();
	}
}
