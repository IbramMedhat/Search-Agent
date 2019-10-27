
public class EndGameProblem extends Problem  {
	private String grid;
	private String warriorsPositions;
	private String stonesPositions;
	private String thanosPosition;
	private EndGameGrid endGameGrid;
	
	public EndGameProblem(String grid) {
		super();
		this.grid = grid;
		
		// Parsing the grid to get warriors, stones and Thanos positions
		String[] gridSplitted = grid.split(";");
		this.warriorsPositions = String.join("", gridSplitted[4]);
		this.stonesPositions = String.join("", gridSplitted[3]);
		this.thanosPosition = String.join("", gridSplitted[2]);
		
		// Denoting endgame operators Up, Down, Left, Right, Collect, Kill and Snap
		this.setOperators("UDLRCKS");
		this.endGameGrid = new EndGameGrid(grid);
		
	}

	public String getGrid() {
		return grid;
	}

	@Override
	public State transitionFunction(State currentState, char operator) {
		EndGameState currentEndState = (EndGameState) currentState; 
		Vector2 currentIronManPos = currentEndState.getIronManPos();
		boolean snaped = currentEndState.isSnaped();
		boolean[] killed = currentEndState.getKilled();
		boolean[] stonesCollected = currentEndState.getStonesCollected();
		switch(operator) {
			case 'U' : 
				// Checking if moving would cause outofBound
				if(endGameGrid.isCellEmpty(currentIronManPos.Up(), currentEndState)) {
					
				}
				break;
			case 'D' :
				// Checking if moving would cause outofBound
				if(endGameGrid.isCellEmpty(currentIronManPos.Down(), currentEndState)) {
					
				}
				break;
			case 'L' :
				// Checking if moving would cause outofBound
				if(endGameGrid.isCellEmpty(currentIronManPos.Left(), currentEndState)) {
					
				}
				break;
			case 'R' :
				if(endGameGrid.isCellEmpty(currentIronManPos.Right(), currentEndState)) {
					
				}
				break;
			case 'C' : //TODO Collect operator transition
			case 'K' : //TODO Kill operator transition
			case 'S' : //TODO Snap operator transition
					
		}
		return null;
	}
	
//	private boolean nextCellFree(int intendedPositionX, int intendedPositionY, boolean[] killedWarriors) {
//		boolean warriorFound = false;
//		boolean thanosFound = false;
//		
//		// Check if warrior is in the target cell of the operator
//		for(int i = 0; i < warriorsPositions.length(); i+=2) {
//			if(intendedPositionX == Character.getNumericValue(warriorsPositions.charAt(i))
//			&& intendedPositionY == Character.getNumericValue(warriorsPositions.charAt (i + 1))
//			&& !killedWarriors[i / 2]) {
//				return false;
//			}
//		}
//		
//		// Check if thanos is in the target cell of the operator
//		if(intendedPositionX == Character.getNumericValue(thanosPosition.charAt(0))
//		&& intendedPositionY == Character.getNumericValue(thanosPosition.charAt(1)))
//			return false;
//	
//		return true;
//	}

	@Override
	public boolean goalTest(State currentState) {
		// TODO Adding goal test to check if snapped action occurred or not
		return false;
	}

	@Override
	public int pathCost(State newState, char operator) {
		
		int totalDamage = 0;
		EndGameState newEndGameState = (EndGameState) newState;
		totalDamage += endGameGrid.getGridCellContent(newEndGameState.getIronManPos().Up(), newEndGameState).getCellDamage();
		totalDamage += endGameGrid.getGridCellContent(newEndGameState.getIronManPos().Down(), newEndGameState).getCellDamage();
		totalDamage += endGameGrid.getGridCellContent(newEndGameState.getIronManPos().Left(), newEndGameState).getCellDamage();
		totalDamage += endGameGrid.getGridCellContent(newEndGameState.getIronManPos().Right(), newEndGameState).getCellDamage();
		
		
		//TODO add the cost of the Operator
		
		return 0;
	}

}
