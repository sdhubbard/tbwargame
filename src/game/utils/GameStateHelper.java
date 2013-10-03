package game.utils;

import game.BO.unit.Unit;

public class GameStateHelper {
  private static Unit selectedUnit = null;
  private static GAMESTATE currentGameState = null;
  
  public enum GAMESTATE {
	  SelectingUnit, //No unit is selected, currently awaiting user to select a unit.
	  SelectingTileToMove, //A unit is selected, currently awaiting user to select a valid location.
	  ConfirmingMove, //Trail is marked between the unit's current position and the position clicked on in the previous state
	  UnitMoving, //A valid location is selected, and the unit is animation to that location.
  }
  
  public static Unit GetSelectedUnit() {
	  return selectedUnit;
  }
  
  public static void SetSelectedUnit(Unit unit) {
	  selectedUnit = unit;
  }
  
  public static GAMESTATE GetCurrentGameState() {
	  return currentGameState;
  }
  
  public static void SetCurrentGameState(GAMESTATE gameState) {
	  currentGameState = gameState;
  }
  
}
