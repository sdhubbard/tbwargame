package game.BO.terrain;

import game.BO.unit.Unit;
import game.BO.unit.Unit.LOCOMOTION;

import java.util.ArrayList;

public abstract class Terrain {
	private int gridX = 0;
	private int gridY = 0;
	private boolean reachable = false;
	private boolean viewable = false;
	private ArrayList<Terrain> bestPath = null;
	private int bestPathPoints = 0;
	private int viewablePoints = 0;
	protected String terrainType = "Default";
	protected int tracksPenalty = 0;
	protected int flightPenalty = 1;
	protected int defenseModifier = 0;
	protected int viewPenalty = 0;
	protected ArrayList<Unit> viewedBy = null;
	protected Terrain aboveTile = null;
	protected Terrain belowTile = null;
	protected Terrain leftTile = null;
	protected Terrain rightTile = null;
	
	public Terrain() {
		viewedBy = new ArrayList<Unit>();
	}
	
	//Just worrying about tracks for now.
	public int GetPenalty(LOCOMOTION locomotion) {
		switch (locomotion) {
		case AIR:
		case WATER:
		case WHEELS:	
			return 1;
		case TRACKS:
			return tracksPenalty;
		default:
			break;
		}
		
		return 1;
	}
	
	public void SetAboveTerrain(Terrain terrain) {
		this.aboveTile = terrain;
	}
	
	public Terrain GetAboveTerrain() {
		return this.aboveTile;
	}
	
	public void SetBelowTerrain(Terrain terrain) {
		this.belowTile = terrain;
	}
	
	public Terrain GetBelowTerrain() {
		return this.belowTile;
	}
	public void SetLeftTerrain(Terrain terrain) {
		this.leftTile = terrain;
	}
	
	public Terrain GetLeftTerrain() {
		return this.leftTile;
	}
	public void SetRightTerrain(Terrain terrain) {
		this.rightTile = terrain;
	}
	
	public Terrain GetRightTerrain() {
		return this.rightTile;
	}
	
	public ArrayList<Unit> getViewedBy()
	{
		return viewedBy;	
	}
	
	public boolean GetIsViewable() {
		return viewable;
	}
	
	public void SetIsViewable(Boolean viewable) {
		this.viewable = viewable;
	}
	
	public boolean GetReachable() {
		return reachable;
	}
	
	public void SetReachable(Boolean reachable) {
		this.reachable = reachable;
	}
	
	public String GetTerrainType() {
		return this.terrainType;
	}
	
	public int GetDefenseModifier() {
		return this.defenseModifier;
	}
	
	public int GetBestPathPoints() {	
		return bestPathPoints;
	}
	
	public void SetBestPathPoints(int bestPathPoints) {
		this.bestPathPoints = bestPathPoints;
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
	
	
	
	public ArrayList<Terrain> GetBestPath() {
		return bestPath;
	}
	
	public void SetBestPath(ArrayList<Terrain> bestPath) {
		this.bestPath = bestPath;
	}
	
	public int GetViewablePoints() {
		return viewablePoints;
	}
	
	public void SetViewablePoints(int viewablePoints) {
		this.viewablePoints = viewablePoints;
	}
	
	public int GetViewPenalty()
	{
		return this.viewPenalty;
	}
}
