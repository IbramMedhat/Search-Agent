import java.util.ArrayList;

public abstract class Problem {
	
	private ArrayList<String> operators;
	//private State initialState;
	
	/*public State transitionFunction(State currentState) {
		
	}*/
	
	//public boolean goalTest(State currentState) {}
	
	public abstract  double pathCost(String[] operators);
}
