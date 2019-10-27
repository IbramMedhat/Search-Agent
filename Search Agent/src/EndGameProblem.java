
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
		int nextIronManX;
		int nextIronManY;
		boolean snapped;
		int numOfWarriors;
		int numOfInfinityStones;
		switch(operator) {
			case 'U' : 
				if(currentEndState.getIronManX() > 0)
					nextIronManX = currentEndState.getIronManX() - 1;
			case 'D' :
				if(currentEndState.getIronManX() < 4)
					nextIronManX = currentEndState.getIronManX() + 1;
			case 'L' :
				if(currentEndState.getIronManY() > 0)
					nextIronManX = currentEndState.getIronManY() - 1;
			case 'R' :
				if(currentEndState.getIronManY() < 4)
					nextIronManX = currentEndState.getIronManY() + 1;
			case 'C' :
			case 'K' :
			case 'S' :
					
		}
		// TODO Adding transition function utilizing the grid instance variable
		return null;
	}

	@Override
	public boolean goalTest(State currentState) {
		// TODO Adding goal test to check if snapped action occurred or not
		return false;
	}

	@Override
	public int pathCost(Node currentNode, char operator) {
		// TODO calculating the path cost
		
		return 0;
	}

}
