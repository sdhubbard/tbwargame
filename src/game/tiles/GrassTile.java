package game.tiles;

import game.BO.terrain.Terrain;
import game.TBSG.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GrassTile extends TerrainTile {

	private static Bitmap grassGraphic = null;
	private final int[] sourceColors = new int[]{0x6EAF2D,0x87CE40, 0xBAE392 };
	
	
	public GrassTile(Context context, Terrain terrain) {
		super(context, terrain);
		// TODO Auto-generated constructor stub
		InitalizeImage();
	}

	@Override
	public Bitmap GetImage() {
		// TODO Auto-generated method stub
		return grassGraphic;
	}

	@Override
	protected void InitalizeImage() {
		// TODO Auto-generated method stub
/*		int[] pixels = new int[40 * 40];
		
		grassGraphic = Bitmap.createBitmap(40, 40, Config.ARGB_8888);
		
		
		for(int currentX = 0; currentX < 20; currentX++) {
			for(int currentY = 0; currentY < 20; currentY++) {
				int currentIndex = new Double(Math.floor(Math.random() * 3)).intValue();
				int currentColor = sourceColors[currentIndex];
				
				grassGraphic.setPixel(currentX * 2, currentY * 2, currentColor + Color.BLACK);
				grassGraphic.setPixel(currentX *2 + 1, currentY * 2, currentColor + Color.BLACK);
				grassGraphic.setPixel(currentX * 2, currentY * 2 + 1, currentColor + Color.BLACK);
				grassGraphic.setPixel(currentX * 2 + 1, currentY * 2 + 1, currentColor + Color.BLACK);
				
				pixels[(currentX * 2) + (currentY * 2) * 40] = currentColor + Color.BLACK;
				pixels[((currentX * 2) + 1) + (currentY * 2) * 40] = currentColor + Color.BLACK;
				pixels[(currentX * 2) + ((currentY * 2) + 1) * 40] = currentColor + Color.BLACK;
				pixels[((currentX * 2) + 1) + ((currentY * 2) + 1) * 40] = currentColor + Color.BLACK;
			}
		}
	
		grassGraphic.setPixels(pixels,0,40,0,0,40,40);*/
		if(grassGraphic == null) {
			Resources res = context.getResources();
			Bitmap forestBitmap = null;
			
			forestBitmap = BitmapFactory.decodeResource(res, R.drawable.grass_tile, GetBitmapOptions());
			//forestBitmap = Bitmap.createScaledBitmap(forestBitmap, 40, 40, true);
			grassGraphic = forestBitmap;
		}
		
	}

}
