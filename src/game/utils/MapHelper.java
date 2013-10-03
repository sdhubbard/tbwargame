package game.utils;

import game.BO.assets.Factory;
import game.BO.terrain.Forest;
import game.BO.terrain.Grass;
import game.BO.terrain.Mountain;
import game.BO.terrain.Road;
import game.BO.terrain.Terrain;
import game.BO.terrain.Water;
import game.BO.unit.Tank;
import game.BO.unit.Unit;
import game.BO.unit.Unit.LOCOMOTION;
import game.tiles.FactoryTile;
import game.tiles.ForestTile;
import game.tiles.GrassTile;
import game.tiles.MountainTile;
import game.tiles.RoadTile;
import game.tiles.TankTile;
import game.tiles.TerrainTile;
import game.tiles.UnitTile;
import game.tiles.WaterTile;
import game.utils.GameStateHelper.GAMESTATE;

import java.util.ArrayList;
import java.util.Date;

import javax.crypto.Cipher;

import android.R;
import android.R.drawable;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;

public class MapHelper {
	public static final int TILE_DIMENSIONS = 40;
	
	private ArrayList<ArrayList<Terrain>> terrainMap = new ArrayList<ArrayList<Terrain>>();
	private ArrayList<ArrayList<TerrainTile>> terrainTileMap = new ArrayList<ArrayList<TerrainTile>>();
	private ArrayList<UnitTile> unitMap = new ArrayList<UnitTile>();
	private ArrayList<Terrain> currentPath = null;
	private Context context = null;
	private int mapWidth = 0;
	private int mapHeight = 0;
	private int numUnits = 10;
	//Might move this functionality into different class later.
	private Bitmap pathCurve = null;
	private Bitmap pathCurve2 = null;
	private Bitmap pathCurve3 = null;
	private Bitmap pathCurve4 = null;
	private Bitmap pathCurve5 = null;
	
	private Bitmap pathArrow1 = null;
	private Bitmap pathArrow2 = null;
	private Bitmap pathArrow3 = null;
	private Bitmap pathArrow4 = null;
	private Bitmap pathArrow5 = null;
	
	private Bitmap pathStraight = null;
	private Bitmap pathStraight2 = null;
	private Bitmap pathStraight3 = null;
	private Bitmap pathStraight4 = null;
	private Bitmap pathStraight5 = null;
	private int red1 = Color.argb(255, 255, 0, 0);
	private int red2 = Color.argb(255, 255, 51, 51);
	private int red3 = Color.argb(255, 255, 102, 102);
	private int red4 = Color.argb(255, 255, 153, 153);
	private int red5 = Color.argb(255, 255, 204, 204);
	
	private final int[] pathSourceColors = new int[] {red1, red5, red4, red3, red2};
	private final int[] pathSourceColors2 = new int[] {red5, red4, red3, red2, red1};
	private final int[] pathSourceColors3 = new int[] {red4, red3, red2, red1, red5};
	private final int[] pathSourceColors4 = new int[] {red3, red2, red1, red5, red4};
	private final int[] pathSourceColors5 = new int[] {red2, red1, red5, red4, red3};
	
	public MapHelper(Context context) {
		this.context = context;
	}
	
	public int getNumberUnits() {
		return numUnits;
	}
	
	public Terrain getTerrain(int x, int y) {
		return terrainMap.get(x).get(y);
	}
	
	public Bitmap getMapTileImage(int x, int y) {
		return terrainTileMap.get(x).get(y).GetImage();
	}
	
	public UnitTile getUnitTile(int index) {
		return unitMap.get(index);
	}
	
	public UnitTile getUnitTile(Unit selectedUnit) {
		for(UnitTile current : unitMap) {
			if(current.GetUnit() == selectedUnit) {
				return current;
			}
		}		
		
		return null;
	}
	
	public int getMapWidth() {
		return mapWidth;
	}
	
	public int getMapHeight() {
		return mapHeight;
	}
	
