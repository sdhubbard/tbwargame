package game.TBSG;

import java.util.ArrayList;
import java.util.Date;

import game.BO.terrain.Terrain;
import game.BO.unit.Unit;
import game.tiles.UnitTile;
import game.utils.GameStateHelper;
import game.utils.UnitMovementAnimator;
import game.utils.GameStateHelper.GAMESTATE;
import game.utils.MapHelper;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.method.DateTimeKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;

public class TBSGView extends SurfaceView implements SurfaceHolder.Callback, OnTouchListener, OnGestureListener, OnScaleGestureListener {
	
	private RectF mapRect;
	private MapHelper mapHelper = null;
	private Unit selectedUnit = null;	
	private Date lastDrawDate = null;
	private Terrain selectedTerrain = null;
	private int TILE_DIMENSIONS = MapHelper.TILE_DIMENSIONS;
/*	private ValueAnimator xPositionAnimator = null;
	private ValueAnimator yPositionAnimator = null;*/
	private UnitMovementAnimator movementAnimator = null;	
	private Float zoomFactor = 1f;
	private Float zoomX = 0f;
	private Float zoomY = 0f;
	private Drawable mImage; 
	private Float minX = 0f;
	private Float maxX = 0f;
	private Float minY = 0f;
	private Float maxY = 0f;
	private Matrix currentMatrix = null;
	float[] maxPointsMapped = new float[4];
	
	class TBSGThread extends Thread {
		
		private SurfaceHolder mSurfaceHolder;
		private Handler mHandler;
		private boolean mRun = false;
		private int pulseLength = 1000; //One second
		
		
		private Context context;
		
		public TBSGThread(SurfaceHolder surfaceHolder, Context context,
				Handler handler) {
			this.context = context;
			
			mSurfaceHolder = surfaceHolder;
			mapHelper = new MapHelper(context);
			mapHelper.LoadMap(R.drawable.test_map_3);
			lastDrawDate = new Date();
			mapHelper.CalculateLineOfSight();
			mapRect = new RectF(0f,
							   0f,
					           mapHelper.getMapWidth() * TILE_DIMENSIONS,
					           mapHelper.getMapHeight() * TILE_DIMENSIONS);
		}
		
		public void setRunning(boolean b) {
			mRun = b;
		}
		
		public void doStart() {
			
		}
		
		public void run() {
			
			
			while (mRun) {
				Canvas c = null;
				
			/*	setScaleX(zoomFactor);
				setScaleY(zoomFactor);*/
				try {
					c = mSurfaceHolder.lockCanvas(null);
					synchronized (mSurfaceHolder) {
						//updatePhysics //We'll worry about this later.
						doDraw(c);
					}
				} catch (Exception e) {
					String exceptionString = e.toString();
				} finally {
					if (c != null) {
						mSurfaceHolder.unlockCanvasAndPost(c);
						
					}
				}
			}
		}
		
