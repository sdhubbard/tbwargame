package game.utils;

import game.BO.terrain.Terrain;
import game.BO.unit.Unit;
import game.tiles.UnitTile;

import java.util.ArrayList;
import java.util.Date;

import android.graphics.PointF;

public class UnitMovementAnimator {

	private ArrayList<Terrain> path = null;
	private UnitTile unitTile = null;
	private int terrainIndex = 0;
	private MapHelper mapHelper = null;
	private final float timePerTile = 250f;
	
	private Date startDate = null;
	
	public UnitMovementAnimator(ArrayList<Terrain> path, UnitTile unitTile, MapHelper mapHelper) {
		this.mapHelper = mapHelper;
		this.path = path;
		this.unitTile = unitTile;
		mapHelper.CalculateLineOfSight();
	}
	
	public void Start() {
		startDate = new Date();
		UpdateDirection();
	}
	
	public Unit GetUnit() {
		return unitTile.GetUnit();
	}
	
	public PointF getPosition() {
		int tileDimensions = MapHelper.TILE_DIMENSIONS;
		float startX = (float)(path.get(terrainIndex).GetGridX() * tileDimensions);
		float startY = (float)(path.get(terrainIndex).GetGridY() * tileDimensions);
		
		if(startDate == null) {
			return new PointF(startX, startY);
		} else if( terrainIndex == path.size() - 1) {
			unitTile.GetUnit().SetGridX(path.get(terrainIndex).GetGridX());
			unitTile.GetUnit().SetGridY(path.get(terrainIndex).GetGridY());
			return new PointF(startX, startY);
		} else {
			float endX = (float)(path.get(terrainIndex + 1).GetGridX() * tileDimensions);
			float endY =  (float)(path.get(terrainIndex + 1).GetGridY() * tileDimensions);
			Date currentDate = new Date();
			long deltaDate = currentDate.getTime() - startDate.getTime();
			PointF newPoint = new PointF(endX, endY);
			
			if(deltaDate < timePerTile) {
				float dateRatio = deltaDate / timePerTile;
				float deltaX = endX - startX;
				float deltaY = endY - startY;
				
				newPoint = new PointF(startX + (deltaX * dateRatio), startY + (deltaY * dateRatio));
			} else {
				startDate = new Date();
				terrainIndex = terrainIndex + 1;
				if(terrainIndex != path.size() - 1) {					
					UpdateDirection();
				}
				
				unitTile.GetUnit().SetGridX(path.get(terrainIndex).GetGridX());
				unitTile.GetUnit().SetGridY(path.get(terrainIndex).GetGridY());
				mapHelper.CalculateLineOfSight();
				return new PointF(endX, endY);
			}
			
			return newPoint;
		}
	}
	
	private void UpdateDirection() {
		Terrain currentLocation = path.get(terrainIndex);
		Terrain nextLocation = path.get(terrainIndex + 1);
		
		if(nextLocation.GetGridX() > currentLocation.GetGridX()) {
			unitTile.GetUnit().SetDirection(Unit.RIGHT_DIRECTION);
		} else if(nextLocation.GetGridX() < currentLocation.GetGridX()) {
			unitTile.GetUnit().SetDirection(Unit.LEFT_DIRECTION);
		} else if(nextLocation.GetGridY() > currentLocation.GetGridY()) {
			unitTile.GetUnit().SetDirection(Unit.DOWN_DIRECTION);
		} else if(nextLocation.GetGridY() < currentLocation.GetGridY()) {
			unitTile.GetUnit().SetDirection(Unit.UP_DIRECTION);
		}
		
	}
}