	public Bitmap GetPathCurve() {
		return pathCurve;
	}
	
	public Bitmap GetPathStraight()
	{
		return pathStraight;
	}
	
	public void GeneratePaths() {
		Resources res = context.getResources();
		
		

		BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
		
		bitmapFactoryOptions.inPreferQualityOverSpeed = true;
		bitmapFactoryOptions.inScaled = false;	
		
		pathStraight = BitmapFactory.decodeResource(res,game.TBSG.R.drawable.path_straight, bitmapFactoryOptions);
		pathStraight2 = BitmapFactory.decodeResource(res,game.TBSG.R.drawable.path_straight2, bitmapFactoryOptions);
		pathStraight3 = BitmapFactory.decodeResource(res,game.TBSG.R.drawable.path_straight3, bitmapFactoryOptions);
		pathStraight4 = BitmapFactory.decodeResource(res,game.TBSG.R.drawable.path_straight4, bitmapFactoryOptions);
		pathStraight5 = BitmapFactory.decodeResource(res,game.TBSG.R.drawable.path_straight5, bitmapFactoryOptions);
		
		pathArrow1 = BitmapFactory.decodeResource(res, game.TBSG.R.drawable.path_arrow, bitmapFactoryOptions);
		pathArrow2 = BitmapFactory.decodeResource(res, game.TBSG.R.drawable.path_arrow2, bitmapFactoryOptions);
		pathArrow3 = BitmapFactory.decodeResource(res, game.TBSG.R.drawable.path_arrow3, bitmapFactoryOptions);
		pathArrow4 = BitmapFactory.decodeResource(res, game.TBSG.R.drawable.path_arrow4, bitmapFactoryOptions);
		pathArrow5 = BitmapFactory.decodeResource(res, game.TBSG.R.drawable.path_arrow5, bitmapFactoryOptions);
		
		pathCurve = GenerateCurvePath(pathSourceColors);
		pathCurve2 = GenerateCurvePath(pathSourceColors2);
		pathCurve3 = GenerateCurvePath(pathSourceColors3);
		pathCurve4 = GenerateCurvePath(pathSourceColors4);
		pathCurve5 = GenerateCurvePath(pathSourceColors5);
	}
	
	private Bitmap GenerateCurvePath(int[] sourceColors) {
		Bitmap currentCurve = Bitmap.createBitmap(40, 40, Config.ARGB_8888);
		Canvas pathCurveCanvas = new Canvas(currentCurve);
		float arcSegmentDegrees = 6f;
		int currentColorIndex = 0;
		Paint darkRedPaint = new Paint();
		
		darkRedPaint.setARGB(255, 194, 0, 0);
		darkRedPaint.setStrokeWidth(0);
		darkRedPaint.setStyle(Style.STROKE);	
		
		//Drawing inner curve.
		for(int r = 12; r <= 20; r++) {
			RectF currentOval = new RectF(-r,-r,r,r);
			
			for(float a = 0; a < 15; a++) {
				Paint currentPaint = new Paint();
				
				currentPaint.setColor(sourceColors[currentColorIndex]);
				currentPaint.setStrokeWidth(1.5f);
				currentPaint.setStyle(Style.STROKE);
				
				pathCurveCanvas.drawArc(currentOval,
										(a) * arcSegmentDegrees,
										(a + 1) * arcSegmentDegrees, false, currentPaint);
				currentColorIndex = currentColorIndex + 1;
				if(currentColorIndex > sourceColors.length - 1) {
					currentColorIndex = 0;
				}
			}
			
			currentColorIndex = currentColorIndex + 1;
			if(currentColorIndex > sourceColors.length - 1) {
				currentColorIndex = 0;
			}
		}
		
		//Drawing outer curve.
		for(int r = 21; r <= 29; r++) {
			RectF currentOval = new RectF(-r,-r,r,r);
			
			for(int a = 0; a < 15; a++) {
				Paint currentPaint = new Paint();
				
				currentPaint.setColor(sourceColors[currentColorIndex]);
				currentPaint.setStrokeWidth(1.5f);
				currentPaint.setStyle(Style.STROKE);
				
				pathCurveCanvas.drawArc(currentOval,
										(a) * arcSegmentDegrees,
										(a + 1) * arcSegmentDegrees, false, currentPaint);
				currentColorIndex = currentColorIndex + 1;
				if(currentColorIndex > sourceColors.length - 1) {
					currentColorIndex = 0;
				}
			}
			
			currentColorIndex = currentColorIndex - 1;
			if(currentColorIndex < 0) {
				currentColorIndex = pathSourceColors.length - 1;
			}
		}
		
		//drawing border
		pathCurveCanvas.drawArc(new RectF(-11, -11, 11, 11),
				 0,
				 90,
				 false, darkRedPaint);
		
		
		pathCurveCanvas.drawArc(new RectF(-30, -30, 30, 30),
								 0,
								 90,
								 false, darkRedPaint);
		pathCurveCanvas.save();
		
		return currentCurve;
	}
	
