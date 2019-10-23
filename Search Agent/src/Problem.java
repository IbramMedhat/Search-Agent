import java.util.ArrayList;

public abstract class Problem {
	
	private String operators;
	private State initialState;
	
	public abstract State transitionFunction(State currentState, char operator);
	
	public abstract boolean goalTest(State currentState);
	
	public abstract  int pathCost(Node currentNode, char operator);

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}
	
	public State getInitialState() {
		return initialState;
	}

	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}

	
	
}
