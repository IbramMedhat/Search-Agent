
public class Node {
	
	private State currentState;
	private Node parentNode;
	private char operator;
	private int depth;
	private int pathCost;
	private int queuingCost;
	
	public Node(State currentState, Node parentNode, char operator, int depth, int pathCost) {
		super();
		this.currentState = currentState;
		this.parentNode = parentNode;
		this.operator = operator;
		this.depth = depth;
		this.pathCost = pathCost;
		this.queuingCost = pathCost;
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
	public int getQueuingCost() {
		return queuingCost;
	}
	public void setQueuingCost(int queuingCost) {
		this.queuingCost = queuingCost;
	}
	
	
}