	public void DrawPathSegmentsFromBestPath(Canvas c, ArrayList<Terrain> path,
											float leftOffset, float topOffset, int frame) {
		Bitmap currentStraight = null;
		Bitmap currentCurve = null;
		Bitmap currentArrow = null;			
		
		switch (frame) {
		case 0:
			currentStraight = pathStraight;
			currentCurve = pathCurve;
			currentArrow = pathArrow1;
			break;
		case 1:
			currentStraight = pathStraight2;
			currentCurve = pathCurve2;
			currentArrow = pathArrow2;
			break;
		case 2:
			currentStraight = pathStraight3;
			currentCurve = pathCurve3;
			currentArrow = pathArrow3;
			break;
		case 3:
			currentStraight = pathStraight4;
			currentCurve = pathCurve4;
			currentArrow = pathArrow4;
			break;
		case 4:
			currentStraight = pathStraight5;
			currentCurve = pathCurve5;
			currentArrow = pathArrow5;
			break;
		default:
			break;
		}
		
		for(int index = 1; index < path.size(); index++) {
			Terrain previousTerrain = path.get(index - 1);
			Terrain currentTerrain = path.get(index);
			Terrain nextTerrain = null;
			float currentX = currentTerrain.GetGridX() * TILE_DIMENSIONS + leftOffset;
			float currentY = currentTerrain.GetGridY() * TILE_DIMENSIONS + topOffset;
			int transformIncrement = 20;
			
			if(index < path.size() - 1) {
				nextTerrain = path.get(index + 1);
			}
			
			c.save();
			if(previousTerrain == currentTerrain.GetLeftTerrain()) {
				if(nextTerrain == null) {
					c.drawBitmap(currentArrow, currentX, currentY, null);
				} else if(nextTerrain == currentTerrain.GetRightTerrain()) {
					c.drawBitmap(currentStraight, currentX, currentY, null);
				} else if(nextTerrain == currentTerrain.GetAboveTerrain()) {
					c.drawBitmap(currentCurve, currentX, currentY, null); //Default curve
				} else if(nextTerrain == currentTerrain.GetBelowTerrain()) {
					c.scale(1, -1, currentX + transformIncrement, currentY + transformIncrement);
					c.drawBitmap(currentCurve, currentX, currentY, null);
				}			
			} else if(previousTerrain == currentTerrain.GetAboveTerrain()) {
				 if(nextTerrain == null) {
					c.rotate(90, currentX + transformIncrement, currentY + transformIncrement);
					c.drawBitmap(currentArrow, currentX, currentY, null);
				} else if(nextTerrain == currentTerrain.GetRightTerrain()) {					
					c.rotate(90, currentX + transformIncrement, currentY + transformIncrement);
					c.drawBitmap(currentCurve, currentX, currentY, null);
				} else if(nextTerrain == currentTerrain.GetLeftTerrain()) {
					c.scale(-1, 1, currentX + transformIncrement, currentY + transformIncrement);
					c.rotate(90, currentX + transformIncrement, currentY + transformIncrement);
					c.drawBitmap(currentCurve, currentX, currentY, null);
				} else if(nextTerrain == currentTerrain.GetBelowTerrain()) {					
					c.rotate(90, currentX + transformIncrement, currentY + transformIncrement);
					c.drawBitmap(currentStraight, currentX, currentY, null);
				}	
			} else if(previousTerrain == currentTerrain.GetBelowTerrain()) {
				if(nextTerrain == null) {
					c.rotate(-90, currentX + transformIncrement, currentY + transformIncrement);
					c.drawBitmap(currentArrow, currentX, currentY, null);
				} else if(nextTerrain == currentTerrain.GetAboveTerrain()) {					
					c.rotate(-90, currentX + transformIncrement, currentY + transformIncrement);
					c.drawBitmap(currentStraight, currentX, currentY, null);
				} else if(nextTerrain == currentTerrain.GetLeftTerrain()) {
					c.rotate(-90, currentX + transformIncrement, currentY + transformIncrement);
					c.drawBitmap(currentCurve, currentX, currentY, null);
				} else if(nextTerrain == currentTerrain.GetRightTerrain()) {
					c.scale(-1, 1, currentX + transformIncrement, currentY + transformIncrement);
					c.rotate(-90, currentX + transformIncrement, currentY + transformIncrement);
					c.drawBitmap(currentCurve, currentX, currentY, null);
				}
			} else if(previousTerrain == currentTerrain.GetRightTerrain()) {
				 if(nextTerrain == null) {
						c.rotate(180, currentX + transformIncrement, currentY + transformIncrement);
						c.drawBitmap(currentArrow, currentX, currentY, null);
				} else if(nextTerrain == currentTerrain.GetLeftTerrain()) {
					c.rotate(180, currentX + transformIncrement, currentY + transformIncrement);
					c.drawBitmap(currentStraight, currentX, currentY, null);
				} else if(nextTerrain == currentTerrain.GetAboveTerrain()) {
					c.scale(-1, 1, currentX + transformIncrement, currentY + transformIncrement);
					c.drawBitmap(currentCurve, currentX, currentY, null);
				} else if(nextTerrain == currentTerrain.GetBelowTerrain()) {
					c.scale(-1, -1, currentX + transformIncrement, currentY + transformIncrement);
					c.drawBitmap(currentCurve, currentX, currentY, null);
				}
			}
			
			c.restore();	
		}
			

					
	}
	
