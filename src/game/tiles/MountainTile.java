package game.tiles;

import game.BO.terrain.Terrain;
import game.TBSG.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MountainTile extends TerrainTile {
	private static Bitmap mountainGraphic = null;
	
	public MountainTile(Context context, Terrain terrain) {
		super(context, terrain);
		// TODO Auto-generated constructor stub
		InitalizeImage();
	}

	@Override
	public Bitmap GetImage() {
		// TODO Auto-generated method stub
		return mountainGraphic;
	}

	@Override
	protected void InitalizeImage() {
		// TODO Auto-generated method stub
		if(mountainGraphic == null) {
			Resources res = context.getResources();
			Bitmap mountainBitmap = null;
			
			mountainBitmap = BitmapFactory.decodeResource(res, R.drawable.mountain_tile, GetBitmapOptions());
			mountainGraphic = mountainBitmap;
		}
	}
}
