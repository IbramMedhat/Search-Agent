import java.util.ArrayList;

public class Main {
	
	//TODO Adding queueDT class implementation
	
	public static void main(String [] args)
	{
		solve("5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3", "BF", false);
	}
	
	public static String solve(String grid, String strategy, boolean visualize) {
		
		EndGameProblem problem = new EndGameProblem(grid);
		Node goalNode = null;
		switch(strategy) {
			case "BF":
				goalNode = generalSearch(problem, QueueingFunction.ENQUEUE_AT_END);break;
			case "DF":
				goalNode = generalSearch(problem, QueueingFunction.ENQUEUE_AT_FRONT);break;
			
			case "ID":
				goalNode = generalSearch(problem, QueueingFunction.ENQUEUE_AT_END);break; //TODO Applying depth limited search till goal is reached
				
			case "UC":
				goalNode = generalSearch(problem, QueueingFunction.SORTED_INSERT);break;
				
//			case "GR1":
//				generalSearch(problem, "enqueueAtEnd");break; //TODO 
//				
//			case "GR2":
//				generalSearch(problem, "enqueueAtEnd");break;
//			
//			case "A*1":
//				generalSearch(problem, "enqueueAtEnd");break;
//			
//			case "A*2":
//				generalSearch(problem, "enqueueAtEnd");break;
//			
		}
		if(goalNode == null)
			return "There is no solution";
		else {
			//TODO return sequence of operators leading to this goal node
			return "";
		}
	}
	
	public static Node generalSearch(Problem problem, QueueingFunction queueingFunction) {
		
		QueueDT<Node> toBeExpandedNodes = new QueueDT<Node>();
		VisitedStateList visitedStates = new VisitedStateList();
		//TODO choosing between different queueing functions
		
		//enqueue initial state before starting the expanding process
		toBeExpandedNodes.add(new Node(problem.getInitialState(), null, '\0', 0, 0));
		
		Node currentNode = (Node) toBeExpandedNodes.poll();
		while(currentNode != null || !problem.goalTest(currentNode.getCurrentState())) {

			expand(toBeExpandedNodes, visitedStates, currentNode, problem, queueingFunction);
			currentNode = (Node) toBeExpandedNodes.poll();
		}
		return currentNode;
		
	}
	
	public static void expand(QueueDT<Node> toBeExpandedQueueDT, VisitedStateList visitedStates, Node currentNode, Problem problem, QueueingFunction queueingFunction){
		
		
		//Expanding Current Node and getting its children
		ArrayList<Node> childrenNodes = new ArrayList<Node>();
		for(int i = 0;i < problem.getOperators().length(); i++) {
			char operator = problem.getOperators().charAt(i); //getting the current expanded operator
			State nextState = problem.transitionFunction(currentNode.getCurrentState(), operator);
			
			//Checking if this state is repeated
			if(nextState != null && visitedStates.CheckState(nextState))
			{
				// Creating the new node and adding it to the front of the queue
				// Changing the path cost function to take current node and current operator
				childrenNodes.add(new Node(nextState, 
												currentNode, 
												operator,
												currentNode.getDepth() + 1,
												problem.pathCost(currentNode, nextState, operator)));
			}
		
		}
		
		switch (queueingFunction) {
		case ENQUEUE_AT_END:
			enqueueAtEnd(toBeExpandedQueueDT, childrenNodes);
			break;
		case ENQUEUE_AT_FRONT:
			enqueueAtFront(toBeExpandedQueueDT, childrenNodes);
			break;
		case SORTED_INSERT:
			orderedInsert(toBeExpandedQueueDT, childrenNodes);
			break;
		default:
			break;	
		
		
		}		
		
	}

	public static void enqueueAtFront(QueueDT<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes) {
		for (Node node : childrenNodes) {
			toBeExpandedQueueDT.addFirst(node);
		}
	}
	
	public static void enqueueAtEnd(QueueDT<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes) {
		for (Node node : childrenNodes) {
			toBeExpandedQueueDT.add(node);
		}
	}
	
	public static void orderedInsert(QueueDT<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes) {
		//TODO ordered insertion based on each node path cost
		boolean inserted = false;
		for (Node nodeToBeInserted : childrenNodes) {
			inserted = false;
			int currentCost = nodeToBeInserted.getPathCost();
			for(int i = 0; i < toBeExpandedQueueDT.size(); i++)
			{
				Node queueNode = (Node) toBeExpandedQueueDT.getItem(i);
				if(queueNode.getPathCost() > currentCost)
				{
					toBeExpandedQueueDT.insertAt(i, nodeToBeInserted);
					inserted = true;
					break;
				}
			}
			// if the queue is empty or if nodeToBeInserted Cost is greater than all the queues' nodes
			if(!inserted)
				toBeExpandedQueueDT.add(nodeToBeInserted);
		}
		
	}
	
}