	public void LoadMap(int mapImageId) {
		Resources res = context.getResources();

		Bitmap mapBitmap = BitmapFactory.decodeResource(res, mapImageId, TerrainTile.GetBitmapOptions());
		
		mapWidth = mapBitmap.getWidth();
		mapHeight = mapBitmap.getHeight();
		GeneratePaths();
		mapBitmap.prepareToDraw();
		//Initial instantiation of terrain business objects.
		for(int x = 0; x < mapWidth; x++) {
			terrainMap.add(new ArrayList<Terrain>());
			
			for(int y = 0; y < mapHeight; y++) {
				int currentColor = mapBitmap.getPixel(x, y);
				
				Integer currentColorInt = mapBitmap.getPixel(x, y);
				String hexString = Integer.toHexString(currentColor);
				int redValue = Color.red(currentColorInt);
				int greenValue = Color.green(currentColorInt);
				int blueValue = Color.blue(currentColorInt);
				Terrain newTerrain = null;
				
				if(currentColorInt == Grass.MAP_COLOR) {
					newTerrain = new Grass();
				} else if(currentColorInt == Mountain.MAP_COLOR) {
					newTerrain = new Mountain();
				} else if(currentColorInt == Water.MAP_COLOR) {
					newTerrain = new Water();
				} else if(currentColorInt == Forest.MAP_COLOR) {
					newTerrain = new Forest();
				} else if(currentColorInt == Road.MAP_COLOR) {
					newTerrain = new Road();
				} else if(currentColorInt == Factory.MAP_COLOR) {
					newTerrain = new Factory();
				} else {
					newTerrain = new Grass();
				}
				
				
				newTerrain.SetGridX(x);
				newTerrain.SetGridY(y);
				terrainMap.get(x).add(newTerrain);				
			}
		}
		
		for(int x = 0; x < mapWidth; x++) {
			for(int y = 0; y < mapHeight; y++) {
				Terrain currentTerrain = terrainMap.get(x).get(y);
				
				if(x > 0) {
					currentTerrain.SetLeftTerrain(terrainMap.get(x - 1).get(y));
				}
				
				if(x < mapWidth - 1) {
					currentTerrain.SetRightTerrain(terrainMap.get(x + 1).get(y));
				}
				
				if(y > 0) {
					currentTerrain.SetAboveTerrain(terrainMap.get(x).get(y - 1));
				}
				
				if(y < mapHeight - 1) {
					currentTerrain.SetBelowTerrain(terrainMap.get(x).get(y + 1));
				}
			}
		}
		
		for(int x = 0; x < mapWidth; x++) {
			terrainTileMap.add(new ArrayList<TerrainTile>());
			
			for(int y = 0; y < mapHeight; y++) {
				Terrain currentTerrain = terrainMap.get(x).get(y);
				
				if(currentTerrain instanceof Forest) {
					terrainTileMap.get(x).add(new ForestTile(context, currentTerrain));
				} else if(currentTerrain instanceof Grass) {
					terrainTileMap.get(x).add(new GrassTile(context, currentTerrain));
				} else if(currentTerrain instanceof Mountain) {
					terrainTileMap.get(x).add(new MountainTile(context, currentTerrain));
				} else if(currentTerrain instanceof Road) {
					terrainTileMap.get(x).add(new RoadTile(context, currentTerrain));
				} else if(currentTerrain instanceof Water) {
					terrainTileMap.get(x).add(new WaterTile(context, currentTerrain));
				} else if(currentTerrain instanceof Factory) {
					terrainTileMap.get(x).add(new FactoryTile(context, currentTerrain));
				}
			}
		}
		
		for(int i = 0; i < numUnits; i++) {
			Tank newTank = new Tank();
			
			
			newTank.SetGridX(new Double(Math.floor((Math.random() * mapWidth))).intValue());
			newTank.SetGridY(new Double(Math.floor((Math.random() * mapHeight))).intValue());
			
			unitMap.add(new TankTile(context, newTank));
		}
	}

