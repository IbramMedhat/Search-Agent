import java.util.ArrayList;

public class Main {
	
	//TODO Adding queue class
	
	public static String solve(String grid, String strategy, boolean visualize) {
		return "";
	}
	
	public static Node generalSearch(Problem problem, String strategy) {
		
		//TODO choosing between different search strategies
		switch (strategy) {
		case "BFS":
			
			break;

		default:
			break;
		}		
		return null;
		
	}
	
	public static ArrayList<Node> expand(Node currentNode, Problem problem){
		State currentState = currentNode.getCurrentState();
		for(int i = 0;i < problem.getOperators().length();i++) {
			//TODO getting all operators of the current problem
		}
		return null;
	}
	
}
