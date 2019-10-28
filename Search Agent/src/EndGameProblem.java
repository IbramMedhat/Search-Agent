
public class EndGameProblem extends Problem  {
	private String grid;
	
	private EndGameGrid endGameGrid;
	
	public EndGameProblem(String grid) {
		super();
		this.grid = grid;
		
		// Parsing the grid to get warriors, stones and Thanos positions
		
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
		Vector2 nextIronManPos = currentIronManPos;
		boolean snaped = currentEndState.isSnaped();
		boolean[] killed = currentEndState.getKilled();
		boolean[] stonesCollected = currentEndState.getStonesCollected();
		// Applying transition function according to different operators
		switch(operator) {
			case 'U' : 
				// Checking if moving would cause outofBound
				if(endGameGrid.isCellEmpty(currentIronManPos.Up(), currentEndState)) {
					nextIronManPos = currentIronManPos.Up();
				}
				break;
			case 'D' :
				// Checking if moving would cause outofBound
				if(endGameGrid.isCellEmpty(currentIronManPos.Down(), currentEndState)) {
					nextIronManPos = currentIronManPos.Down();
				}
				break;
			case 'L' :
				// Checking if moving would cause outofBound
				if(endGameGrid.isCellEmpty(currentIronManPos.Left(), currentEndState)) {
					nextIronManPos = currentIronManPos.Left();
				}
				break;
			case 'R' :
				if(endGameGrid.isCellEmpty(currentIronManPos.Right(), currentEndState)) {
					nextIronManPos = currentIronManPos.Right();
				}
				break;
			case 'C' : 
				// Check if there is stone in the current ironman position
				if(endGameGrid.doesCellContain(currentIronManPos, EndGameCellType.STONE, currentEndState)) {
					stonesCollected[endGameGrid.getEndGameCell(currentIronManPos).getContentIndex()] = true;
				}
			case 'K' : 
				// Checking in all adjacent cells to kill all surrounding warriors 
				if(endGameGrid.doesCellContain(currentIronManPos.Up(), EndGameCellType.WARRIOR, currentEndState)) {
					killed[endGameGrid.getEndGameCell(currentIronManPos.Up()).getContentIndex()] = true;
				}
				if(endGameGrid.doesCellContain(currentIronManPos.Down(), EndGameCellType.WARRIOR, currentEndState)) {
					killed[endGameGrid.getEndGameCell(currentIronManPos.Down()).getContentIndex()] = true;
				}
				if(endGameGrid.doesCellContain(currentIronManPos.Right(), EndGameCellType.WARRIOR, currentEndState)) {
					killed[endGameGrid.getEndGameCell(currentIronManPos.Right()).getContentIndex()] = true;
				}
				if(endGameGrid.doesCellContain(currentIronManPos.Left(), EndGameCellType.WARRIOR, currentEndState)) {
					killed[endGameGrid.getEndGameCell(currentIronManPos.Left()).getContentIndex()] = true;
				}
			case 'S' : 
				boolean allCollected = true;
				for(int i = 0; i < stonesCollected.length; i++) {
					if(!stonesCollected[i]) {
						allCollected = false;
						break;
					}
				}
				if(allCollected) {
					// Checking if all stones are collected and Thanos is in the same cell as ironman
					if(endGameGrid.doesCellContain(currentIronManPos, EndGameCellType.THANOS, currentEndState))
						snaped = true;
				}
		}
		return new EndGameState(nextIronManPos, killed, stonesCollected, snaped);
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
		EndGameState currentEndState = (EndGameState) currentState;
		if(currentEndState.isSnaped()) {
			return true;
		}
		return false;
	}

	@Override
	public int pathCost(Node parentNode, State newState, char operator) {
		
		int totalDamage = 0;
		EndGameState newEndGameState = (EndGameState) newState;
		EndGameState parentEndGameState = (EndGameState) parentNode.getCurrentState();
		int numOfSurroundingWarriors = 0;
		
		// Adding damage caused of being adjacent to warriors or thanos
		totalDamage += endGameGrid.getGridCellContent(newEndGameState.getIronManPos().Up(), newEndGameState).getCellDamage();
		totalDamage += endGameGrid.getGridCellContent(newEndGameState.getIronManPos().Down(), newEndGameState).getCellDamage();
		totalDamage += endGameGrid.getGridCellContent(newEndGameState.getIronManPos().Left(), newEndGameState).getCellDamage();
		totalDamage += endGameGrid.getGridCellContent(newEndGameState.getIronManPos().Right(), newEndGameState).getCellDamage();
		
		
		switch(operator) {
			case 'C' : totalDamage += 3;break; // Cost for collect infinity stone is 3
			case 'K' : 
				// Incrementing damage by 2 for each warrior adjacent to ironman before kill operator
				if(endGameGrid.doesCellContain(parentEndGameState.getIronManPos().Up(), EndGameCellType.WARRIOR, parentEndGameState))
					numOfSurroundingWarriors ++;
				if(endGameGrid.doesCellContain(parentEndGameState.getIronManPos().Down(), EndGameCellType.WARRIOR, parentEndGameState))
					numOfSurroundingWarriors ++;
				if(endGameGrid.doesCellContain(parentEndGameState.getIronManPos().Left(), EndGameCellType.WARRIOR, parentEndGameState))
					numOfSurroundingWarriors ++;
				if(endGameGrid.doesCellContain(parentEndGameState.getIronManPos().Right(), EndGameCellType.WARRIOR, parentEndGameState))
					numOfSurroundingWarriors ++;
				totalDamage = totalDamage + (2 * numOfSurroundingWarriors);break;
			default : totalDamage += 0;break; // Costs for the other five operators are zero
		}
		return totalDamage + parentNode.getPathCost();
	}

}