	public void ClearMovement() {
		for(int x = 0; x < mapWidth; x++) {
			for(int y = 0; y < mapHeight; y++) {
				Terrain currentTerrain = terrainMap.get(x).get(y);
				
				currentTerrain.SetBestPath(null); //Research this a bit more.
				currentTerrain.SetBestPathPoints(0);
			}
		}
	}
	
	public Unit GetUnitAtGrid(int x, int y) {
		int numUnits = this.getNumberUnits();
		
		for(int i = 0; i < numUnits; i++) {
			UnitTile currentTile = getUnitTile(i);
			Unit currentUnit =  currentTile.GetUnit();

			if(currentUnit.GetGridX() == x && currentUnit.GetGridY() == y) {
				return currentUnit;
			}
		}
		
		return null;
	}
	
	public Terrain GetTerrainAtGrid(int x, int y)
	{
		if(x > terrainMap.size() - 1)
		{
			return null;
		}
		
		if(y > terrainMap.get(x).size() - 1) {
			return null;
		}
		
		return terrainMap.get(x).get(y);
	}
	
	public void CalculateLineOfSight() {
		for(int x = 0; x < mapWidth; x++) {
			for(int y = 0; y < mapHeight; y++) {
				Terrain currentTerrain = GetTerrainAtGrid(x, y);
				
				currentTerrain.getViewedBy().clear();
			}
		}
		
		//try {
			for(UnitTile currentTile : unitMap) {
				Unit unit = currentTile.GetUnit();
				
				CalculateLineOfSight(unit);
			}
/*		} catch (Exception e)
		{
			
		}*/
	}
	
