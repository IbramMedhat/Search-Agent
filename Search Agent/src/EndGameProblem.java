
public class EndGameProblem extends Problem  {
	private String grid;
	
	
	public EndGameProblem(String grid) {
		super();
		this.grid = grid;
	}

	public String getGrid() {
		return grid;
	}

	@Override
	public State transitionFunction(State currentState, char operator) {
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