		public void doDraw(Canvas c) {
			int mapWidth = mapHelper.getMapWidth();
			int mapHeight = mapHelper.getMapHeight();
			float currentLeft = mapRect.left;
			float currentTop = mapRect.top;
			int numUnits = mapHelper.getNumberUnits();
			Paint selectedUnitPaint = new Paint();
			Paint notVisiblePaint = new Paint();
			LightingColorFilter selectedUnitLightingColorFilter = null;			
			Date currentDate = new Date();
			long dateDiff = currentDate.getTime() - lastDrawDate.getTime();
			int pulseRatio = (int)((255 * dateDiff) / pulseLength);		
			int SelectionColor =  Color.rgb(pulseRatio, 0, 0);
			int notVisibleColor = Color.rgb(200, 200, 200);
			UnitTile selectedUnitTile = null;
			float localZoom = zoomFactor;
			Matrix copy = null;
			RectF screenRectF = new RectF(0f, 0f, (float)getWidth(), (float)getHeight());
			float[] maxPoints = new float[] {screenRectF.left, screenRectF.top, screenRectF.right, screenRectF.bottom};
			
			c.save();
			//c.scale(localZoom, localZoom);
			c.scale(localZoom, localZoom, zoomX, zoomY);
			currentMatrix = c.getMatrix();
			copy = new Matrix(currentMatrix);
			copy.invert(copy);
			copy.mapPoints(maxPointsMapped, maxPoints);
			
			//c.translate((zoomX), (zoomY));
			if(dateDiff > pulseLength) {
				lastDrawDate = new Date();
			}

			selectedUnitLightingColorFilter = new LightingColorFilter(Color.WHITE, SelectionColor);
			
			selectedUnitPaint.setColorFilter(selectedUnitLightingColorFilter);
			notVisiblePaint.setColorFilter(new LightingColorFilter(Color.GRAY, Color.BLACK));
			
			c.drawColor(Color.BLACK);
			
			for(int x = 0; x < mapWidth; x++) {
				for(int y = 0; y < mapHeight; y++) {
					Bitmap currentBitmap = mapHelper.getMapTileImage(x,y);
					Terrain currentTerrain = mapHelper.getTerrain(x, y);
					
					if(currentBitmap != null) {
						if(currentTerrain.GetBestPath() != null && GameStateHelper.GetCurrentGameState() == GAMESTATE.SelectingTileToMove) {
							c.drawBitmap(currentBitmap, currentLeft + (x * TILE_DIMENSIONS), currentTop + (y * TILE_DIMENSIONS), selectedUnitPaint);
						} else {
							if(currentTerrain.getViewedBy().size() > 0) {
								c.drawBitmap(currentBitmap, currentLeft + (x * TILE_DIMENSIONS), currentTop + (y * TILE_DIMENSIONS), null);
							} else {
								c.drawBitmap(currentBitmap, currentLeft + (x * TILE_DIMENSIONS), currentTop + (y * TILE_DIMENSIONS), notVisiblePaint);
							}
						}
					} else {
						String newString = "test";
					}
				}
			}
			
			for(int i = 0; i < numUnits; i++) {
				UnitTile currentTile = mapHelper.getUnitTile(i);
				
				if(selectedUnit == currentTile.GetUnit()) {
					if(GameStateHelper.GetCurrentGameState() == GAMESTATE.SelectingTileToMove) {
						c.drawBitmap(currentTile.GetImage(),
								currentLeft + currentTile.GetUnit().GetGridX() * TILE_DIMENSIONS,
								currentTop + currentTile.GetUnit().GetGridY() * TILE_DIMENSIONS, selectedUnitPaint);
					} else if(GameStateHelper.GetCurrentGameState() == GAMESTATE.UnitMoving) {
						PointF currentPoint = movementAnimator.getPosition();
						float currentMovingX = currentPoint.x;
						float currentMovingY = currentPoint.y;
						
						c.drawBitmap(currentTile.GetImage(),
								currentLeft + currentMovingX,
								currentTop + currentMovingY, null);
					} else {
						c.drawBitmap(currentTile.GetImage(),
								currentLeft + currentTile.GetUnit().GetGridX() * TILE_DIMENSIONS,
								currentTop + currentTile.GetUnit().GetGridY() * TILE_DIMENSIONS, null);
					}
				} else {				
					c.drawBitmap(currentTile.GetImage(),
							currentLeft + currentTile.GetUnit().GetGridX() * TILE_DIMENSIONS,
							currentTop + currentTile.GetUnit().GetGridY() * TILE_DIMENSIONS, null);
				}
			}
			
			if(GameStateHelper.GetCurrentGameState() == GAMESTATE.ConfirmingMove) {
				ArrayList<Terrain> currentBestPath = selectedTerrain.GetBestPath();
				int pathLength = pulseLength / 2;
				long pathDateDiff = dateDiff / 2;
				
				if(pathDateDiff < pathLength / 5) { 
					mapHelper.DrawPathSegmentsFromBestPath(c, currentBestPath, currentLeft, currentTop, 0);
				} else if (pathDateDiff < (pathLength * 2) / 5) {
					mapHelper.DrawPathSegmentsFromBestPath(c, currentBestPath, currentLeft, currentTop, 1);
				} else if (pathDateDiff < (pathLength * 3) / 5) {
					mapHelper.DrawPathSegmentsFromBestPath(c, currentBestPath, currentLeft, currentTop, 2);
				} else if (pathDateDiff < (pathLength * 4) / 5) {
					mapHelper.DrawPathSegmentsFromBestPath(c, currentBestPath, currentLeft, currentTop, 3);
				} else {
					mapHelper.DrawPathSegmentsFromBestPath(c, currentBestPath, currentLeft, currentTop, 4);
				}
			}
			
			//c.save();
			//c.scale(zoomFactor, zoomFactor);
			//c.scale(localZoom, localZoom, 0f, 0f);
			c.restore();
		}
	}
	