	private void CalculateLineOfSight(Unit unit) {
		int unitX = unit.GetGridX();
		int unitY = unit.GetGridY();
		int viewXMax = unitX + unit.GetVisualRange();
		int viewXMin = unitX - unit.GetVisualRange();
		int viewYMax = unitY + unit.GetVisualRange();
		int viewYMin = unitY - unit.GetVisualRange();
		
		if (viewXMax > mapWidth - 1) {
			viewXMax = mapWidth - 1;
		}
		
		if (viewXMin < 0) {
			viewXMin = 0;
		}
		
		if (viewYMax > mapHeight - 1) {
			viewYMax =  mapHeight - 1;
		}
		
		if (viewYMin < 0) {
			viewYMin = 0;
		}
		
		for(int x = viewXMin; x <= viewXMax; x++) {
			for(int y = viewYMin; y <= viewYMax; y++) {
				if(x >= mapWidth || y >= mapHeight) {
					continue;
				}
				
				Terrain currentTerrain = GetTerrainAtGrid(x, y);
				
				if(!currentTerrain.getViewedBy().contains(unit)) {
					CalcIsVisible(unit, currentTerrain);
				}
			}
		}		
	}
	
	public void CalcIsVisible(Unit unit, Terrain terrain) {
		int unitX = unit.GetGridX();
		int unitY = unit.GetGridY();
		int terrainX = terrain.GetGridX();
		int terrainY = terrain.GetGridY();
		int deltaX = unitX - terrainX;
		int deltaY = unitY - terrainY;
		int currentVisiblePoints = unit.GetVisualRange();
		int slopeRise = deltaY;
		int slopeRun = deltaX;
		int gridXMin = 0;
		int gridXMax = 0;
		int gridYMin = 0;
		int gridYMax = 0;
		float slope = 0;
		float terrainMidX = terrainX + .5f;
		float terrainMidY = terrainY + .5f;
		float unitMidX = unitX + .5f;
		float unitMidY = unitY + .5f;
		ArrayList<Terrain> lineTiles = new ArrayList<Terrain>();
		Terrain unitTerrain = GetTerrainAtGrid(unitX, unitY);
		
		if(Math.abs(deltaX) + Math.abs(deltaY) > currentVisiblePoints) {
			return;
		}
		
		//Step 1: Get Line equation
		if(slopeRun == 0) {
			slopeRun = 0;
			slopeRise = 1;
		} else if(slopeRise == 0) {
			slopeRise = 0;
			slopeRun = 1;
		} else {		
			if(Math.abs(deltaY) > Math.abs(deltaX)) {
				for(int testDivisor = Math.abs(deltaX); testDivisor > 0; testDivisor--) {
					if(Math.abs(deltaY) % testDivisor == 0 && Math.abs(deltaX) % testDivisor == 0) {
						slopeRise = deltaY / testDivisor;
						slopeRun = deltaX / testDivisor;
						break;
					}
				}
			} else if(Math.abs(deltaX) > Math.abs(deltaY)) {
				for(int testDivisor = Math.abs(deltaY); testDivisor > 0; testDivisor--) {
					if(Math.abs(deltaY) % testDivisor == 0 && Math.abs(deltaX) % testDivisor == 0) {
						slopeRise = deltaY / testDivisor;
						slopeRun = deltaX / testDivisor;
						break;
					}
				}
			}
		}
		
		if(unitX >= terrainX) {
			gridXMax = unitX;
			gridXMin = terrainX;
		} else {
			gridXMax = terrainX;
			gridXMin = unitX;
		}
		
		if(unitY >= terrainY) {
			gridYMax = unitY;
			gridYMin = terrainY;
		} else {
			gridYMax = terrainY;
			gridYMin = unitY;
		}		

		//lineTiles.add(terrain);
		slope = ((float)slopeRise) / ((float)slopeRun);
		//Step 2: Get terrain tiles along line.		
		for(int currentGridX = gridXMin; currentGridX <= gridXMax; currentGridX++) {
			float yAtX = 1;
			float yAtXplus = 1;
			
			if(slopeRise != 0 && slopeRun != 0) {			
				yAtX = terrainMidY + ((currentGridX) - terrainMidX) * slope;
				yAtXplus = terrainMidY + (1 + ((currentGridX) - terrainMidX)) * slope;
			}
			
			for(int currentGridY = gridYMin; currentGridY <= gridYMax; currentGridY++) {
				Terrain testTerrain = GetTerrainAtGrid(currentGridX, currentGridY);
				
				if(testTerrain != unitTerrain) {
					//Case 1: View Line is a vertical or horizontal line.
					if(deltaX == 0 || deltaY == 0) {
						lineTiles.add(testTerrain);
					} else {
						//For case 4:
						float deltaYAtX = testTerrain.GetGridY() - yAtX;
						float deltaYAtXplus = testTerrain.GetGridY() - yAtXplus;
						
						//Case 2: view line intersects upper left and lower right corners	
						if(yAtX == testTerrain.GetGridY()  && yAtXplus == testTerrain.GetGridY() + 1) {					
							lineTiles.add(testTerrain);
						}
						//Case 3: view line intersects lower left and upper right corners
						else if(yAtX == testTerrain.GetGridY() + 1  && yAtXplus == testTerrain.GetGridY()) {
							lineTiles.add(testTerrain);
						}
						//Case 4: line intersects left side of tile.
						else if((yAtX > testTerrain.GetGridY() && yAtX < testTerrain.GetGridY() + 1)) { 
							lineTiles.add(testTerrain);							
						}
						//Case 5: line intersects right side
						else if((yAtXplus > testTerrain.GetGridY() && yAtXplus < testTerrain.GetGridY() + 1)) {							
							lineTiles.add(testTerrain);
						}				
/*						//Case 6: line is above upper left corner and below upper right corner (or vice versa). Indicates
						//line passing through horizontal boundaries with out passing between vertical boundaries.
						else if(Math.copySign(1, deltaYAtX) != Math.copySign(1, deltaYAtXplus)) {
							lineTiles.add(testTerrain);
						} */
						//Case 6: intersecting top boundary
						else if((yAtX < testTerrain.GetGridY() && yAtXplus > testTerrain.GetGridY()) ||
								(yAtX > testTerrain.GetGridY() && yAtXplus < testTerrain.GetGridY())) {
							lineTiles.add(testTerrain);
						}
						//Case 7: interesting bottom boundary
						else if((yAtX < testTerrain.GetGridY() + 1 &&  yAtXplus > testTerrain.GetGridY() + 1) ||
								(yAtX > testTerrain.GetGridY() + 1 &&  yAtXplus < testTerrain.GetGridY() + 1)) {
							lineTiles.add(testTerrain);
						}
					}
				}
			}
		}
		
		//Step 3: Calculate visual range across line.
		for(Terrain lineTerrain: lineTiles) {
			if(unitTerrain != lineTerrain) {
				currentVisiblePoints = currentVisiblePoints - (1 + lineTerrain.GetViewPenalty());
			}
			
			if(currentVisiblePoints < 0) {
				return;
			}
		}		
		
		lineTiles.add(unitTerrain);
		for (Terrain line : lineTiles) {
			if(!line.getViewedBy().contains(unit)) {
				line.getViewedBy().add(unit);
			}
		}
	}
	
