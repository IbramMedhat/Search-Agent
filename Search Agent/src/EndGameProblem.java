
public class EndGameProblem extends Problem  {
	private String grid;
	private String warriorsPositions;
	private String stonesPositions;
	private String thanosPosition;
	
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
		
	}

	public String getGrid() {
		return grid;
	}

	@Override
	public State transitionFunction(State currentState, char operator) {
		EndGameState currentEndState = (EndGameState) currentState; 
		int nextIronManX = currentEndState.getIronManX();
		int nextIronManY = currentEndState.getIronManY();
		boolean snaped = currentEndState.isSnaped();
		boolean[] killed = currentEndState.getKilled();
		boolean[] stonesCollected = currentEndState.getStonesCollected();
		switch(operator) {
			case 'U' : 
				// Checking if moving would cause outofBound
				if(currentEndState.getIronManX() > 0) {
					// Checking if next cell is free
					if(nextCellFree(currentEndState.getIronManX() - 1, currentEndState.getIronManY(), 
							currentEndState.getKilled()))
						nextIronManX = currentEndState.getIronManX() - 1;
				}
			case 'D' :
				// Checking if moving would cause outofBound
				if(currentEndState.getIronManX() < 4) {
					// Checking if next cell is free
					if(nextCellFree(currentEndState.getIronManX() + 1, currentEndState.getIronManY(), 
						currentEndState.getKilled()))
					nextIronManX = currentEndState.getIronManX() + 1;
				}
			case 'L' :
				// Checking if moving would cause outofBound
				if(currentEndState.getIronManY() > 0){
					// Checking if next cell is free
					if(nextCellFree(currentEndState.getIronManX(), currentEndState.getIronManY() - 1, 
						currentEndState.getKilled()))
					nextIronManX = currentEndState.getIronManY() - 1;
				}
			case 'R' :
				// Checking if moving would cause outofBound
				if(currentEndState.getIronManY() < 4){
					// Checking if next cell is free
					if(nextCellFree(currentEndState.getIronManX(), currentEndState.getIronManY() + 1, 
						currentEndState.getKilled()))
					nextIronManX = currentEndState.getIronManY() + 1;
				}
			case 'C' : //TODO Collect operator transition
			case 'K' : //TODO Kill operator transition
			case 'S' : //TODO Snap operator transition
					
		}
		return null;
	}
	
	private boolean nextCellFree(int intendedPositionX, int intendedPositionY, boolean[] killedWarriors) {
		boolean warriorFound = false;
		boolean thanosFound = false;
		
		// Check if warrior is in the target cell of the operator
		for(int i = 0; i < warriorsPositions.length(); i+=2) {
			if(intendedPositionX == Character.getNumericValue(warriorsPositions.charAt(i))
			&& intendedPositionY == Character.getNumericValue(warriorsPositions.charAt (i + 1))
			&& !killedWarriors[i / 2]) {
				return false;
			}
		}
		
		// Check if thanos is in the target cell of the operator
		if(intendedPositionX == Character.getNumericValue(thanosPosition.charAt(0))
		&& intendedPositionY == Character.getNumericValue(thanosPosition.charAt(1)))
			return false;
	
		return true;
	}

	@Override
	public boolean goalTest(State currentState) {
		// TODO Adding goal test to check if snapped action occurred or not
		return false;
	}

	@Override
	public int pathCost(State currentState, char operator) {
		// TODO calculating the path cost
		
		return 0;
	}

}
