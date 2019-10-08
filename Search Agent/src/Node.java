
public class Node {
	
	private State currentState;
	private Node parentNode;
	private char operator;
	private int depth;
	private int pathCost;
	
	public Node(State currentState, Node parentNode, char operator, int depth, int pathCost) {
		super();
		this.currentState = currentState;
		this.parentNode = parentNode;
		this.operator = operator;
		this.depth = depth;
		this.pathCost = pathCost;
	}
	
	public State getCurrentState() {
		return currentState;
	}
	public Node getParentNode() {
		return parentNode;
	}
	public char getOperator() {
		return operator;
	}
	public int getDepth() {
		return depth;
	}
	public int getPathCost() {
		return pathCost;
	}
	
	
	
	
}
