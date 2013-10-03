package game.tiles;

import game.BO.unit.Unit;
import game.TBSG.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class TankTile extends UnitTile {
	private static boolean imagesLoaded = false;
	private static Bitmap tankUp = null;
	private static Bitmap tankDown = null;
	private static Bitmap tankLeft = null;
	private static Bitmap tankRight = null;
	
	
	public TankTile(Context context, Unit unit) {
		super(context, unit);
		
		InitializeImage();
	}
	
	private void InitializeImage() {
		if(!imagesLoaded) {
			Resources res = context.getResources();
			
			tankUp = BitmapFactory.decodeResource(res, R.drawable.tank_up, GetBitmapOptions());
			tankDown = BitmapFactory.decodeResource(res, R.drawable.tank_down, GetBitmapOptions());
			tankLeft = BitmapFactory.decodeResource(res, R.drawable.tank_left, GetBitmapOptions());
			tankRight = BitmapFactory.decodeResource(res, R.drawable.tank_right, GetBitmapOptions());
			imagesLoaded = true;
		}
	}
	
	public Bitmap GetUpImage() {
		return tankUp;
	}
	
	public Bitmap GetDownImage() {
		return tankDown;
	}
	
	public Bitmap GetLeftImage() {
		return tankLeft;
	}
	
	public Bitmap GetRightImage() {
		return tankRight;
	}
	
	@Override
	public Bitmap GetImage() {
		// TODO Auto-generated method stub
		String direction = unit.GetDirection();
		
		if(direction == Unit.UP_DIRECTION) {
			return tankUp;
		} else if(direction == Unit.DOWN_DIRECTION) {
			return tankDown;
		} else if(direction == Unit.LEFT_DIRECTION) {
			return tankLeft;
		}  else {
			return tankRight;
		}
	}

}
