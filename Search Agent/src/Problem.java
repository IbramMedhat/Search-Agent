public abstract class Problem {
	
	private String operators;
	private State initialState;
	
	private VisitedStateList visitedStates;
	
	public abstract State transitionFunction(State currentState, char operator);
	
	public abstract boolean goalTest(State currentState);
	
	public abstract  int pathCost(Node parentNode, State currentState, char operator);
	
	public abstract int calculateHeuristic(int functionIndex, Node currentNode);
	
	
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

	public VisitedStateList getVisitedStates() {
		return visitedStates;
	}

	public void setVisitedStates(VisitedStateList visitedStates) {
		this.visitedStates = visitedStates;
	}
	
	
	
	
}