	public void CalculateReachableTerrain(Unit unit) {
		int gridX = unit.GetGridX();
		int gridY = unit.GetGridY();
		int movementPoints = unit.GetSpeed();
		LOCOMOTION locomotion = unit.GetLocomotion();
		ArrayList<Terrain> reachableTerrain = new ArrayList<Terrain>();
		Terrain currentTerrain = terrainMap.get(gridX).get(gridY);
		ArrayList<Terrain> path = new ArrayList<Terrain>();
		path.add(currentTerrain);
		
		if(currentTerrain.GetLeftTerrain() != null) //Going Left 
		{
			CalculateFromTerrain(currentTerrain.GetLeftTerrain(), movementPoints, locomotion, (ArrayList<Terrain>)path.clone());
		}
		
		if(currentTerrain.GetRightTerrain() != null) //Going Right 
		{
			CalculateFromTerrain(currentTerrain.GetRightTerrain(), movementPoints, locomotion, (ArrayList<Terrain>)path.clone());
		}
		
		if(currentTerrain.GetAboveTerrain() != null) //Going Above 
		{
			CalculateFromTerrain(currentTerrain.GetAboveTerrain(), movementPoints, locomotion, (ArrayList<Terrain>)path.clone());
		}
		
		if(currentTerrain.GetBelowTerrain() != null) //Going Below 
		{
			CalculateFromTerrain(currentTerrain.GetBelowTerrain(), movementPoints, locomotion, (ArrayList<Terrain>)path.clone());
		}
	}
	
