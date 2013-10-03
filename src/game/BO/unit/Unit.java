package game.BO.unit;

import android.graphics.Bitmap;

public abstract class Unit {
	public final static String DOWN_DIRECTION = "downDirection";
	public final static String UP_DIRECTION = "upDirection";
	public final static String LEFT_DIRECTION = "leftDirection";
	public final static String RIGHT_DIRECTION = "rightDirection";
	
	private int gridX = 0;
	private int gridY = 0;
	
	protected String direction = UP_DIRECTION;
	protected int visualRange = 1;	
	protected int unitSpeed = 0;
	protected LOCOMOTION locomotion =  LOCOMOTION.TRACKS;
	
	public enum LOCOMOTION {
		TRACKS, WHEELS, AIR, WATER
	}
	
	public LOCOMOTION GetLocomotion() {
		return locomotion;
	}
	
	public int GetSpeed() {
		return unitSpeed;
	}
	
	public int GetVisualRange() {
		return visualRange;
	}
	
	public String GetDirection() {
		return direction;
	}
	
	public void SetDirection(String direction) {
		this.direction = direction;
	}
	
	public int GetGridY() {
		return gridY;
	}
	
	public void SetGridY(int gridY) {
		this.gridY = gridY;
	}
	
	public int GetGridX() {
		return gridX;
	}
	
	public void SetGridX(int gridX) {
		this.gridX = gridX;
	}
}
