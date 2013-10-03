package game.tiles;

import game.BO.terrain.Terrain;
import game.TBSG.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ForestTile extends TerrainTile {
	private static Bitmap forestGraphic = null; //Not sure if I wanna use bitmaps or drawables. :/
	
	public ForestTile(Context context, Terrain terrain) {
		super(context, terrain);
		
		InitalizeImage();
	}
	
	@Override
	protected void InitalizeImage() {
		// TODO Auto-generated method stub
		if(forestGraphic == null) {
			Resources res = context.getResources();
			Bitmap forestBitmap = null;
			
			forestBitmap = BitmapFactory.decodeResource(res, R.drawable.forest_tile, GetBitmapOptions());
			//forestBitmap = Bitmap.createScaledBitmap(forestBitmap, 40, 40, true);
			
			forestGraphic = forestBitmap;
		}
	}

	@Override
	public Bitmap GetImage() {
		// TODO Auto-generated method stub
		return forestGraphic;
	}

}