	private void CalculateFromTerrain(Terrain terrain,
									  int movePoints,
									  LOCOMOTION locomotion,
									  ArrayList<Terrain> path) {
		int gridX = terrain.GetGridX();
		int gridY = terrain.GetGridY();
		int newMovementPoints = movePoints - 1;
		
		if(terrain.GetPenalty(locomotion) == - 1 ||movePoints < 1 || GetUnitAtGrid(gridX, gridY) != null) {
			//Can go no farther at this point
			return;
		}
		
		
		if(terrain.GetBestPath() == null) {
			path.add(terrain);
			terrain.SetBestPath((ArrayList<Terrain>)path.clone()); //Research this a bit more.
			terrain.SetBestPathPoints(movePoints);
			
		} else if(movePoints > terrain.GetBestPathPoints()) {
			path.add(terrain);
			terrain.SetBestPath((ArrayList<Terrain>)path.clone());
			terrain.SetBestPathPoints(movePoints);
			
		}
		
		newMovementPoints = newMovementPoints - terrain.GetPenalty(locomotion);
		
		if(terrain.GetLeftTerrain() != null && !path.contains(terrain.GetLeftTerrain())) //Going Left 
		{
			int leftMovementPoints = newMovementPoints - terrain.GetLeftTerrain().GetPenalty(locomotion);
			
			CalculateFromTerrain(terrain.GetLeftTerrain(), newMovementPoints, locomotion, (ArrayList<Terrain>)terrain.GetBestPath().clone());
		}
		
		if(terrain.GetRightTerrain() != null && !path.contains(terrain.GetRightTerrain())) //Going Right 
		{
			int rightMovementPoints = newMovementPoints - terrain.GetRightTerrain().GetPenalty(locomotion);
			
			CalculateFromTerrain(terrain.GetRightTerrain(), newMovementPoints, locomotion, (ArrayList<Terrain>)terrain.GetBestPath().clone());
		}
		
		if(terrain.GetAboveTerrain() != null && !path.contains(terrain.GetAboveTerrain())) //Going Above 
		{
			int aboveMovementPoints = newMovementPoints - terrain.GetAboveTerrain().GetPenalty(locomotion);
			
			CalculateFromTerrain(terrain.GetAboveTerrain(), newMovementPoints, locomotion, (ArrayList<Terrain>)terrain.GetBestPath().clone());
		}
		
		if(terrain.GetBelowTerrain() != null && !path.contains(terrain.GetBelowTerrain())) //Going Below 
		{
			int belowMovementPoints = newMovementPoints - terrain.GetBelowTerrain().GetPenalty(locomotion);
			
			CalculateFromTerrain(terrain.GetBelowTerrain(), newMovementPoints, locomotion, (ArrayList<Terrain>)terrain.GetBestPath().clone());
		}		
	}
}
