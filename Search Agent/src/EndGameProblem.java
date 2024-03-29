
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
		EndGameState initialState = new EndGameState();
		initialState.setIronManPosition(endGameGrid.getInitialIronManPos());
		this.setInitialState(initialState);
		setVisitedStates(new VisitedStateList());
		
	}

	public String getGrid() {
		return grid;
	}

	@Override
	public State transitionFunction(State currentState, char operator) {
		EndGameState currentEndState = (EndGameState) currentState; 
		Vector2 currentIronManPos = currentEndState.getIronManPos();
//		Vector2 nextIronManPos = currentIronManPos;
		
		EndGameState newEndGameState = new EndGameState(currentEndState.getRawState());
		
//		boolean snaped = currentEndState.isSnaped();
//		boolean[] killed = currentEndState.getKilled();
//		boolean[] stonesCollected = currentEndState.getStonesCollected();
		// Applying transition function according to different operators
		switch(operator) {
			case 'U' : 
				// Checking if moving would cause outofBound
				if(endGameGrid.isCellEmpty(currentIronManPos.Up(), currentEndState)) {
					newEndGameState.setIronManPosition(currentIronManPos.Up());
				}
				break;
			case 'D' :
				// Checking if moving would cause outofBound
				if(endGameGrid.isCellEmpty(currentIronManPos.Down(), currentEndState)) {
					newEndGameState.setIronManPosition(currentIronManPos.Down());
				}
				break;
			case 'L' :
				// Checking if moving would cause outofBound
				if(endGameGrid.isCellEmpty(currentIronManPos.Left(), currentEndState)) {
					newEndGameState.setIronManPosition(currentIronManPos.Left());
				}
				break;
			case 'R' :
				if(endGameGrid.isCellEmpty(currentIronManPos.Right(), currentEndState)) {
					newEndGameState.setIronManPosition(currentIronManPos.Right());
				}
				break;
			case 'C' : 
				// Check if there is stone in the current ironman position
				if(endGameGrid.doesCellContain(currentIronManPos, EndGameCellType.STONE, currentEndState)) {
					newEndGameState.setStoneCollected(endGameGrid.getEndGameCell(currentIronManPos).getContentIndex());
				}
				break;
			case 'K' : 
				// Checking in all adjacent cells to kill all surrounding warriors 
				if(endGameGrid.doesCellContain(currentIronManPos.Up(), EndGameCellType.WARRIOR, currentEndState)) {
					newEndGameState.setWarriorKilled(endGameGrid.getEndGameCell(currentIronManPos.Up()).getContentIndex());
				}
				if(endGameGrid.doesCellContain(currentIronManPos.Down(), EndGameCellType.WARRIOR, currentEndState)) {
					newEndGameState.setWarriorKilled(endGameGrid.getEndGameCell(currentIronManPos.Down()).getContentIndex());
				}
				if(endGameGrid.doesCellContain(currentIronManPos.Right(), EndGameCellType.WARRIOR, currentEndState)) {
					newEndGameState.setWarriorKilled(endGameGrid.getEndGameCell(currentIronManPos.Right()).getContentIndex());
				}
				if(endGameGrid.doesCellContain(currentIronManPos.Left(), EndGameCellType.WARRIOR, currentEndState)) {
					newEndGameState.setWarriorKilled(endGameGrid.getEndGameCell(currentIronManPos.Left()).getContentIndex());
				}
				break;
			case 'S' : 

				if(currentEndState.areAllStoneCollected()) {
					// Checking if all stones are collected and Thanos is in the same cell as ironman
					if(endGameGrid.doesCellContain(currentIronManPos, EndGameCellType.THANOS, currentEndState))
						newEndGameState.setSnapped();
				}
				break;
		}
		
		//checking if the state is not equal the parent state
		if(newEndGameState.getRawState() != currentEndState.getRawState() && !getVisitedStates().isStateRepeated(newEndGameState))
			return newEndGameState;
		else
			return null;
	}
	


	@Override
	public boolean goalTest(State currentState) {
		EndGameState currentEndState = (EndGameState) currentState;
		if(currentEndState.isSnapped()) {
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
		if(operator != 'S')
			totalDamage += endGameGrid.getGridCellContent(newEndGameState.getIronManPos(), newEndGameState).getCellDamage();
		
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

	@Override
	public int calculateHeuristic(int functionIndex, Node currentNode) {
		switch(functionIndex) {
			case 1 : return stonesRemainingAndWarriorsHeuristic(currentNode);
			case 2 : return stonesRemainingHeuristic(currentNode);
			default : return 0;
		}
	}
	
	private int stonesRemainingHeuristic(Node currentNode) {
		int collectedStones = ((EndGameState)currentNode.getCurrentState()).CollectedStonesCount();
		int totalCostOfCollectStones = 18;
		int estimatedRemainingCost = totalCostOfCollectStones - 3 * collectedStones;
		return estimatedRemainingCost;
		
	}
	
	private int stonesRemainingAndWarriorsHeuristic(Node currentNode) {
		int estimatedRemainingCost;
		int collectedStones = ((EndGameState)currentNode.getCurrentState()).CollectedStonesCount();
		int killedWarriors = ((EndGameState)currentNode.getCurrentState()).KilledWarriorsCount();
		
		
		//if the number of warriors exceeds 5 or we have already collected all the stones, we ignore the effect 
		//don't calculate the warriors in our heuristic.
		if(killedWarriors > 5 || collectedStones > 5)
			killedWarriors = 5;
		
		int totalCostOfCollectStones = 18 + 5;
		estimatedRemainingCost = totalCostOfCollectStones - (3 * collectedStones + killedWarriors);

		
		return estimatedRemainingCost;
		
	}
	
	@SuppressWarnings("unused")
	private int warriorsRemainingHeurisitic(Node currentNode) {
		int killedWarriors = ((EndGameState)currentNode.getCurrentState()).KilledWarriorsCount();
		int totalCostOfKilledWarriors = 10;
		int estimatedRemainingCost = totalCostOfKilledWarriors - 2 * killedWarriors;
		return estimatedRemainingCost > 0? estimatedRemainingCost : 0;
	}
	
	private int cityBlockDistanceFromThanos(Node currentNode) {
		
		Vector2 thanosPos = endGameGrid.getThanosPos();
		Vector2 ironManPos = ((EndGameState)currentNode.getCurrentState()).getIronManPos();
		int deltaX = Math.abs(thanosPos.x - ironManPos.x);
		int deltaY = Math.abs(thanosPos.y - ironManPos.y);
		return deltaX < deltaY ? deltaX : deltaY;
	}

	@Override
	protected String Visualize(State currentState) {
		String visualization = "";
		visualization = this.endGameGrid.Visualize(currentState);
		return visualization;
	}

}
