
public class Main {
	
	//TODO Adding queueDT class implementation
	
	public static String solve(String grid, String strategy, boolean visualize) {
		
		EndGameProblem problem = new EndGameProblem(grid);
		Node goalNode = null;
		switch(strategy) {
			case "BF":
				goalNode = generalSearch(problem, "enqueueAtEnd");break;
			case "DF":
				goalNode = generalSearch(problem, "enqueueAtFront");break;
			
			case "ID":
				goalNode = generalSearch(problem, "enqueueAtEnd");break; //TODO Applying depth limited search till goal is reached
				
			case "UC":
				goalNode = generalSearch(problem, "orderedInsert");break;
				
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
	
	public static Node generalSearch(Problem problem, String queueingFunction) {
		
		QueueDT<Node> toBeExpandedNodes = new QueueDT<Node>();
		//TODO choosing between different queueing functions
		
		//enqueue initial state before starting the expanding process
		toBeExpandedNodes.add(new Node(problem.getInitialState(), null, '\0', 0, 0));
		
		Node currentNode = (Node) toBeExpandedNodes.poll();
		while(currentNode != null || !problem.goalTest(currentNode.getCurrentState())) {

			expand(toBeExpandedNodes, currentNode, problem, queueingFunction);
			currentNode = (Node) toBeExpandedNodes.poll();
		}
		return currentNode;
		
	}
	
	public static void expand(QueueDT<Node> toBeExpandedQueueDT, Node currentNode, Problem problem, String queueingFunction){
		
		switch (queueingFunction) {	
			case "enqueueAtFront":
				enqueueAtFront(toBeExpandedQueueDT, currentNode, problem);break;
			case "enqueueAtEnd":
				enqueueAtEnd(toBeExpandedQueueDT, currentNode, problem);break;
			
			case "orderedInsert":
				orderedInsert(toBeExpandedQueueDT, currentNode, problem);break;
			default:
				break;
		}		
		
	}

	public static void enqueueAtFront(QueueDT<Node> toBeExpandedQueueDT, Node currentNode, Problem problem) {
		for(int i = 0;i < problem.getOperators().length(); i++) {
			char operator = problem.getOperators().charAt(i); //getting the current expanded operator
			State nextState = problem.transitionFunction(currentNode.getCurrentState(), operator);
			
			// Creating the new node and adding it to the front of the queue
			// Changing the path cost function to take current node and current operator
			toBeExpandedQueueDT.addFirst(new Node(nextState, 
											currentNode, 
											operator,
											currentNode.getDepth() + 1,
											problem.pathCost(currentNode, operator)));
		}
	}
	
	public static void enqueueAtEnd(QueueDT<Node> toBeExpandedQueueDT, Node currentNode, Problem problem) {
		for(int i = 0;i < problem.getOperators().length(); i++) {
			char operator = problem.getOperators().charAt(i); //getting the current expanded operator
			State nextState = problem.transitionFunction(currentNode.getCurrentState(), operator);
			
			// Creating the new node and adding it to the end of the queue
			// Changing the path cost function to take current node and current operator
			toBeExpandedQueueDT.add(new Node(nextState, 
											currentNode, 
											operator,
											currentNode.getDepth() + 1,
											problem.pathCost(currentNode, operator)));
		}
	}
	
	public static void orderedInsert(QueueDT<Node> toBeExpandedQueueDT, Node currentNode, Problem problem) {
		for(int i = 0;i < problem.getOperators().length(); i++) {
			//TODO
		}
	}
	
}