	private Context mContext;
	private TBSGThread thread;
	private GestureDetector gestureDetector;
	private ScaleGestureDetector scaleGestureDetector;
	
	public TBSGView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		SurfaceHolder holder = getHolder();
		
		gestureDetector = new GestureDetector(context, this);
		scaleGestureDetector = new ScaleGestureDetector(context, this);
		holder.addCallback(this);
		setOnTouchListener(this);
		maxX = (float)getWidth();
		maxY = (float)getHeight();
		
		thread = new TBSGThread(holder, context, new Handler() {
			public void handleMessage(Message m) {
				
			}
		});
		
		setFocusable(true);
	}

	public TBSGThread getThread() {
		return thread;
	}
	
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub		
		thread.setRunning(true);
		thread.start();
		
		if(mapRect.width() < this.getWidth()) {
			float currentWidth = mapRect.width();
			
			mapRect.offset((this.getWidth() - currentWidth) / 2, 0);
		}
		
		if(mapRect.height() < this.getHeight()) {
			float currentHeight = mapRect.height();
			
			mapRect.offset(0, (this.getHeight() - currentHeight)/ 2);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		gestureDetector.onTouchEvent(event);
		scaleGestureDetector.onTouchEvent(event);
		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		float deltaX = distanceX;
		float deltaY = distanceY;
		RectF rectF = new RectF(mapRect);
		deltaX = deltaX / zoomFactor;
		deltaY = deltaY / zoomFactor;
		
		//the map rectangle's left border should be no more than the left side of the screen (0)
		//the map rectangle's right border should be no less than the right side of the screen.
		
		rectF.offset(-deltaX, -deltaY);
		mapRect = correctScroll(currentMatrix, rectF);	
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		float[] point = new float[]{e.getX(), e.getY(), 0f, 0f};
		Matrix tapMatrix = new Matrix(currentMatrix);
		
		tapMatrix.invert(tapMatrix);
		tapMatrix.mapPoints(point);
		Log.v("Test", "Pre Tap X: " + Float.toString(e.getX()) + " Pre Tap Y: " + Float.toString(e.getY()));
		Log.v("Test", "Tap X: " + Float.toString(point[2]) + " Tap Y: " + Float.toString(point[3]));
		
		int tapUpX = new Float(point[0]).intValue();
		int tapUpY = new Float(point[1]).intValue();
		float currentLeft = mapRect.left;
		float currentTop = mapRect.top;
		int tapGridX = (tapUpX - (int)currentLeft) / TILE_DIMENSIONS;
		int tapGridY = (tapUpY - (int)currentTop) / TILE_DIMENSIONS;
		
		if(tapGridX < 0 || tapGridY < 0 || tapGridX > mapHelper.getMapWidth() || tapGridY > mapHelper.getMapHeight()) {
			return false;
		}
		
		Unit currentUnit = mapHelper.GetUnitAtGrid(tapGridX, tapGridY);
		Terrain currentTerrain = mapHelper.GetTerrainAtGrid(tapGridX, tapGridY);
		GAMESTATE currentGameState = GameStateHelper.GetCurrentGameState();
		
		if(currentTerrain == null) {
			return false;
		}
		
		if(currentUnit != null) {
			mapHelper.ClearMovement();
			selectedUnit = currentUnit;
			mapHelper.CalculateReachableTerrain(selectedUnit);
			lastDrawDate = new Date();
			GameStateHelper.SetCurrentGameState(GAMESTATE.SelectingTileToMove);
		}
		//Temporarily commenting out check for game state.
		//if(GameStateHelper.GetCurrentGameState() == GAMESTATE.SelectingUnit) {

/*		}  */
		else if (GameStateHelper.GetCurrentGameState() == GAMESTATE.SelectingTileToMove) {
			if (currentTerrain.GetBestPath() != null) {
				selectedTerrain = currentTerrain;
				GameStateHelper.SetCurrentGameState(GAMESTATE.ConfirmingMove);
			}
		} else if(GameStateHelper.GetCurrentGameState() == GAMESTATE.ConfirmingMove) { //Movement confirmed.
			if (currentTerrain == selectedTerrain) {
				UnitTile currentTile = mapHelper.getUnitTile(selectedUnit);
				
				movementAnimator = new UnitMovementAnimator(selectedTerrain.GetBestPath(), currentTile, mapHelper);
				movementAnimator.Start();
				GameStateHelper.SetCurrentGameState(GAMESTATE.UnitMoving);
				selectedUnit.SetGridX(selectedTerrain.GetGridX());
				selectedUnit.SetGridY(selectedTerrain.GetGridY());
				
			}
		}
		
		return false;
	}

	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		// TODO Auto-generated method stub
		float deltaSpan =  detector.getCurrentSpan() - detector.getPreviousSpan();
		float zoomRatio = zoomFactor;
		float deltaX = zoomX;
		float deltaY = zoomY;
		Matrix testMatrix = new Matrix();		
		
		zoomFactor = zoomFactor + deltaSpan / 100;
		
		if(zoomFactor < 1) {
			zoomFactor  = 1f;
		} else if (zoomFactor > 4) {
			zoomFactor = 4f;
		}
		
		zoomRatio = zoomFactor / zoomRatio;
		
		zoomX = detector.getFocusX();
		zoomY = detector.getFocusY();		
		testMatrix.postScale(zoomFactor, zoomFactor, zoomX, zoomY);
		mapRect = correctScroll(testMatrix, mapRect);		
		
		return true;
	}
	
	public RectF correctScroll(Matrix matrix, RectF rectF) {
		RectF rectLocal = new RectF(rectF);
		Matrix invertMatrix = new Matrix();
		float[] rectPoints = new float[] {rectLocal.left, rectLocal.top, rectLocal.right, rectLocal.bottom};
		float[] screenPoints = new float[] {0,0, getWidth(), getHeight()};
		
		
		matrix.mapPoints(rectPoints);
		matrix.invert(invertMatrix);
		invertMatrix.mapPoints(screenPoints);
		
		float mLeft = rectPoints[0];
		float mTop = rectPoints[1];
		float mRight = rectPoints[2];
		float mBottom = rectPoints[3];			
		
		if(mLeft > 0) {			
			rectLocal.offset(-(rectLocal.left - screenPoints[0]), 0);
		} else if(mRight < getWidth()) {
			rectLocal.offset(screenPoints[2] - rectLocal.right, 0);
		}
		
		if(mTop > 0) {
			rectLocal.offset(0, -(rectLocal.top - screenPoints[1]));
		} else if(mBottom < getHeight()) {
			rectLocal.offset(0, screenPoints[3] - rectLocal.bottom);
		}

		return rectLocal;
	}	

	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		float deltaSpan =  detector.getCurrentSpan() - detector.getPreviousSpan();
		
		zoomFactor = zoomFactor + deltaSpan / 100;
		
		if(zoomFactor < 1) {
			zoomFactor  = 1f;
		} else if (zoomFactor > 4) {
			zoomFactor = 4f;
		}
		
		return true;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {
		// TODO Auto-generated method stub
		
	}
}
